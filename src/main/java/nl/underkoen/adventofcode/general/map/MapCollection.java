package nl.underkoen.adventofcode.general.map;

import java.util.Collection;
import java.util.Map;

public interface MapCollection<K, V, C extends Collection<V>> extends Map<K, C> {
    void add(K key, V value);

//    boolean remove(K key, V value);

}
