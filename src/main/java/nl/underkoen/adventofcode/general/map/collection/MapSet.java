package nl.underkoen.adventofcode.general.map.collection;

import java.util.Set;
import java.util.function.Supplier;

public interface MapSet<K, V> extends MapCollection<K, V, Set<V>> {
    /**
     * Inverts this map to a MapSet, see {@link #invert(Supplier)} for more information
     *
     * @see #invert(Supplier)
     */
    MapSet<V, K> invert();

    /**
     * {@inheritDoc}
     */
    @Override
    MapSet<K, V> deepCopy();

    /**
     * {@inheritDoc}
     */
    @Override
    MapSet<K, V> shallowCopy();
}
