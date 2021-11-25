package nl.underkoen.adventofcode.general.map.collection;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HashMapSet<K, V> extends AbstractMapCollection<K, V, Set<V>> implements MapSet<K, V> {
    public HashMapSet() {
        super();
    }

    public HashMapSet(Map<? extends K, ? extends Set<V>> m) {
        super(m);
    }

    @Override
    protected Set<V> createCollection() {
        return new HashSet<>();
    }

    @Override
    protected Set<V> copyCollection(Set<V> original) {
        return new HashSet<>(original);
    }

    @Override
    protected MapCollection<K, V, Set<V>> constructMap() {
        return new HashMapSet<>();
    }

    @Override
    public MapSet<V, K> invert() {
        return invert(HashMapSet::new);
    }

    @Override
    public MapSet<K, V> shallowCopy() {
        return new HashMapSet<>(this);
    }

    @Override
    public MapSet<K, V> deepCopy() {
        HashMapSet<K, V> map = new HashMapSet<>();

        deepCopyOver(map);

        return map;
    }
}
