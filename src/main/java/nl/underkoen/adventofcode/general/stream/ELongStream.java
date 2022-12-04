package nl.underkoen.adventofcode.general.stream;

import com.google.common.primitives.Longs;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public interface ELongStream extends EStream<Long> {
    static ELongStream of(Stream<Long> stream) {
        return new ImplELongStream(stream);
    }

    static ELongStream of(LongStream stream) {
        return of(stream.boxed());
    }

    static ELongStream of(Collection<Long> collection) {
        return of(collection.stream());
    }

    static ELongStream of(Long[] array) {
        return of(Arrays.stream(array));
    }

    static ELongStream of(long[] array) {
        return of(Longs.asList(array));
    }

    static ELongStream range(long startInclusive, final long endExclusive) {
        return of(LongStream.range(startInclusive, endExclusive));
    }

    static ELongStream rangeIncl(long startInclusive, final long endInclusive) {
        return range(startInclusive, endInclusive + 1);
    }

    default long sum() {
        return reduce(0L, Long::sum);
    }

    default Optional<Double> average() {
        return Optional.of(collect(Collectors.averagingLong(l -> l)));
    }

    default Optional<Long> min() {
        return min(Long::compareTo);
    }

    default Optional<Long> max() {
        return min(Long::compareTo);
    }

    default LongStream unboxed() {
        return mapToLong(l -> l);
    }

    //=====EStream=====
    @Override
    default ELongStream duplicates() {
        return ELongStream.of(EStream.super.duplicates());
    }

    @Override
    default ELongStream duplicates(int amount) {
        return ELongStream.of(EStream.super.duplicates(amount));
    }

    @Override
    default ELongStream filter(Predicate<Long> predicate, Consumer<Long> onRemove) {
        return ELongStream.of(EStream.super.filter(predicate, onRemove));
    }

    @Override
    default ELongStream reverse() {
        return ELongStream.of(EStream.super.reverse());
    }

    //=====Original=====
    @Override
    ELongStream filter(Predicate<? super Long> predicate);

    @Override
    ELongStream distinct();

    @Override
    ELongStream sorted();

    @Override
    ELongStream sorted(Comparator<? super Long> comparator);

    @Override
    ELongStream peek(Consumer<? super Long> action);

    @Override
    ELongStream limit(long maxSize);

    @Override
    ELongStream skip(long n);

    @Override
    ELongStream takeWhile(Predicate<? super Long> predicate);

    @Override
    ELongStream dropWhile(Predicate<? super Long> predicate);

    @Nonnull
    @Override
    ELongStream sequential();

    @Nonnull
    @Override
    ELongStream parallel();

    @Nonnull
    @Override
    ELongStream unordered();

    @Nonnull
    @Override
    ELongStream onClose(Runnable closeHandler);
}
