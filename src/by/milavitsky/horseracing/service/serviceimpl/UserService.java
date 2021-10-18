package by.milavitsky.horseracing.service.serviceimpl;

import by.milavitsky.horseracing.cache.Cache;
import by.milavitsky.horseracing.cache.CacheFactory;
import by.milavitsky.horseracing.cache.CacheType;
import by.milavitsky.horseracing.dao.DaoFactory;
import by.milavitsky.horseracing.dao.daoabstract.RolePermissionsDaoAbstract;
import by.milavitsky.horseracing.dao.daoabstract.UserDaoAbstract;
import by.milavitsky.horseracing.entity.Role;
import by.milavitsky.horseracing.entity.User;
import by.milavitsky.horseracing.service.serviceinterface.UserServiceInterface;
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
import java.util.concurrent.atomic.AtomicLong;

import static by.milavitsky.horseracing.cache.CacheVariables.COUNT_ACTIVE;
import static by.milavitsky.horseracing.cache.CacheVariables.USER_ROLES;
import static by.milavitsky.horseracing.controller.CommandParameter.*;
import static by.milavitsky.horseracing.service.ServiceParameter.USERS_ON_PAGE;


public class UserService implements UserServiceInterface {

    private static final Logger logger = LogManager.getLogger(UserService.class);

    private UserService() {
    }

    @Override
    public User authorization(User user) throws ServiceException {
        if (!UserValidator.isValidEmail(user.getEmail()) || !UserValidator.isValidPassword(user.getPassword())) {
            return null;
        }
        try {
            UserDaoAbstract userDao = (UserDaoAbstract) DaoFactory.getInstance().getClass(UserDaoAbstract.class);
           User userAuthorized = userDao.authorization(user);//todo
            if (userAuthorized == null) {//todo
                return null;
            }
            if (!BCryptService.verifyPassword(user.getPassword(), userAuthorized.getPassword())) {//todo
                return null;
            }
            setCashRole(userAuthorized);
            return userAuthorized;
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
                userDao.registration(userUI);
                Cache cache = (Cache) CacheFactory.getInstance().getCache(CacheType.USER_COUNT);
                if (!cache.isEmpty()) {
                    AtomicLong aLong = (AtomicLong) cache.getCache(COUNT_ACTIVE);
                    long newLong = aLong.incrementAndGet();
                    AtomicLong newALong = new AtomicLong(newLong);
                    cache.setCacheValue(COUNT_ACTIVE, aLong, newALong);
                }
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
        try {
            long count;
            Cache cache = (Cache) CacheFactory.getInstance().getCache(CacheType.USER_COUNT);
            if (cache.isEmpty()) {
                UserDaoAbstract userDao = (UserDaoAbstract) DaoFactory.getInstance().getClass(UserDaoAbstract.class);
                count = userDao.count();
                cache.addCache(COUNT_ACTIVE, new AtomicLong(count));
            } else {
                AtomicLong aLong = (AtomicLong) cache.getCache(COUNT_ACTIVE);
                count = aLong.get();
            }
            return (int) Math.ceil((double) count / USERS_ON_PAGE);
        } catch (DaoException e) {
            logger.error("Users count exception!", e);
            throw new ServiceException("Users count exception!", e);
        }
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

    private void setCashRole(User user) throws DaoException {
        Cache cache = (Cache) CacheFactory.getInstance().getCache(CacheType.ROLES);
        List<Role> roles;
        if (cache.isEmpty()) {
            RolePermissionsDaoAbstract rolePermissionsDao = (RolePermissionsDaoAbstract) DaoFactory.getInstance()
                    .getClass(RolePermissionsDaoAbstract.class);
            roles = rolePermissionsDao.findAll();
            cache.addCache(USER_ROLES, roles);
        } else {
            roles = (List<Role>) cache.getCache(USER_ROLES);
        }
        for (Role role : roles) {
            if (role.getId().equals(user.getRole().getId())) {
                user.setRole(role);
            }
        }
    }

}
