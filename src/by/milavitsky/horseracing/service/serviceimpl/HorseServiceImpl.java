package by.milavitsky.horseracing.service.serviceimpl;

import by.milavitsky.horseracing.dao.DaoFactory;
import by.milavitsky.horseracing.dao.daoabstract.HorseDaoAbstract;
import by.milavitsky.horseracing.entity.Horse;
import by.milavitsky.horseracing.exception.DaoException;
import by.milavitsky.horseracing.exception.ServiceException;
import by.milavitsky.horseracing.service.serviceinterface.HorseService;
import by.milavitsky.horseracing.validation.CommonValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HorseServiceImpl implements HorseService {
    private static final Logger logger = LogManager.getLogger(HorseServiceImpl.class);

    private HorseServiceImpl(){
    }

    @Override
    public Set<Horse> showByRace(String raceId) throws ServiceException {
        if (!CommonValidator.isIdValid(raceId)) {
            return new HashSet<>();
        }
        try {
            Long id = Long.valueOf(raceId);
            HorseDaoAbstract horseDao = (HorseDaoAbstract) DaoFactory.getInstance().getClass(HorseDaoAbstract.class);
            Set<Horse> horses = horseDao.findByRace(id);
            return horses;
        } catch (DaoException e) {
            logger.error("Show horses by race exception!", e);
            throw new ServiceException("Show horses by race exception!", e);
        }
    }

    @Override
    public List<Horse> findAll() throws ServiceException {
        try {
            HorseDaoAbstract horseDao = (HorseDaoAbstract) DaoFactory.getInstance().getClass(HorseDaoAbstract.class);
            List<Horse> horses = horseDao.findAll();
            return horses;
        } catch (DaoException e) {
            logger.error("Show all horses exception!", e);
            throw new ServiceException("Show all horses exception!", e);
        }
    }


    private static class HorseServiceHolder{
        private static final HorseServiceImpl HOLDER_INSTANCE = new HorseServiceImpl();
    }

    public static HorseServiceImpl getInstance() {
        return HorseServiceHolder.HOLDER_INSTANCE;
    }

}

