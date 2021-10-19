package by.milavitsky.horseracing.service.serviceinterface;

import by.milavitsky.horseracing.entity.enums.Ratio;
import by.milavitsky.horseracing.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface RatioServiceInterface extends Service {

    List<Ratio> findRatio(String raceId) throws ServiceException;

    boolean addRatios(Map<String, String> parameterMap) throws ServiceException;
}
