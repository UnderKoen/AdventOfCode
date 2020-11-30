package nl.underkoen.adventofcode.utils;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@UtilityClass
public class MapUtils {
    public <T> void IncreaseInt(Map<T, Integer> map, T key, int defaultValue) {
        int n = map.getOrDefault(key, defaultValue);
        map.put(key, n + 1);
    }
    public <T> void IncreaseInt(Map<T, Integer> map, T key) {
        IncreaseInt(map, key, 0);
    }

    public <T> void IncreaseLong(Map<T, Long> map, T key, long defaultValue) {
        long n = map.getOrDefault(key, defaultValue);
        map.put(key, n + 1);
    }

    public <T> void IncreaseLong(Map<T, Long> map, T key) {
        IncreaseLong(map, key, 0);
    }

    public <T, V> void Add(Map<T, List<V>> map, T key, V value) {
        map.putIfAbsent(key, new ArrayList<>());
        map.get(key).add(value);
    }
}
