package by.milavitsky.horseracing.service.serviceimpl;

import by.milavitsky.horseracing.dao.DaoFactory;
import by.milavitsky.horseracing.dao.daoabstract.BetDaoAbstract;
import by.milavitsky.horseracing.dao.pool.TransactionManager;
import by.milavitsky.horseracing.entity.Bet;
import by.milavitsky.horseracing.entity.Horse;
import by.milavitsky.horseracing.entity.Race;
import by.milavitsky.horseracing.entity.enums.BetType;
import by.milavitsky.horseracing.exception.DaoException;
import by.milavitsky.horseracing.exception.ServiceException;
import by.milavitsky.horseracing.service.ServiceFactory;
import by.milavitsky.horseracing.service.serviceinterface.BetServiceInterface;
import by.milavitsky.horseracing.service.serviceinterface.HorseServiceInterface;
import by.milavitsky.horseracing.validation.BetValidator;
import by.milavitsky.horseracing.validation.CommonValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.math.BigDecimal;
import java.util.*;

import static by.milavitsky.horseracing.controller.CommandParameter.BLANK;

public class BetService implements BetServiceInterface {
    private static final Logger logger = LogManager.getLogger(BetService.class);

    private static final String INFO_TITLE = "Ratio\\{";
    private static final int INFO_ARRAY = 4;
    private static final String COMMA = ",";
    private static final String EQUAL_SIGN = "=";
    private static final String MAP_KEY_DELIMITER = "\\|";
    private static final int TYPE_COUNT = 2;

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

    @Override
    public boolean add(String info, String betCash, BigDecimal userCash, Long userId) throws ServiceException {
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
            Race race = new Race();
            race.setId(Long.valueOf(infoArray[0]));
            bet.setRace(race);
            bet.setAmountBet(cash);
            bet.setRatio(new BigDecimal(infoArray[3]));
            BetType[] values = BetType.values();
            BetType type = values[Integer.parseInt(infoArray[2]) - 1];
            bet.setBetType(type);
            Horse horse = new Horse();
            horse.setId(Long.valueOf(infoArray[1]));
            bet.setHorse(horse);
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
    public List<Bet> findRatioByRaceId(String raceId) throws ServiceException {
        if (!CommonValidator.isIdValid(raceId)) {
            return new ArrayList<>();
        }
        try {
            Long id = Long.valueOf(raceId);
            BetDaoAbstract betDao = (BetDaoAbstract) DaoFactory.getInstance().getClass(BetDaoAbstract.class);
            List<Bet> bet = betDao.findByRaceRatio(id);
            return bet;
        } catch (DaoException e) {
            logger.error("Find ratio list fail!", e);
            throw new ServiceException("Find ratio list fail!", e);
        }
    }

    @Override
    public boolean addRatios(Map<String, String> parameterMap) throws ServiceException {
        try {
            Set<Bet> betSet = new HashSet<>();
            Long raceId = 0L;
            for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (!BetValidator.isMapKeyValid(key) || !BetValidator.isMapValueValid(value)) {
                    continue;
                }
                String[] keys = key.split(MAP_KEY_DELIMITER);
                raceId = Long.parseLong(keys[0]);
                Long horseId = Long.parseLong(keys[1]);
                Long typeId = (long) BetType.valueOf(keys[2].toUpperCase()).ordinal();
                typeId++;
                BigDecimal ratio = new BigDecimal(value);
                Bet bet = new Bet();
                bet.setRacesId(raceId);
                bet.setHorseId(horseId);
                bet.setTypeId(typeId);
                bet.setRatio(ratio);
                betSet.add(bet);
            }
            if (betSet.isEmpty() || raceId == 0L) {
                return false;
            }
            HorseServiceInterface horseService = (HorseServiceInterface) ServiceFactory.getInstance().getClass(HorseServiceInterface.class);
            Set<Horse> horses = horseService.showByRace(raceId.toString());
            if (betSet.size() != horses.size() * TYPE_COUNT) {
                return false;
            }

            BetDaoAbstract betDao = (BetDaoAbstract) DaoFactory.getInstance().getClass(BetDaoAbstract.class);
            boolean result = betDao.setRatios(betSet);
            return result;
        } catch (DaoException e) {
            logger.error("Set ratios fail!", e);
            throw new ServiceException("Set ratios fail!", e);
        }
    }
    private static class BetServiceHolder{
        private static final BetService HOLDER_INSTANCE = new BetService();
    }

    public static BetService getInstance() {
        return BetServiceHolder.HOLDER_INSTANCE;
    }
}
