package by.milavitsky.horseracing.service.serviceinterface;

import by.milavitsky.horseracing.entity.User;
import by.milavitsky.horseracing.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * The interface User service.
 */
public interface UserService extends Service {
    /**
     * Authorize user.
     *
     * @param user the user
     * @return the user
     * @throws ServiceException the service exception
     */
    User authorization(User user) throws ServiceException;
    /**
     * Register user and return map of incorrect parameters.
     *
     * @param user the user
     * @return the map
     * @throws ServiceException the service exception
     */
    Map<String, String> registration(User user) throws ServiceException;

    /**
     * Find all users.
     *
     * @param page the page
     * @return the list
     * @throws ServiceException the service exception
     */
    List<User> findAll(String page) throws ServiceException;

    /**
     * Update user cash.
     *
     * @param cash   the cash
     * @param userId the user id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateCash(BigDecimal cash, Long userId) throws ServiceException;
    /**
     * Gets users pages count.
     *
     * @return the users pages count
     * @throws ServiceException the service exception
     */
    int getUsersPagesCount() throws ServiceException;

    /**
     * Set status = 0 user by id.
     *
     * @param userId the user id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean ban(String userId) throws ServiceException;
}
