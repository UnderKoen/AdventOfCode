package nl.underkoen.adventofcode.utils;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UtilityClass
public class MapUtils {
    /**
     * @return true when the value already existed
     */
    public <T> boolean increaseInt(Map<T, Integer> map, T key, int defaultValue) {
        int n = map.getOrDefault(key, defaultValue);
        return map.put(key, n + 1) != null;
    }

    /**
     * @return true when the value already existed
     */
    public <T> boolean increaseInt(Map<T, Integer> map, T key) {
        return increaseInt(map, key, 0);
    }

    /**
     * @return true when the value already existed
     */
    public <T> boolean increaseLong(Map<T, Long> map, T key, long defaultValue) {
        long n = map.getOrDefault(key, defaultValue);
        return map.put(key, n + 1) != null;
    }

    /**
     * @return true when the value already existed
     */
    public <K> boolean increaseLong(Map<K, Long> map, K key) {
        return increaseLong(map, key, 0);
    }

    public <K, V> void add(Map<K, List<V>> map, K key, V value) {
        map.computeIfAbsent(key, k -> new ArrayList<>());
        map.get(key).add(value);
    }

    public <K, V> boolean remove(Map<K, List<V>> map, K key, V value) {
        if (!map.containsKey(key)) return false;
        return map.get(key).remove(value);
    }

    public <K, V> Map<V, List<K>> invert(Map<K, List<V>> map) {
        Map<V, List<K>> r = new HashMap<>();
        for (Map.Entry<K, List<V>> entry : map.entrySet()) {
            for (V value : entry.getValue()) {
                add(r, value, entry.getKey());
            }
        }
        return r;
    }
}
