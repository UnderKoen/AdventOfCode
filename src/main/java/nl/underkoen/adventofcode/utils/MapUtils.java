package nl.underkoen.adventofcode.utils;

import lombok.experimental.UtilityClass;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

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
        add(map, key, value, ArrayList::new);
    }

    public <K, V> void addSet(Map<K, Set<V>> map, K key, V value) {
        add(map, key, value, HashSet::new);
    }

    public <K, V, C extends Collection<V>> void add(Map<K, C> map, K key, V value, Supplier<C> supplier) {
        map.computeIfAbsent(key, k -> supplier.get());
        map.get(key).add(value);
    }

    public <K, V, C extends Collection<V>> boolean remove(Map<K, C> map, K key, V value) {
        if (!map.containsKey(key)) return false;
        return map.get(key).remove(value);
    }

    public <K, V> Map<V, List<K>> invert(Map<K, List<V>> map) {
        return invert(map, ArrayList::new);
    }

    public <K, V> Map<V, Set<K>> invertSet(Map<K, Set<V>> map) {
        return invert(map, HashSet::new);
    }

    public <K, V, C extends Collection<V>, CK extends Collection<K>> Map<V, CK> invert(Map<K, C> map, Supplier<CK> supplier) {
        Map<V, CK> r = new HashMap<>();
        for (Map.Entry<K, C> entry : map.entrySet()) {
            for (V value : entry.getValue()) {
                add(r, value, entry.getKey(), supplier);
            }
        }
        return r;
    }

    public <K, V, C extends Collection<V>> Map<K, C> deepCopy(Map<K, C> map, Function<C, C> supplier) {
        Map<K, C> copy = new HashMap<>();
        map.forEach((k, v) -> copy.put(k, supplier.apply(v)));
        return copy;
    }

    public <K, V, C extends Collection<V>> void reduce(Map<K, C> map) {
        reduce(map, c -> c);
    }

    public <K, V, C extends Collection<V>> Map<K, V> reduce(Map<K, C> map, Function<C, C> supplier) {
        Map<K, V> keys = new HashMap<>();
        map = deepCopy(map, supplier);

        for (int t = 0; t < map.size(); t++) {
            for (Map.Entry<K, C> entry : map.entrySet()) {
                if (entry.getValue().size() != 1) continue;

                V only = entry.getValue().iterator().next();
                for (Map.Entry<K, C> e : map.entrySet()) {
                    if (e == entry) continue;
                    e.getValue().remove(only);
                }
            }
        }

        map.forEach((k, v) -> {
            if (v.size() == 1) keys.put(k, v.iterator().next());
        });

        return keys;
    }
}
