package nl.underkoen.adventofcode.general.map.collection;

import nl.underkoen.adventofcode.general.sets.ESet;

import java.util.Stack;
import java.util.function.Supplier;

public interface MapStack<K, V> extends MapCollection<K, V, Stack<V>> {
    /**
     * Inverts this map to a MapSet, see {@link #invert(Supplier)} for more information
     *
     * @see #invert(Supplier)
     */
    MapStack<V, K> invert();

    /**
     * {@inheritDoc}
     */
    @Override
    MapStack<K, V> deepCopy();

    /**
     * {@inheritDoc}
     */
    @Override
    MapStack<K, V> shallowCopy();

    default V pop(K key) {
        return get(key).pop();
    };
}
