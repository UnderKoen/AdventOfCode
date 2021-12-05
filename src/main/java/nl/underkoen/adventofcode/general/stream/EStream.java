package nl.underkoen.adventofcode.general.stream;

import com.google.common.primitives.Booleans;
import nl.underkoen.adventofcode.general.tuple.BiHolder;
import nl.underkoen.adventofcode.utils.StreamUtils;
import org.apache.commons.lang3.function.TriFunction;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface EStream<T> extends Stream<T> {
    static <T> EStream<T> of(Stream<T> stream) {
        return new ImplEStream<>(stream);
    }

    static <T> EStream<T> of(Collection<T> collection) {
        return of(collection.stream());
    }

    static <T> EStream<T> of(T[] array) {
        return of(Arrays.stream(array));
    }

    static <T> EStream<EStream<T>> of(T[][] array) {
        return of(Arrays.stream(array))
                .map(Arrays::stream)
                .map(EStream::of);
    }

    static EStream<BoolStream> of(Boolean[][] array) {
        return of(Arrays.stream(array))
                .map(Arrays::stream)
                .map(BoolStream::of);
    }

    static EStream<BoolStream> of(boolean[][] array) {
        return of(Arrays.stream(array))
                .map(Booleans::asList)
                .map(BoolStream::of);
    }

    default <R> EStream<R> mapWithPrev(BiFunction<T, T, R> mapper) {
        return StreamUtils.mapWithPrev(this, mapper);
    }

    default <R> EStream<R> mapWithPrev(TriFunction<T, T, T, R> mapper) {
        return StreamUtils.mapWithPrev(this, mapper);
    }

    default EStream<BiHolder<Integer, T>> indexed() {
        return StreamUtils.indexed(this);
    }

    default <R> EStream<R> mapPairs(BiFunction<T, T, R> mapper) {
        return StreamUtils.mapPairs(this, mapper);
    }

    default BoolStream mapToBool(PredicateFunction<T> mapper) {
        return BoolStream.of(this.map(mapper));
    }

    default BoolStream flatMapToBool(Function<T, Stream<Boolean>> mapper) {
        return BoolStream.of(this.flatMap(mapper));
    }

    default BoolStream mapMultiToBool(BiConsumer<T, Consumer<Boolean>> mapper) {
        return BoolStream.of(this.mapMulti(mapper));
    }

    default List<T> toMutable() {
        return this.collect(Collectors.toList());
    }

    //=====Original=====
    @Override
    EStream<T> filter(Predicate<? super T> predicate);

    @Override
    <R> EStream<R> map(Function<? super T, ? extends R> mapper);

    @Override
    <R> EStream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper);

    @Override
    <R> EStream<R> mapMulti(BiConsumer<? super T, ? super Consumer<R>> mapper);

    @Override
    EStream<T> distinct();

    @Override
    EStream<T> sorted();

    @Override
    EStream<T> sorted(Comparator<? super T> comparator);

    @Override
    EStream<T> peek(Consumer<? super T> action);

    @Override
    EStream<T> limit(long maxSize);

    @Override
    EStream<T> skip(long n);

    @Override
    EStream<T> takeWhile(Predicate<? super T> predicate);

    @Override
    EStream<T> dropWhile(Predicate<? super T> predicate);

    @Nonnull
    @Override
    EStream<T> sequential();

    @Nonnull
    @Override
    EStream<T> parallel();

    @Nonnull
    @Override
    EStream<T> unordered();

    @Nonnull
    @Override
    EStream<T> onClose(Runnable closeHandler);

    interface PredicateFunction<T> extends Function<T, Boolean> {
    }
}
