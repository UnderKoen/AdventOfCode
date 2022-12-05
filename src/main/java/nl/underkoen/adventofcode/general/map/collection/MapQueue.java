package nl.underkoen.adventofcode.general.map.collection;

import nl.underkoen.adventofcode.general.sets.ESet;

import java.util.Queue;
import java.util.function.Supplier;

public interface MapQueue<K, V> extends MapCollection<K, V, Queue<V>> {
    /**
     * Inverts this map to a MapSet, see {@link #invert(Supplier)} for more information
     *
     * @see #invert(Supplier)
     */
    MapQueue<V, K> invert();

    /**
     * {@inheritDoc}
     */
    @Override
    MapQueue<K, V> deepCopy();

    /**
     * {@inheritDoc}
     */
    @Override
    MapQueue<K, V> shallowCopy();
}
