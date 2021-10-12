package by.milavitsky.horseracing.service.service_interface;

import by.milavitsky.horseracing.entity.Bet;
import by.milavitsky.horseracing.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface BetServiceInterface extends Service {
    /**
     * Show user bets.
     *
     * @param userId the user id
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Bet> showByUser(Long userId) throws ServiceException;

    /**
     * Add bet.
     *
     * @param info the info
     * @param betCash the bet cash
     * @param userCash the user cash
     * @param userId   the user id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean add(String info, String betCash, BigDecimal userCash, Long userId) throws ServiceException;

    List<Bet> findRatioByRaceId(String raceId) throws ServiceException;

    boolean enterResult(Map<Integer, String> horseMap, String raceId) throws ServiceException;

    boolean addRatios(Map<String, String> parameterMap) throws ServiceException;
}
