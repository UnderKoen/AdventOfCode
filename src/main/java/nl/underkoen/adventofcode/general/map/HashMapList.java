package nl.underkoen.adventofcode.general.map;

import java.util.ArrayList;
import java.util.List;

public class HashMapList<K, V> extends AbstractMapCollection<K, V, List<V>> implements MapList<K, V>{
    public HashMapList() {
        super(ArrayList::new);
    }
}
