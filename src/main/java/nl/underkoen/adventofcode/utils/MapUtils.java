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
    public <K> boolean increaseInt(Map<K, Integer> map, K key) {
        return increase(map, key, 0);
    }

    /**
     * @return true when the value already existed
     */
    public <K> boolean increaseLong(Map<K, Long> map, K key) {
        return increase(map, key, 0L);
    }

    /**
     * @return true when the value already existed
     */
    public <K, N extends Number> boolean increase(Map<K, N> map, K key, N defaultValue) {
        N n = map.getOrDefault(key, defaultValue);

        return map.put(key, NumberUtils.increase(n)) != null;
    }

    public <K, N extends Number> void increaseAll(Map<K, N> map, Iterable<K> values, N defaultValue) {
        values.forEach(k -> increase(map, k, defaultValue));
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
