package by.milavitsky.horseracing.cache;

import java.util.EnumMap;
import java.util.Map;

public class CacheFactory {
    private static final CacheFactory instance = new CacheFactory();
    private static final Map<CacheType, ? super Cache> cacheMap = new EnumMap<>(CacheType.class);

    private CacheFactory() {
    }

    static {
        cacheMap.put(CacheType.ROLES, new Cache());
        cacheMap.put(CacheType.RACES_COUNT, new Cache());
        cacheMap.put(CacheType.USER_COUNT, new Cache());
    }

    /**
     * Gets cache by type.
     *
     * @param type the type
     * @return the cache
     */
    public Object getCache(CacheType type) {
        return cacheMap.get(type);
    }

    private static class CacheFactoryHolder{
        private static final CacheFactory HOLDER_INSTANCE = new CacheFactory();
    }

    public static CacheFactory getInstance() {
        return CacheFactoryHolder.HOLDER_INSTANCE;
    }
}
