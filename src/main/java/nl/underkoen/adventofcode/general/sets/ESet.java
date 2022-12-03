package nl.underkoen.adventofcode.general.sets;

import com.google.common.collect.Sets;
import nl.underkoen.adventofcode.general.stream.EStream;
import nl.underkoen.adventofcode.utils.StringUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public interface ESet<T> extends Set<T> {
    static <T> ESet<T> of() {
        return new ImplESet<>(Set.of());
    }
    
    @SafeVarargs
    static <T> ESet<T> of(T... elements) {
        return new ImplESet<>(Set.of(elements));
    }

    static <T> ESet<T> of(Set<T> set) {
        return new ImplESet<>(set);
    }

    static <T> ESet<T> of(Collection<T> set) {
        return of(new HashSet<>(set));
    }

    static ESet<Character> chars(String s) {
        return StringUtils.asSet(s);
    }

    @Override
    default EStream<T> stream() {
        return EStream.of(Set.super.stream());
    }

    @Override
    default EStream<T> parallelStream() {
        return EStream.of(Set.super.parallelStream());
    }

    default ESet<T> union(Set<T> set) {
        return ESet.of(Sets.union(this, set));
    }

    default ESet<T> intersection(Set<T> set) {
        return ESet.of(Sets.intersection(this, set));
    }

    default ESet<T> difference(Set<T> set) {
        return ESet.of(Sets.difference(this, set));
    }

    default ESet<T> symmetricDifference(Set<T> set) {
        return ESet.of(Sets.symmetricDifference(this, set));
    }
}
