package com.jvmbytes.commons.utils.collection;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author luanjia
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> {

    private final int maxCapacity;

    public LRUCache(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maxCapacity;
    }

}
