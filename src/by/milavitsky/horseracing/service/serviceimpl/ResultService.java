package by.milavitsky.horseracing.service.serviceimpl;


import by.milavitsky.horseracing.dao.pool.TransactionManager;
import by.milavitsky.horseracing.entity.Race;
import by.milavitsky.horseracing.exception.DaoException;
import by.milavitsky.horseracing.exception.ServiceException;
import by.milavitsky.horseracing.service.ServiceFactory;
import by.milavitsky.horseracing.service.serviceinterface.RaceServiceInterface;
import by.milavitsky.horseracing.service.serviceinterface.ResultServiceInterface;
import by.milavitsky.horseracing.validation.CommonValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static by.milavitsky.horseracing.service.ServiceParameter.MAX_HORSE_PARTICIPANTS;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class ResultService implements ResultServiceInterface {

    private static final Logger logger = LogManager.getLogger(ResultService.class);

    private ResultService(){
    }

    @Override
    public boolean enterResult(Map<Integer, String> horseMap, String raceId) throws ServiceException {
        if (!CommonValidator.isIdValid(raceId)) {
            return false;
        }
        try {
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
            return true;//todo
        } catch (DaoException e) {
            logger.error("Enter result exception!", e);
            throw new ServiceException("Enter result exception!", e);
        }
    }


    private static class ResultServiceHolder{
        private static final ResultService HOLDER_INSTANCE = new ResultService();
    }

    public static ResultService getInstance() {
        return ResultServiceHolder.HOLDER_INSTANCE;
    }
}
