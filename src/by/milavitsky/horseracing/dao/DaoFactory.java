package by.milavitsky.horseracing.dao;

import by.milavitsky.horseracing.dao.daoabstract.*;
import by.milavitsky.horseracing.dao.daoimpl.*;

import java.util.Map;

/**
 * The type Dao factory.
 */
public class DaoFactory {

    private static final Map<Class<? extends Dao<?, ?>>, Dao<?, ?>> factory;

    static{
        factory = Map.of(
                UserDaoAbstract.class, UserDao.getInstance(),
                BetDaoAbstract.class, BetDao.getInstance(),
                RaceDaoAbstract.class, RaceDao.getInstance(),
                HorseDaoAbstract.class, HorseDao.getInstance(),
        RatioDaoAbstract.class, RatioDao.getInstance(),
        RolePermissionsDaoAbstract.class, RolePermissionDao.getInstance());
    }

    private DaoFactory(){

    }

    /**
     * Gets  implementation of abstract class.
     *
     * @param clazz the clazz
     * @return the class
     */
    public Dao<?, ?> getClass(Class<?> clazz) {
        return factory.get(clazz);
    }

    /**
     * Make inner class that does singleton
     */
    private static class DaoFactoryHolder{
        private static final DaoFactory HOLDER_INSTANCE = new DaoFactory();

    }
    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static DaoFactory getInstance() {
        return DaoFactoryHolder.HOLDER_INSTANCE;
    }
}
