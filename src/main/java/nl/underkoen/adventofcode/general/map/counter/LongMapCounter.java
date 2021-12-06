package nl.underkoen.adventofcode.general.map.counter;

import javax.annotation.Nonnull;
import java.util.Map;

public class LongMapCounter<K> extends HashMapCounter<K, Long> {
    public LongMapCounter() {
        super(0L);
    }

    public LongMapCounter(@Nonnull Long defaultValue) {
        super(defaultValue);
    }

    public LongMapCounter(@Nonnull Long defaultValue, @Nonnull Number defaultIncrement) {
        super(defaultValue, defaultIncrement);
    }

    public LongMapCounter(Map<? extends K, ? extends Long> m, @Nonnull Long defaultValue, @Nonnull Number defaultIncrement) {
        super(m, defaultValue, defaultIncrement);
    }

    public LongMapCounter(Map<? extends K, ? extends Long> m, @Nonnull Long defaultValue) {
        super(m, defaultValue);
    }

    public LongMapCounter(HashMapCounter<? extends K, ? extends Long> m) {
        super(m);
    }
}
