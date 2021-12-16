package nl.underkoen.adventofcode.general.stream;

import com.google.common.primitives.Booleans;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Predicate;
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

    //=====EStream=====
    @Override
    default BoolStream duplicates() {
        return BoolStream.of(EStream.super.duplicates());
    }

    @Override
    default BoolStream duplicates(int amount) {
        return BoolStream.of(EStream.super.duplicates(amount));
    }

    @Override
    default BoolStream filter(Predicate<Boolean> predicate, Consumer<Boolean> onRemove) {
        return BoolStream.of(EStream.super.filter(predicate, onRemove));
    }

    @Override
    default BoolStream reverse() {
        return BoolStream.of(EStream.super.reverse());
    }

    //=====Original=====
    @Override
    BoolStream filter(Predicate<? super Boolean> predicate);

    @Override
    BoolStream distinct();

    @Override
    BoolStream sorted();

    @Override
    BoolStream sorted(Comparator<? super Boolean> comparator);

    @Override
    BoolStream peek(Consumer<? super Boolean> action);

    @Override
    BoolStream limit(long maxSize);

    @Override
    BoolStream skip(long n);

    @Override
    BoolStream takeWhile(Predicate<? super Boolean> predicate);

    @Override
    BoolStream dropWhile(Predicate<? super Boolean> predicate);

    @Nonnull
    @Override
    BoolStream sequential();

    @Nonnull
    @Override
    BoolStream parallel();

    @Nonnull
    @Override
    BoolStream unordered();

    @Nonnull
    @Override
    BoolStream onClose(Runnable closeHandler);
}
