package by.milavitsky.horseracing.service.serviceinterface;

import by.milavitsky.horseracing.entity.Race;
import by.milavitsky.horseracing.exception.ServiceException;

import java.util.List;
import java.util.Set;

public interface RaceServiceInterface extends Service {

    List<Race> showAllActive(String page) throws ServiceException;

    List<Race> showAll(String page) throws ServiceException;

    int pageNumberActive() throws ServiceException;

    int pageNumberAll() throws ServiceException;

    boolean addRace(Set<Long> horseSet, String location, String dateTime) throws ServiceException;

    boolean delete(String raceId) throws ServiceException;

    Race findInfo(String raceId) throws ServiceException;
}
