package nl.underkoen.adventofcode.general.map.collection;

import org.jetbrains.annotations.Contract;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

public interface MapCollection<K, V, C extends Collection<V>> extends Map<K, C> {
    /**
     * Add a value to the collection of a given key.
     *
     * @param key   the key associated with the collection
     * @param value the value to add to the associated collection
     */
    @Contract(mutates = "this")
    void add(K key, V value);

    @Contract(mutates = "this")
    default void addAll(K key, Collection<V> value) {
        for (V v : value) {
            add(key, v);
        }
    }

    default void addAll(Map<K, ? extends Collection<V>> map) {
        map.forEach(this::addAll);
    }

    /**
     * Deletes a value from the collection of a given key.
     *
     * @param key   the key associated with the collection
     * @param value the value to remove from the associated collection
     * @return {@code true} if a value was removed
     */
    @Contract(mutates = "this")
    boolean delete(K key, V value);

    /**
     * <p>Inverts a MapCollection so that all keys that were associated with a value are inverted.</p>
     * <p>This means that all keys that have a value in common will be associated in reverse</p>
     * <br>
     * <p>So for example:</p>
     * <p><code>1 -> [a,b,c], 2 -> [b], 3 -> [b, c]</code></p>
     * <br>
     * <p>Becomes:</p>
     * <p><code>a -> [1], b -> [1, 2, 3], c -> [2, 3]</code></p>
     *
     * @param supplier Creates the map
     * @return The inverted map
     */
    @Contract(mutates = "param")
    <R extends MapCollection<V, K, ? extends Collection<K>>> R invert(Supplier<R> supplier);

    /**
     * Copy all values, but with a new map and lists
     *
     * @return a deep copy of this MapCollection
     */
    @Contract(pure = true, value = " -> new")
    MapCollection<K, V, C> deepCopy();

    /**
     * Copy all values, but with a new map
     *
     * @return a shallow copy of this MapCollection
     */
    @Contract(pure = true, value = " -> new")
    MapCollection<K, V, C> shallowCopy();

    /**
     * <p>Reduces a MapCollection to a Map.</p>
     * Where every key has a unique value.
     * When a key has multiple values it is reduced by removing all values that are already assigned to other key.
     * When an element still has multiple values it is not included in the map.</p>
     * <br><br>
     * <p>So for example:</p>
     * <p><code>1 -> [a,b,c], 2 -> [b], 3 -> [b, c]</code></p>
     * <br>
     * <p>Becomes:</p>
     * <p><code>1 -> a, 2 -> b, 3 -> c</code></p>
     * <br>
     * <p>Here is b removed from 3 because b is assigned to 2
     * Therefore c is assigned to 3
     * And a is assigned to 1
     *
     * @return a map with all reduced values
     * @see #reduceSelf()
     */
    @Contract(pure = true, value = " -> new")
    Map<K, V> reduce();

    /**
     * <p>Reduces a MapCollection to a Map <b>and removes elements from this.</b></p>
     * Where every key has a unique value.
     * When a key has multiple values it is reduced by removing all values that are already assigned to other key.
     * When an element still has multiple values it is not included in the map.</p>
     * <br><br>
     * <p>So for example:</p>
     * <p><code>1 -> [a,b,c], 2 -> [b], 3 -> [b, c]</code></p>
     * <br>
     * <p>Becomes:</p>
     * <p><code>1 -> a, 2 -> b, 3 -> c</code></p>
     * <br>
     * <p>Here is b removed from 3 because b is assigned to 2
     * Therefore c is assigned to 3
     * And a is assigned to 1
     *
     * @return a map with all reduced values
     * @see #reduce()
     */
    @Contract(mutates = "this", value = "-> new")
    Map<K, V> reduceSelf();
}
