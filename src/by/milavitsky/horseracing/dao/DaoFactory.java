package by.milavitsky.horseracing.dao;

import by.milavitsky.horseracing.dao.daoabstract.*;
import by.milavitsky.horseracing.dao.daoimpl.*;

import java.util.Map;

public class DaoFactory {

    private static final Map<Class<? extends Dao<?, ?>>, Dao<?, ?>> factory;

    static{
        factory = Map.of(
                UserDaoAbstract.class, UserDao.getInstance(),
                BetDaoAbstract.class, BetDao.getInstance(),
                ResultDaoAbstract.class, ResultDao.getInstance(),
                RaceDaoAbstract.class, RaceDao.getInstance(),
                HorseDaoAbstract.class, HorseDao.getInstance(),
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

    private static class DaoFactoryHolder{
        private static final DaoFactory HOLDER_INSTANCE = new DaoFactory();

    }

    public static DaoFactory getInstance() {
        return DaoFactoryHolder.HOLDER_INSTANCE;
    }
}
