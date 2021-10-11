package by.milavitsky.horse_racing.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Cache {
    private final ConcurrentMap<String, Object> cache = new ConcurrentHashMap<>();

    /**
     * Gets cache.
     *
     * @param type the type
     * @return the cache
     */
    public Object getCache(String type) {
        return cache.get(type);
    }

    /**
     * Add cache.
     *
     * @param type   the type
     * @param object the object
     */
    public void addCache(String type, Object object) {
        this.cache.putIfAbsent(type, object);
    }

    /**
     * Sets cache value.
     *
     * @param key    the key
     * @param oldObj the old obj
     * @param newObj the new obj
     * @return the cache value
     */
    public boolean setCacheValue(String key, Object oldObj, Object newObj) {
        return this.cache.replace(key, oldObj, newObj);
    }

    /**
     * Contains key boolean.
     *
     * @param key the key
     * @return the boolean
     */
    public boolean containsKey(String key) {
        return cache.containsKey(key);
    }

    /**
     * Is empty boolean.
     *
     * @return the boolean
     */
    public boolean isEmpty() {
        return cache.isEmpty();
    }
}
