package nl.underkoen.adventofcode.general.map.collection;

import nl.underkoen.adventofcode.general.sets.ESet;

import java.util.HashSet;
import java.util.Map;

public class HashMapSet<K, V> extends AbstractMapCollection<K, V, ESet<V>> implements MapSet<K, V> {
    public HashMapSet() {
        super();
    }

    public HashMapSet(Map<? extends K, ? extends ESet<V>> m) {
        super(m);
    }

    @Override
    protected ESet<V> createCollection() {
        return ESet.of(new HashSet<>());
    }

    @Override
    protected ESet<V> copyCollection(ESet<V> original) {
        return ESet.of(new HashSet<>(original));
    }

    @Override
    protected MapCollection<K, V, ESet<V>> constructMap() {
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
