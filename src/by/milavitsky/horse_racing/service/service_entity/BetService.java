package by.milavitsky.horse_racing.service.service_entity;

import by.milavitsky.horse_racing.dao.DaoFactory;
import by.milavitsky.horse_racing.dao.dao_abstract.BetDaoAbstract;
import by.milavitsky.horse_racing.dao.pool.TransactionManager;
import by.milavitsky.horse_racing.entity.Bet;
import by.milavitsky.horse_racing.entity.Horse;
import by.milavitsky.horse_racing.entity.Race;
import by.milavitsky.horse_racing.entity.enums.BetType;
import by.milavitsky.horse_racing.exception.DaoException;
import by.milavitsky.horse_racing.exception.ServiceException;
import by.milavitsky.horse_racing.service.ServiceFactory;
import by.milavitsky.horse_racing.service.service_interface.BetServiceInterface;
import by.milavitsky.horse_racing.service.service_interface.HorseServiceInterface;
import by.milavitsky.horse_racing.service.service_interface.RaceServiceInterface;
import by.milavitsky.horse_racing.validation.BetValidator;
import by.milavitsky.horse_racing.validation.CommonValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.math.BigDecimal;
import java.util.*;

import static by.milavitsky.horse_racing.service.service_entity.ServiceParameter.MAX_HORSE_PARTICIPANTS;
import static org.apache.commons.lang3.StringUtils.*;
import static by.milavitsky.horse_racing.controller.CommandParameter.BLANK;

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
        try {
            if (!BetValidator.isInfoValid(info) || !CommonValidator.isBigDecimalValid(betCash)) {
                return false;
            }
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
        try {
            if (!CommonValidator.isIdValid(raceId)) {
                return new ArrayList<>();
            }
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
    public boolean enterResult(Map<Integer, String> horseMap, String raceId) throws ServiceException {
        try {
            if (!CommonValidator.isIdValid(raceId)) {
                return false;
            }
            Long idRace = Long.valueOf(raceId);
            RaceServiceInterface raceService = (RaceServiceInterface) ServiceFactory.getInstance().getClass(RaceServiceInterface.class);
            Race race = raceService.findInfo(raceId);
            Set<Long> horsesId = race.getHorse();
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
            if (resultMap.size() < MAX_HORSE_PARTICIPANTS) {
                for (int i = resultMap.size() + 1; i <= MAX_HORSE_PARTICIPANTS; i++) {
                    resultMap.put(i, 0L);
                }
            }
            TransactionManager.getInstance().enterResult(resultMap, idRace);
            return true;
        } catch (DaoException e) {
            logger.error("Enter result exception!", e);
            throw new ServiceException("Enter result exception!", e);
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
                bet.setBetTypeId(typeId);
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
