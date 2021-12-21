package by.milavitsky.horseracing.service.serviceinterface;

import by.milavitsky.horseracing.entity.Race;
import by.milavitsky.horseracing.exception.ServiceException;

import java.util.List;
import java.util.Set;

/**
 * The interface Race service.
 */
public interface RaceService extends Service {
    /**
     * Show all active race.
     *
     * @param page the page
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Race> showAllActive(String page) throws ServiceException;

    /**
     * Show all race.
     *
     * @param page the page
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Race> showAll(String page) throws ServiceException;

    /**
     * Number of all race.
     *
     * @return the int
     * @throws ServiceException the service exception
     */
    int pageNumberActive() throws ServiceException;

    /**
     * Number of all race.
     *
     * @return the int
     * @throws ServiceException the service exception
     */
    int pageNumberAll() throws ServiceException;

    /**
     * Add race.
     *
     * @param horseSet the horse set
     * @param location the location
     * @param dateTime the date time
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean addRace(Set<Long> horseSet, String location, String dateTime) throws ServiceException;

    /**
     * Delete race.
     *
     * @param raceId the race id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean delete(String raceId) throws ServiceException;

    /**
     * Find race info.
     *
     * @param raceId the race id
     * @return the race
     * @throws ServiceException the service exception
     */
    Race findInfo(String raceId) throws ServiceException;

    /**
     *
     * @param info string from view
     * @return chech race time
     */
    boolean isCorrectTimeRace(String info) throws ServiceException;
}
