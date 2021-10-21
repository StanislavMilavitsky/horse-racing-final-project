package by.milavitsky.horseracing.service.serviceinterface;

import by.milavitsky.horseracing.entity.Bet;
import by.milavitsky.horseracing.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface BetServiceInterface extends Service {

    List<Bet> showByUser(Long userId) throws ServiceException;

    boolean addBet(String info, String betCash, BigDecimal userCash, Long userId) throws ServiceException;

    boolean enterResult(Map<Integer, String> horseMap, String raceId) throws ServiceException;
}
