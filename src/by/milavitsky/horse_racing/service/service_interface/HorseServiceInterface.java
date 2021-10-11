package by.milavitsky.horse_racing.service.service_interface;

import by.milavitsky.horse_racing.entity.Horse;
import by.milavitsky.horse_racing.exception.ServiceException;

import java.util.List;
import java.util.Set;

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
