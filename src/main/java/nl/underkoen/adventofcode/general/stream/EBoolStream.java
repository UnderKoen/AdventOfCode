package nl.underkoen.adventofcode.general.stream;

import com.google.common.primitives.Booleans;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface EBoolStream extends EStream<Boolean> {
    static EBoolStream of(Stream<Boolean> stream) {
        return new ImplEBoolStream(stream);
    }

    static EBoolStream of(Collection<Boolean> collection) {
        return of(collection.stream());
    }

    static EBoolStream of(Boolean[] array) {
        return of(Arrays.stream(array));
    }

    static EBoolStream of(boolean[] array) {
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
    default EBoolStream duplicates() {
        return EBoolStream.of(EStream.super.duplicates());
    }

    @Override
    default EBoolStream duplicates(int amount) {
        return EBoolStream.of(EStream.super.duplicates(amount));
    }

    @Override
    default EBoolStream filter(Predicate<Boolean> predicate, Consumer<Boolean> onRemove) {
        return EBoolStream.of(EStream.super.filter(predicate, onRemove));
    }

    @Override
    default EBoolStream reverse() {
        return EBoolStream.of(EStream.super.reverse());
    }

    //=====Original=====
    @Override
    EBoolStream filter(Predicate<? super Boolean> predicate);

    @Override
    EBoolStream distinct();

    @Override
    EBoolStream sorted();

    @Override
    EBoolStream sorted(Comparator<? super Boolean> comparator);

    @Override
    EBoolStream peek(Consumer<? super Boolean> action);

    @Override
    EBoolStream limit(long maxSize);

    @Override
    EBoolStream skip(long n);

    @Override
    EBoolStream takeWhile(Predicate<? super Boolean> predicate);

    @Override
    EBoolStream dropWhile(Predicate<? super Boolean> predicate);

    @Nonnull
    @Override
    EBoolStream sequential();

    @Nonnull
    @Override
    EBoolStream parallel();

    @Nonnull
    @Override
    EBoolStream unordered();

    @Nonnull
    @Override
    EBoolStream onClose(Runnable closeHandler);
}
