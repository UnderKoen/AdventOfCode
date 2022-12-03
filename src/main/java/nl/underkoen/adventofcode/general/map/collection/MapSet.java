package nl.underkoen.adventofcode.general.map.collection;

import nl.underkoen.adventofcode.general.sets.ESet;

import java.util.function.Supplier;

public interface MapSet<K, V> extends MapCollection<K, V, ESet<V>> {
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
