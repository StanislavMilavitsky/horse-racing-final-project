package by.milavitsky.horseracing.service;

import by.milavitsky.horseracing.service.serviceimpl.*;
import by.milavitsky.horseracing.service.serviceinterface.*;

import java.util.Map;

public class ServiceFactory {
    private static final Map<Class<? extends Service>, Service> factory;

    private ServiceFactory() {
    }

    static {
        factory = Map.of(
                UserServiceInterface.class, UserService.getInstance(),
                BetServiceInterface.class, BetService.getInstance(),
                RaceServiceInterface.class, RaceService.getInstance(),
                HorseServiceInterface.class, HorseService.getInstance(),
                ResultServiceInterface.class, ResultService.getInstance(),
                RatioServiceInterface.class, RatioService.getInstance());
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

    public static ServiceFactory getInstance() {
        return ServiceFactoryHolder.HOLDER_INSTANCE;
    }
}
