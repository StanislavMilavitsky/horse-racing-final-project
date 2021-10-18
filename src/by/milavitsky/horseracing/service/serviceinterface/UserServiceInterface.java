package by.milavitsky.horseracing.service.serviceinterface;

import by.milavitsky.horseracing.entity.User;
import by.milavitsky.horseracing.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface UserServiceInterface extends Service {

    User authorization(User user) throws ServiceException;

    Map<String, String> registration(User user) throws ServiceException;

    List<User> findAll(String page) throws ServiceException;

    boolean updateCash(BigDecimal cash, Long userId) throws ServiceException;

    int getUsersPagesCount() throws ServiceException;

    boolean ban(String userId) throws ServiceException;
}
