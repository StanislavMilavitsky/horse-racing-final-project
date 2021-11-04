package by.milavitsky.horseracing.service;

import by.milavitsky.horseracing.service.serviceimpl.*;
import by.milavitsky.horseracing.service.serviceinterface.*;

import java.util.Map;
/**
 * The type Service factory.
 */
public class ServiceFactory {
    private static final Map<Class<? extends Service>, Service> factory;

    private ServiceFactory() {
    }

    static {
        factory = Map.of(
                UserService.class, UserServiceImpl.getInstance(),
                BetService.class, BetServiceImpl.getInstance(),
                RaceService.class, RaceServiceImpl.getInstance(),
                HorseService.class, HorseServiceImpl.getInstance(),
                RatioService.class, RatioServiceImpl.getInstance());
    }

    /**
     * Gets implementation of interface class.
     *
     * @param clazz the clazz
     * @return the class
     */
    public Service getClass(Class<?> clazz) {
        return factory.get(clazz);
    }

    private static class ServiceFactoryHolder{
        private static final ServiceFactory HOLDER_INSTANCE = new ServiceFactory();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */

    public static ServiceFactory getInstance() {
        return ServiceFactoryHolder.HOLDER_INSTANCE;
    }
}
