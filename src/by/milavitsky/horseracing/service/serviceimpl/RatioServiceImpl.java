package by.milavitsky.horseracing.service.serviceimpl;

import by.milavitsky.horseracing.dao.DaoFactory;
import by.milavitsky.horseracing.dao.daoabstract.RatioDaoAbstract;
import by.milavitsky.horseracing.entity.Horse;
import by.milavitsky.horseracing.entity.enumentity.BetType;
import by.milavitsky.horseracing.entity.Ratio;
import by.milavitsky.horseracing.exception.DaoException;
import by.milavitsky.horseracing.exception.ServiceException;
import by.milavitsky.horseracing.service.ServiceFactory;
import by.milavitsky.horseracing.service.serviceinterface.HorseService;
import by.milavitsky.horseracing.service.serviceinterface.RatioService;
import by.milavitsky.horseracing.validation.CommonValidator;
import by.milavitsky.horseracing.validation.RatioValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.*;

public class RatioServiceImpl implements RatioService {

    private static final Logger logger = LogManager.getLogger(RatioServiceImpl.class);

    private static final String MAP_KEY_DELIMITER = "\\|";
    private static final int TYPE_COUNT = 2;

    @Override
    public List<Ratio> findRatio(String raceId) throws ServiceException {
        try {
            if (!CommonValidator.isIdValid(raceId)) {
                return new ArrayList<>();
            }
            Long id = Long.valueOf(raceId);
            RatioDaoAbstract ratioDao = (RatioDaoAbstract) DaoFactory.getInstance().getClass(RatioDaoAbstract.class);
            List<Ratio> ratios = ratioDao.findRatio(id);
            return ratios;
        } catch (DaoException e) {
            logger.error("Find ratio list fail!", e);
            throw new ServiceException("Find ratio list fail!", e);
        }
    }

    @Override
    public boolean addRatios(Map<String, String> parameterMap) throws ServiceException {
        try {
            Set<Ratio> ratioSet = new HashSet<>();
            Long raceId = 0L;
            for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (!RatioValidator.isMapKeyValid(key) || !RatioValidator.isMapValueValid(value)) {
                    continue;
                }
                String[] keys = key.split(MAP_KEY_DELIMITER);
                raceId = Long.parseLong(keys[0]);
                Long horseId = Long.parseLong(keys[1]);
                Long typeId = (long) BetType.valueOf(keys[2].toUpperCase()).ordinal();
                typeId++;
                BigDecimal ratio = new BigDecimal(value);
                ratioSet.add(new Ratio(raceId, horseId, typeId, ratio));
            }
            if (ratioSet.isEmpty() || raceId == 0L) {
                return false;
            }
            HorseService horseService = (HorseService) ServiceFactory.getInstance().getClass(HorseService.class);
            Set<Horse> horses = horseService.showByRace(raceId.toString());
            if (ratioSet.size() != horses.size() * TYPE_COUNT) {
                return false;
            }

            RatioDaoAbstract ratioDao = (RatioDaoAbstract) DaoFactory.getInstance().getClass(RatioDaoAbstract.class);
            boolean result = ratioDao.setRatios(ratioSet);
            return result;
        } catch (DaoException e) {
            logger.error("Set ratios fail!", e);
            throw new ServiceException("Set ratios fail!", e);
        }
    }

    private static class RatioServiceHolder{
        private static final RatioServiceImpl HOLDER_INSTANCE = new RatioServiceImpl();
    }

    public static RatioServiceImpl getInstance() {
        return RatioServiceHolder.HOLDER_INSTANCE;
    }
}
