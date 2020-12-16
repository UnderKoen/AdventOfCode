package nl.underkoen.adventofcode.utils;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UtilityClass
public class MapUtils {
    public <T> void increaseInt(Map<T, Integer> map, T key, int defaultValue) {
        int n = map.getOrDefault(key, defaultValue);
        map.put(key, n + 1);
    }
    public <T> void increaseInt(Map<T, Integer> map, T key) {
        increaseInt(map, key, 0);
    }

    public <T> void increaseLong(Map<T, Long> map, T key, long defaultValue) {
        long n = map.getOrDefault(key, defaultValue);
        map.put(key, n + 1);
    }

    public <K> void increaseLong(Map<K, Long> map, K key) {
        increaseLong(map, key, 0);
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
