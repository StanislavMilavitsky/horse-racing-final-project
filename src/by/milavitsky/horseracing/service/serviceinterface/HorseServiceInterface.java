package by.milavitsky.horseracing.service.serviceinterface;

import by.milavitsky.horseracing.entity.Horse;
import by.milavitsky.horseracing.exception.ServiceException;

import java.util.List;
import java.util.Set;

public interface HorseServiceInterface extends Service {

    Set<Horse> showByRace(String raceId) throws ServiceException;

    List<Horse> findAll() throws ServiceException;
}
