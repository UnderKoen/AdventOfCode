package nl.underkoen.adventofcode.general.map.counter;

import nl.underkoen.adventofcode.general.stream.EMapStream;
import nl.underkoen.adventofcode.utils.NumberUtils;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.Map;

public interface MapCounter<K, N extends Number & Comparable<N>> extends Map<K, N> {
    default N sum() {
        return values().stream().reduce(NumberUtils.addition::compute).orElseThrow();
    }

    /**
     * @return the value previously assigned to the field, if no value was previously assigned the default is returned
     */
    @Contract(mutates = "this")
    default N increase(K key) {
        return increase(key, 1);
    }

    /**
     * @return the value previously assigned to the field, if no value was previously assigned the default is returned
     */
    @Contract(mutates = "this")
    default N increase(K key, Number with) {
        return compute(key, with, NumberUtils.addition);
    }

    /**
     * @return the value previously assigned to the field, if no value was previously assigned the default is returned
     */
    @Contract(mutates = "this")
    default N decrease(K key) {
        return decrease(key, 1);
    }

    /**
     * @return the value previously assigned to the field, if no value was previously assigned the default is returned
     */
    @Contract(mutates = "this")
    default N decrease(K key, Number with) {
        return compute(key, with, NumberUtils.subtraction);
    }

    /**
     * @return the value previously assigned to the field, if no value was previously assigned the default is returned
     */
    @Contract(mutates = "this")
    N compute(K key, Number with, NumberUtils.NumberComputation computation);

    N max();

    N min();

    default K maxKey() {
        return keySet().stream().max(Comparator.comparing(this::get)).orElse(null);
    }

    default K minKey() {
        return keySet().stream().min(Comparator.comparing(this::get)).orElse(null);
    }

    @Contract(mutates = "this", value = "null -> fail")
    default void increaseAll(Iterable<K> values) {
        values.forEach(this::increase);
    }

    @Contract(pure = true, value = "_, _ -> new")
    MapCounter<K, N> with(@Nonnull N defaultValue, @Nonnull Number defaultIncrease);

    @Contract(pure = true, value = "_ -> new")
    MapCounter<K, N> with(@Nonnull N defaultValue);

    default EMapStream<K, N> stream() {
        return EMapStream.of(this);
    }
}
