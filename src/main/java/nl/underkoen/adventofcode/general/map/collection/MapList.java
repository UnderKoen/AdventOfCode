package nl.underkoen.adventofcode.general.map.collection;

import java.util.List;
import java.util.function.Supplier;

public interface MapList<K, V> extends MapCollection<K, V, List<V>> {
    /**
     * Inverts this map to a MapList, see {@link #invert(Supplier)} for more information
     *
     * @see #invert(Supplier)
     */
    MapList<V, K> invert();

    /**
     * {@inheritDoc}
     */
    @Override
    MapList<K, V> deepCopy();

    /**
     * {@inheritDoc}
     */
    @Override
    MapList<K, V> shallowCopy();
}
