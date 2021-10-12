package by.milavitsky.horseracing.service.service_entity;

import by.milavitsky.horseracing.dao.DaoFactory;
import by.milavitsky.horseracing.dao.dao_abstract.UserDaoAbstract;
import by.milavitsky.horseracing.entity.User;
import by.milavitsky.horseracing.service.service_interface.UserServiceInterface;
import by.milavitsky.horseracing.util.BCryptService;
import by.milavitsky.horseracing.validation.CommonValidator;
import by.milavitsky.horseracing.validation.UserValidator;
import by.milavitsky.horseracing.exception.DaoException;
import by.milavitsky.horseracing.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.milavitsky.horseracing.controller.CommandParameter.*;
import static by.milavitsky.horseracing.service.service_entity.ServiceParameter.USERS_ON_PAGE;


public class UserService implements UserServiceInterface {

    private static final Logger logger = LogManager.getLogger(UserService.class);

    private UserService() {
    }

    @Override
    public User authorization(User user) throws ServiceException {
        try {
            if (!UserValidator.isValidEmail(user.getEmail()) || !UserValidator.isValidPassword(user.getPassword())) {
                return null;
            }
            UserDaoAbstract userDao = (UserDaoAbstract) DaoFactory.getInstance().getClass(UserDaoAbstract.class);
           Optional<User> userAuthorized = userDao.authorization(user);//todo
            User userAuthorizedOfOptional = userAuthorized.get();
            if (userAuthorizedOfOptional == null) {
                return null;
            }
            if (!BCryptService.verifyPassword(user.getPassword(), userAuthorizedOfOptional.getPassword())) {
                return null;
            }
            return userAuthorizedOfOptional;
        } catch (DaoException e) {
            logger.error("Authorization exception!", e);
            throw new ServiceException("Authorization exception!", e);
        }
    }
    @Override
    public Map<String, String> registration(User userUI) throws ServiceException {
        try {
            Map<String, String> userMAP = new HashMap<>();
            boolean invalidUser = false;
            if (UserValidator.isValidName(userUI.getName())) {
                userMAP.put(PARAM_NAME, userUI.getName());
            } else {
                invalidUser = true;
                userMAP.put(PARAM_NAME, BLANK);
            }
            if (UserValidator.isValidSurname(userUI.getSurname())) {
                userMAP.put(PARAM_SURNAME, userUI.getSurname());
            } else {
                invalidUser = true;
                userMAP.put(PARAM_SURNAME, BLANK);
            }
            if (UserValidator.isValidPassword(userUI.getPassword())) {
                userMAP.put(PARAM_PASSWORD, userUI.getPassword());
            } else {
                invalidUser = true;
                userMAP.put(PARAM_PASSWORD, BLANK);
            }
            UserDaoAbstract userDao = (UserDaoAbstract) DaoFactory.getInstance().getClass(UserDaoAbstract.class);
            Optional<User> user = userDao.findByEmail(userUI.getEmail());
            if (UserValidator.isValidEmail(userUI.getEmail()) && user.isEmpty()) {
                userMAP.put(PARAM_EMAIL, userUI.getEmail());
            } else {
                invalidUser = true;
                userMAP.put(PARAM_EMAIL, BLANK);
            }
            if (!invalidUser) {
                String password = userUI.getPassword();
                userUI.setPassword(BCryptService.hashPassword(password));
                userDao.create(userUI);
                return null;
            }
            return userMAP;
        } catch (DaoException e) {
            logger.error("Registration exception!", e);
            throw new ServiceException("Registration exception!", e);
        }
    }
    @Override
    public List<User> findAll(String page) throws ServiceException {
        try {
            int offset = 0;
            if (page != null && !page.isEmpty()&& CommonValidator.isIdValid(page)) {
                offset = (Integer.parseInt(page) - 1) * USERS_ON_PAGE;
            }
            UserDaoAbstract userDao = (UserDaoAbstract) DaoFactory.getInstance().getClass(UserDaoAbstract.class);
            return userDao.findAll(USERS_ON_PAGE, offset);
        } catch (DaoException e) {
            logger.error("Show all users exception!", e);
            throw new ServiceException("Show all users service exception!", e);
        }
    }

    @Override
    public boolean updateCash(BigDecimal cash, Long userId) throws ServiceException {
        try {
            UserDaoAbstract userDao = (UserDaoAbstract) DaoFactory.getInstance().getClass(UserDaoAbstract.class);
            return userDao.updateCash(cash, userId);
        } catch (DaoException e) {
            logger.error("Add cash exception!", e);
            throw new ServiceException("Add cash exception!", e);
        }
    }

    @Override
    public int getUsersPagesCount() throws ServiceException {
        return 0;
    }
    @Override
    public boolean ban(String userId) throws ServiceException {
        try {
            if (!CommonValidator.isIdValid(userId)) {
                return false;
            }
            Long LongUserId = Long.valueOf(userId);
            UserDaoAbstract userDao = (UserDaoAbstract) DaoFactory.getInstance().getClass(UserDaoAbstract.class);
            return userDao.ban(LongUserId);
        } catch (DaoException e) {
            logger.error("User ban exception!", e);
            throw new ServiceException("User ban exception!", e);
        }
    }
    private static class UserServiceHolder{
        private static final UserService HOLDER_INSTANCE = new UserService();
    }

    public static UserService getInstance() {
        return UserServiceHolder.HOLDER_INSTANCE;
    }


}
