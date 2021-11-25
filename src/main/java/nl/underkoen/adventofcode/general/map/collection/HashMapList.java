package nl.underkoen.adventofcode.general.map.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HashMapList<K, V> extends AbstractMapCollection<K, V, List<V>> implements MapList<K, V> {
    public HashMapList() {
        super();
    }

    public HashMapList(Map<? extends K, ? extends List<V>> m) {
        super(m);
    }

    @Override
    protected List<V> createCollection() {
        return new ArrayList<>();
    }

    @Override
    protected List<V> copyCollection(List<V> original) {
        return new ArrayList<>(original);
    }

    @Override
    protected MapCollection<K, V, List<V>> constructMap() {
        return new HashMapList<>();
    }

    @Override
    public MapList<V, K> invert() {
        return invert(HashMapList::new);
    }

    @Override
    public MapList<K, V> shallowCopy() {
        return new HashMapList<>(this);
    }

    @Override
    public MapList<K, V> deepCopy() {
        HashMapList<K, V> map = new HashMapList<>();

        deepCopyOver(map);

        return map;
    }
}
