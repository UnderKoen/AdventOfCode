package nl.underkoen.adventofcode.general.map.counter;

import nl.underkoen.adventofcode.utils.NumberUtils;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class HashMapCounter<K, N extends Number> extends HashMap<K, N> implements MapCounter<K, N> {
    private final N defaultValue;
    private final Number defaultIncrement;

    public HashMapCounter(@Nonnull N defaultValue, @Nonnull Number defaultIncrement) {
        super();
        this.defaultValue = defaultValue;
        this.defaultIncrement = defaultIncrement;
    }

    public HashMapCounter(@Nonnull N defaultValue) {
        this(defaultValue, 1);
    }

    public HashMapCounter(Map<? extends K, ? extends N> m, @Nonnull N defaultValue, @Nonnull Number defaultIncrement) {
        super(m);
        this.defaultValue = defaultValue;
        this.defaultIncrement = defaultIncrement;
    }

    public HashMapCounter(Map<? extends K, ? extends N> m, @Nonnull N defaultValue) {
        this(m, defaultValue, 1);
    }

    public HashMapCounter(HashMapCounter<? extends K, ? extends N> m) {
        this(m, m.defaultValue, m.defaultIncrement);
    }

    @Override
    public N increase(K key) {
        return increase(key, defaultIncrement);
    }

    @Override
    public N decrease(K key) {
        return decrease(key, defaultIncrement);
    }

    @Override
    public N compute(K key, Number with, NumberUtils.NumberComputation computation) {
        N n = getOrDefault(key, defaultValue);

        put(key, computation.compute(n, with));
        return n;
    }


    @Override
    public HashMapCounter<K, N> with(@Nonnull N defaultValue, @Nonnull Number defaultIncrease) {
        return new HashMapCounter<K, N>(this, defaultValue, defaultIncrease);
    }

    @Override
    public HashMapCounter<K, N> with(@Nonnull N defaultValue) {
        return new HashMapCounter<K, N>(this, defaultValue, defaultIncrement);
    }
}
