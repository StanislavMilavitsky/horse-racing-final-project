package by.milavitsky.horseracing.service.serviceimpl;

import by.milavitsky.horseracing.dao.DaoFactory;
import by.milavitsky.horseracing.dao.daoabstract.BetDaoAbstract;
import by.milavitsky.horseracing.dao.daoabstract.HorseDaoAbstract;
import by.milavitsky.horseracing.dao.TransactionManager;
import by.milavitsky.horseracing.entity.Bet;
import by.milavitsky.horseracing.entity.Horse;
import by.milavitsky.horseracing.entity.enumentity.BetType;
import by.milavitsky.horseracing.exception.DaoException;
import by.milavitsky.horseracing.exception.ServiceException;
import by.milavitsky.horseracing.service.serviceinterface.BetService;
import by.milavitsky.horseracing.validation.BetValidator;
import by.milavitsky.horseracing.validation.CommonValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static by.milavitsky.horseracing.controller.CommandParameter.BLANK;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class BetServiceImpl implements BetService {
    private static final Logger logger = LogManager.getLogger(BetServiceImpl.class);

    private static final String INFO_TITLE = "Ratio\\{";
    private static final int INFO_ARRAY = 4;
    private static final String COMMA = ",";
    private static final String EQUAL_SIGN = "=";

    @Override
    public List<Bet> showByUser(Long userId) throws ServiceException {
        try {
            BetDaoAbstract betDao = (BetDaoAbstract) DaoFactory.getInstance().getClass(BetDaoAbstract.class);
            List<Bet> bets = betDao.findByUser(userId);
            return bets;
        } catch (DaoException e) {
            logger.error("Show by user bets exception!", e);
            throw new ServiceException("Show by user bets exception!", e);
        }
    }

    public boolean addBet(String info, String betCash, BigDecimal userCash, Long userId) throws ServiceException {
        if (!BetValidator.isInfoValid(info) || !CommonValidator.isBigDecimalValid(betCash)) {
            return false;
        }

        try {
            BigDecimal cash = new BigDecimal(betCash);
            if (cash.compareTo(userCash) > 0) {
                return false;
            }
            String string = info.replaceAll(INFO_TITLE, BLANK).substring(0, info.length() - 1 - INFO_TITLE.length());
            String[] split = string.split(COMMA);
            String[] infoArray = new String[INFO_ARRAY];
            if (split.length == INFO_ARRAY) {
                for (int i = 0; i < split.length; i++) {
                    String str = split[i];
                    int index = str.indexOf(EQUAL_SIGN);
                    infoArray[i] = str.substring(index + 1);
                }
            }
            Bet bet = new Bet();
            bet.setUserId(userId);
            bet.setRacesId(Long.valueOf(infoArray[0]));
            bet.setAmountBet(cash);
            bet.setRatio(new BigDecimal(infoArray[3]));
            bet.setRatio(new BigDecimal(infoArray[3]));
            BetType[] values = BetType.values();
            BetType type = values[Integer.parseInt(infoArray[2]) - 1];
            bet.setBetType(type);
            bet.setHorseId(Long.valueOf(infoArray[1]));
            Optional<Bet> betResult = TransactionManager.getInstance().addBet(bet);
            boolean result = false;
            if (betResult.isPresent()) {
                result = true;
            }
            return result;
        } catch (Exception e) {
            logger.error("Add bet exception!", e);
            throw new ServiceException("Add bet exception!", e);
        }
    }

    @Override
    public boolean enterResult(Map<Integer, String> horseMap, String raceId) throws ServiceException {
        if (!CommonValidator.isIdValid(raceId)) {
            return false;
        }
        try {
            Long idRace = Long.valueOf(raceId);
            HorseDaoAbstract horseDao = (HorseDaoAbstract) DaoFactory.getInstance().getClass(HorseDaoAbstract.class);
            Set<Horse> horses = horseDao.findByRace(idRace);
            Set<Long> horsesId = horses.stream().map(Horse::getId).collect(Collectors.toSet());
            Set<Long> resultSet = new HashSet<>();
            Map<Integer, Long> resultMap = new HashMap<>();
            for (Map.Entry<Integer, String> entry : horseMap.entrySet()) {
                Integer key = entry.getKey();
                String value = entry.getValue();
                if (isNotBlank(value)) {
                    if (!CommonValidator.isIdValid(value)) {
                        return false;
                    }
                    Long id = Long.valueOf(value);
                    if (horsesId.contains(id)) {
                        resultSet.add(id);
                        resultMap.put(key, id);
                    }
                } else {
                    resultMap.put(key, 0L);
                }
            }
            if (resultSet.size() != horsesId.size()) {
                return false;
            }
            TransactionManager.getInstance().enterResult(resultMap, idRace);
            return true;
        } catch (DaoException e) {
            logger.error("Enter result exception!", e);
            throw new ServiceException("Enter result exception!", e);
        }
    }

    private static class BetServiceHolder{
        private static final BetServiceImpl HOLDER_INSTANCE = new BetServiceImpl();
    }

    public static BetServiceImpl getInstance() {
        return BetServiceHolder.HOLDER_INSTANCE;
    }
}
