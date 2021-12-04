package nl.underkoen.adventofcode.general.stream;

import com.google.common.primitives.Booleans;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

public interface BoolStream extends EStream<Boolean> {
    static BoolStream of(Stream<Boolean> stream) {
        return new ImplBoolStream(stream);
    }

    static BoolStream of(Collection<Boolean> collection) {
        return of(collection.stream());
    }

    static BoolStream of(Boolean[] array) {
        return of(Arrays.stream(array));
    }

    static BoolStream of(boolean[] array) {
        return of(Booleans.asList(array));
    }

    default boolean all() {
        return this.allMatch(b -> b);
    }

    default boolean any() {
        return this.anyMatch(b -> b);
    }
}
