package by.milavitsky.horseracing.service.serviceinterface;

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
     * @param betCash  the bet cash
     * @param userCash the user cash
     * @param userId   the user id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean addBet(String info, String betCash, BigDecimal userCash, Long userId) throws ServiceException;

    /**
     * Enter race result and pay all bets.
     *
     * @param horseMap the horse map
     * @param raceId   the race id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean enterResult(Map<Integer, String> horseMap, String raceId) throws ServiceException;
}
