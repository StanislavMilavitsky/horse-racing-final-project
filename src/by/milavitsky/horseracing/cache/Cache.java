package by.milavitsky.horseracing.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Cache {
    private final ConcurrentMap<String, Object> cache = new ConcurrentHashMap<>();

    public Object getCache(String type) {
        return cache.get(type);
    }

    public void addCache(String type, Object object) {
        this.cache.putIfAbsent(type, object);
    }


    public boolean setCacheValue(String key, Object oldObj, Object newObj) {
        return this.cache.replace(key, oldObj, newObj);
    }


    public boolean containsKey(String key) {
        return cache.containsKey(key);
    }

    public boolean isEmpty() {
        return cache.isEmpty();
    }
}
