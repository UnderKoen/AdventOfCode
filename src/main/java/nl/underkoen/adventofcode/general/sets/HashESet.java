package nl.underkoen.adventofcode.general.sets;

import java.util.Collection;
import java.util.HashSet;

public class HashESet<T> extends ImplESet<T> {
    public HashESet() {
        super(new HashSet<>());
    }

    public HashESet(Collection<? extends T> c) {
        super(new HashSet<>(c));
    }

    public HashESet(int initialCapacity) {
        super(new HashSet<>(initialCapacity));
    }

    public HashESet(int initialCapacity, float loadFactor) {
        super(new HashSet<>(initialCapacity, loadFactor));
    }
}
