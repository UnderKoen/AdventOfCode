package nl.underkoen.adventofcode.general.map;

import nl.underkoen.adventofcode.utils.NumberUtils;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class MapCounter<K, N extends Number> extends HashMap<K, N> {
    private final N defaultValue;
    private final Number defaultIncrease;

    public MapCounter(@Nonnull N defaultValue, @Nonnull Number defaultIncrease) {
        super();
        this.defaultValue = defaultValue;
        this.defaultIncrease = defaultIncrease;
    }

    public MapCounter(@Nonnull N defaultValue) {
        this(defaultValue, 1);
    }

    public MapCounter(Map<? extends K, ? extends N> m, @Nonnull N defaultValue, @Nonnull Number defaultIncrease) {
        super(m);
        this.defaultValue = defaultValue;
        this.defaultIncrease = defaultIncrease;
    }

    public MapCounter(Map<? extends K, ? extends N> m, @Nonnull N defaultValue) {
        this(m, defaultValue, 1);
    }

    public MapCounter(MapCounter<? extends K, ? extends N> m) {
        this(m, m.defaultValue, m.defaultIncrease);
    }

    /**
     * @return the value previously assigned to the field, if no value was previously assigned the default is returned
     */
    public N increase(K key) {
        return increase(key, defaultIncrease);
    }

    /**
     * @return the value previously assigned to the field, if no value was previously assigned the default is returned
     */
    public N increase(K key, Number with) {
        N n = getOrDefault(key, defaultValue);

        put(key, NumberUtils.addNumbers(n, with));
        return n;
    }

    public void increaseAll(Iterable<K> values) {
        values.forEach(this::increase);
    }

    public MapCounter<K, N> with(@Nonnull N defaultValue, @Nonnull Number defaultIncrease) {
        return new MapCounter<K, N>(this, defaultValue, defaultIncrease);
    }

    public MapCounter<K, N> with(@Nonnull N defaultValue) {
        return new MapCounter<K, N>(this, defaultValue, defaultIncrease);
    }
}
