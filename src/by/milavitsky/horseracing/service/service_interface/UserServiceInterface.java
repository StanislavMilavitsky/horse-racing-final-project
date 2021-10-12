package by.milavitsky.horseracing.service.service_interface;

import by.milavitsky.horseracing.entity.User;
import by.milavitsky.horseracing.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface UserServiceInterface extends Service {
    /**
     * Authorize user.
     *
     * @param user the user
     * @return the user
     * @throws ServiceException the service by.milavitsky.horse_racing.exception
     */
    User authorization(User user) throws ServiceException;

    /**
     * Register user and return map of incorrect parameters.
     *
     * @param user the user
     * @return the map
     * @throws ServiceException the service by.milavitsky.horse_racing.exception
     */
    Map<String, String> registration(User user) throws ServiceException;

    /**
     * Find all users.
     *
     * @param page the page
     * @return the list
     * @throws ServiceException the service by.milavitsky.horse_racing.exception
     */
    List<User> findAll(String page) throws ServiceException;

    boolean updateCash(BigDecimal cash, Long userId) throws ServiceException;

    /**
     * Gets users pages count.
     *
     * @return the users pages count
     * @throws ServiceException the service by.milavitsky.horse_racing.exception
     */
    int getUsersPagesCount() throws ServiceException;

    boolean ban(String userId) throws ServiceException;
}
