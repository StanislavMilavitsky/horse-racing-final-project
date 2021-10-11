package by.milavitsky.horse_racing.service.service_entity;

import by.milavitsky.horse_racing.dao.DaoFactory;
import by.milavitsky.horse_racing.cache.Cache;
import by.milavitsky.horse_racing.cache.CacheFactory;
import by.milavitsky.horse_racing.cache.CacheType;
import by.milavitsky.horse_racing.dao.dao_abstract.RaceDaoAbstract;
import by.milavitsky.horse_racing.dao.pool.TransactionManager;
import by.milavitsky.horse_racing.entity.Race;
import by.milavitsky.horse_racing.exception.DaoException;
import by.milavitsky.horse_racing.exception.ServiceException;
import by.milavitsky.horse_racing.service.service_interface.RaceServiceInterface;
import by.milavitsky.horse_racing.validation.CommonValidator;
import by.milavitsky.horse_racing.validation.RaceValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import static by.milavitsky.horse_racing.cache.CacheVariables.COUNT_ACTIVE;
import static by.milavitsky.horse_racing.cache.CacheVariables.COUNT_ALL;
import static by.milavitsky.horse_racing.service.service_entity.ServiceParameter.RACES_ON_PAGE;

public class RaceService implements RaceServiceInterface {

    private static final Logger logger = LogManager.getLogger(RaceService.class);

    private RaceService(){
    }

    @Override
    public List<Race> showAllActive(String page) throws ServiceException {
        try {
            int offset = 0;
            if (page != null && !page.isEmpty()) {
                offset = (Integer.parseInt(page) - 1) * RACES_ON_PAGE;
            }
            RaceDaoAbstract raceDao = (RaceDaoAbstract) DaoFactory.getInstance().getClass(RaceDaoAbstract.class);
            List<Race> races = raceDao.findActive(RACES_ON_PAGE, offset);
            return races;
        } catch (DaoException ex) {
            logger.error("Show all active races exception!", ex);
            throw new ServiceException("Show all active races service exception!", ex);
        }
    }

    @Override
    public boolean addRace(Set<Long> horseList, String location, String dateTime) throws ServiceException {
        try {
            if (!RaceValidator.isValidLocation(location) || !RaceValidator.isValidDateTime(dateTime)) {
                return false;
            }
            RaceDaoAbstract raceDao = (RaceDaoAbstract) DaoFactory.getInstance().getClass(RaceDaoAbstract.class);
            Race race = new Race();
            race.setDateTime(LocalDateTime.parse(dateTime));
            race.setHorse(horseList);
            race.setHippodrome(location);
            raceDao.create(race);


                Cache cache = (Cache) CacheFactory.getInstance().getCache(CacheType.RACES_COUNT);
                if (cache.containsKey(COUNT_ACTIVE)) {
                    AtomicLong activeLong = (AtomicLong) cache.getCache(COUNT_ACTIVE);
                    AtomicLong newActiveLong = new AtomicLong(activeLong.incrementAndGet());
                    cache.setCacheValue(COUNT_ACTIVE, activeLong, newActiveLong);
                } else {
                    long countActive = raceDao.countActual();
                    cache.addCache(COUNT_ACTIVE, new AtomicLong(countActive));
                }
                if (cache.containsKey(COUNT_ALL)) {
                    AtomicLong allLong = (AtomicLong) cache.getCache(COUNT_ALL);
                    AtomicLong newAllLong = new AtomicLong(allLong.incrementAndGet());
                    cache.setCacheValue(COUNT_ALL, allLong, newAllLong);
                } else {
                    long countAll = raceDao.countAll();
                    cache.addCache(COUNT_ALL, new AtomicLong(countAll));
                }

            return true;
        } catch (DaoException e) {
            logger.error("Add race exception!", e);
            throw new ServiceException("Add race exception!", e);
        }
    }

    @Override
    public List<Race> showAll(String page) throws ServiceException {
        try {
            int offset = 0;
            if (page != null && !page.isEmpty()) {
                offset = (Integer.parseInt(page) - 1) * RACES_ON_PAGE;
            }
            RaceDaoAbstract raceDao = (RaceDaoAbstract) DaoFactory.getInstance().getClass(RaceDaoAbstract.class);
            List<Race> races = raceDao.findAll(RACES_ON_PAGE, offset);
            return races;
        } catch (DaoException e) {
            logger.error("Show all races exception!", e);
            throw new ServiceException("Show all races service exception!", e);
        }
    }

    @Override
    public int pageNumberActive() throws ServiceException {
        try {
            long count;
            Cache cache = (Cache) CacheFactory.getInstance().getCache(CacheType.RACES_COUNT);
            if (!cache.containsKey(COUNT_ACTIVE)) {
                RaceDaoAbstract raceDao = (RaceDaoAbstract) DaoFactory.getInstance().getClass(RaceDaoAbstract.class);
                count = raceDao.countActual();
                cache.addCache(COUNT_ACTIVE, new AtomicLong(count));
            } else {
                AtomicLong aLong = (AtomicLong) cache.getCache(COUNT_ACTIVE);
                count = aLong.get();
            }
            return (int) Math.ceil((double) count / RACES_ON_PAGE);
        } catch (DaoException e) {
            logger.error("Races count actual exception!", e);
            throw new ServiceException("Races count actual exception!", e);
        }
    }

    @Override
    public int pageNumberAll() throws ServiceException {
        try {
            long count;
            Cache cache = (Cache) CacheFactory.getInstance().getCache(CacheType.RACES_COUNT);
            if (!cache.containsKey(COUNT_ALL)) {
                RaceDaoAbstract raceDao = (RaceDaoAbstract) DaoFactory.getInstance().getClass(RaceDaoAbstract.class);
                count = raceDao.countAll();
                cache.addCache(COUNT_ALL, new AtomicLong(count));
            } else {
                AtomicLong aLong = (AtomicLong) cache.getCache(COUNT_ALL);
                count = aLong.get();
            }
            return (int) Math.ceil((double) count / RACES_ON_PAGE);
        } catch (DaoException e) {
            logger.error("Races count all exception!", e);
            throw new ServiceException("Races count all exception!", e);
        }
    }

    @Override
    public boolean delete(String raceId) throws ServiceException {
        try {
            if (!CommonValidator.isIdValid(raceId)) {
                return false;
            }
            Long id = Long.valueOf(raceId);
            RaceDaoAbstract raceDao = (RaceDaoAbstract) DaoFactory.getInstance().getClass(RaceDaoAbstract.class);
            boolean result = TransactionManager.getInstance().deleteRace(id);
            if (result) {
                Cache cache = (Cache) CacheFactory.getInstance().getCache(CacheType.RACES_COUNT);
                if (cache.containsKey(COUNT_ACTIVE)) {
                    AtomicLong activeLong = (AtomicLong) cache.getCache(COUNT_ACTIVE);
                    AtomicLong newActiveLong = new AtomicLong(activeLong.decrementAndGet());
                    cache.setCacheValue(COUNT_ACTIVE, activeLong, newActiveLong);
                } else {
                    long countActive = raceDao.countActual();
                    cache.addCache(COUNT_ACTIVE, new AtomicLong(countActive));
                }
                if (cache.containsKey(COUNT_ALL)) {
                    AtomicLong allLong = (AtomicLong) cache.getCache(COUNT_ALL);
                    AtomicLong newAllALong = new AtomicLong(allLong.decrementAndGet());
                    cache.setCacheValue(COUNT_ALL, allLong, newAllALong);
                } else {
                    long countAll = raceDao.countAll();
                    cache.addCache(COUNT_ALL, new AtomicLong(countAll));
                }
            }
            return result;
        } catch (DaoException e) {
            logger.error("Add race exception!", e);
            throw new ServiceException("Add race exception!", e);
        }
    }

    @Override
    public Race findInfo(String raceId) throws ServiceException {
        try {
            if (!CommonValidator.isIdValid(raceId)) {
                return new Race();
            }
            Long id = Long.valueOf(raceId);
            RaceDaoAbstract raceDao = (RaceDaoAbstract) DaoFactory.getInstance().getClass(RaceDaoAbstract.class);
            Optional<Race> raceOptional = raceDao.read(id);
            Race race = new Race();
            if (raceOptional.isPresent()) {
                race = raceOptional.get();
            }
            return race;
        } catch (DaoException e) {
            logger.error("Find race info exception!", e);
            throw new ServiceException("Find race info exception!", e);
        }
    }

    private static class RaceServiceHolder{
        private static final RaceService HOLDER_INSTANCE = new RaceService();
    }

    public static RaceService getInstance() {
        return RaceServiceHolder.HOLDER_INSTANCE;
    }
}
