package nl.underkoen.adventofcode.general.map.collection;

import org.jetbrains.annotations.Contract;

import java.util.*;
import java.util.function.Supplier;

/**
 * Uses {@link java.util.HashMap} as map implementation
 */
abstract public class AbstractMapCollection<K, V, C extends Collection<V>> extends HashMap<K, C> implements MapCollection<K, V, C> {
    public AbstractMapCollection() {
        super();
    }

    public AbstractMapCollection(Map<? extends K, ? extends C> m) {
        super(m);
    }

    protected abstract C createCollection();

    protected abstract C copyCollection(C original);

    protected abstract MapCollection<K, V, C> constructMap();

    @Override
    public void add(K key, V value) {
        computeIfAbsent(key, k -> createCollection());
        get(key).add(value);
    }

    @Override
    public boolean delete(K key, V value) {
        if (!containsKey(key)) return false;
        return get(key).remove(value);
    }

    @Override
    public <R extends MapCollection<V, K, ? extends Collection<K>>> R invert(Supplier<R> supplier) {
        R r = supplier.get();

        for (Map.Entry<K, C> entry : entrySet()) {
            K key = entry.getKey();
            for (V value : entry.getValue()) {
                r.add(value, key);
            }
        }

        return r;
    }

    @Override
    public MapCollection<K, V, C> deepCopy() {
        MapCollection<K, V, C> r = constructMap();

        deepCopyOver(r);

        return r;
    }

    @Contract(mutates = "param")
    protected void deepCopyOver(MapCollection<K, V, C> copy) {
        forEach((k, v) -> copy.put(k, copyCollection(v)));
    }

    @Override
    public Map<K, V> reduce() {
        return reduce(deepCopy());
    }

    @Override
    public Map<K, V> reduceSelf() {
        return reduce(shallowCopy());
    }

    @Contract(mutates = "param", value = "_ -> new")
    protected Map<K, V> reduce(MapCollection<K, V, C> map) {
        Map<K, V> keys = new HashMap<>();
        Set<Entry<K, C>> entries = new HashSet<>(map.entrySet());

        boolean changed;
        do {
            changed = false;

            for (Iterator<Entry<K, C>> iter = entries.iterator(); iter.hasNext(); ) {
                Entry<K, C> entry = iter.next();
                int size = entry.getValue().size();

                if (size > 1) continue;
                iter.remove();
                if (size == 0) continue;

                V value = entry.getValue().iterator().next();

                keys.put(entry.getKey(), value);

                for (Entry<K, C> e : entries) {
                    changed = e.getValue().remove(value) || changed;
                }
            }
        } while (changed);

        return keys;
    }
}