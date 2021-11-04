package by.milavitsky.horseracing.service.serviceinterface;

import by.milavitsky.horseracing.entity.Horse;
import by.milavitsky.horseracing.exception.ServiceException;

import java.util.List;
import java.util.Set;

/**
 * The interface Horse service.
 */
public interface HorseServiceInterface extends Service {
    /**
     * Show horse set by race id.
     *
     * @param raceId the race id
     * @return the set
     * @throws ServiceException the service exception
     */
    Set<Horse> showByRace(String raceId) throws ServiceException;

    /**
     * Find all horses.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Horse> findAll() throws ServiceException;
}
