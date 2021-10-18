package by.milavitsky.horseracing.service.serviceinterface;

import by.milavitsky.horseracing.exception.ServiceException;

import java.util.Map;

public interface ResultServiceInterface extends Service{
    boolean enterResult(Map<Integer, String> horseMap, String raceId) throws ServiceException;
}
