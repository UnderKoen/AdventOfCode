package nl.underkoen.adventofcode.general.map.counter;

import nl.underkoen.adventofcode.utils.NumberUtils;

import javax.annotation.Nonnull;
import java.util.Map;

public interface MapCounter<K, N extends Number> extends Map<K, N> {
    /**
     * @return the value previously assigned to the field, if no value was previously assigned the default is returned
     */
    default N increase(K key) {
        return increase(key, 1);
    }

    /**
     * @return the value previously assigned to the field, if no value was previously assigned the default is returned
     */
    default N increase(K key, Number with) {
        return compute(key, with, NumberUtils.addition);
    }

    /**
     * @return the value previously assigned to the field, if no value was previously assigned the default is returned
     */
    default N decrease(K key) {
        return decrease(key, 1);
    }

    /**
     * @return the value previously assigned to the field, if no value was previously assigned the default is returned
     */
    default N decrease(K key, Number with) {
        return compute(key, with, NumberUtils.subtraction);
    }

    /**
     * @return the value previously assigned to the field, if no value was previously assigned the default is returned
     */
    N compute(K key, Number with, NumberUtils.NumberComputation computation);

    default void increaseAll(Iterable<K> values) {
        values.forEach(this::increase);
    }

    MapCounter<K, N> with(@Nonnull N defaultValue, @Nonnull Number defaultIncrease);

    MapCounter<K, N> with(@Nonnull N defaultValue);
}
