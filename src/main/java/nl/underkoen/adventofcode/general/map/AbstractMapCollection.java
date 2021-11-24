package nl.underkoen.adventofcode.general.map;

import java.util.*;
import java.util.function.Supplier;

abstract public class AbstractMapCollection<K, V, C extends Collection<V>> extends HashMap<K, C> implements MapCollection<K, V, C> {
    private final Supplier<C> collectionMaker;

    public AbstractMapCollection(Supplier<C> collectionMaker) {
        super();
        this.collectionMaker = collectionMaker;
    }

    @Override
    public void add(K key, V value) {
        computeIfAbsent(key, k -> collectionMaker.get());
        get(key).add(value);
    }

//    public boolean remove(K key, V value) {
//        if (!containsKey(key)) return false;
//        return get(key).remove(value);
//    }

//    public <CK extends Collection<K>> AbstractMapCollection<V, K, CK> invert(Supplier<CK> maker) {
//        AbstractMapCollection<V, K, CK> r = new AbstractMapCollection<>(maker);
//        for (Map.Entry<K, C> entry : entrySet()) {
//            for (V value : entry.getValue()) {
//                r.add(value, entry.getKey());
//            }
//        }
//        return r;
//    }

//    public MapCollection<K, V, C> deepCopy() {
//        MapCollection<K, V, C> copy = new MapCollection<>();
//        forEach((k, v) -> copy.put(k, supplier.apply(v)));
//        return copy;
//    }
}