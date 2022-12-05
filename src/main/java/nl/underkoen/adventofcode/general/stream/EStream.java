package nl.underkoen.adventofcode.general.stream;

import com.google.common.primitives.Booleans;
import com.google.common.primitives.Longs;
import nl.underkoen.adventofcode.general.map.counter.LongMapCounter;
import nl.underkoen.adventofcode.general.sets.ESet;
import nl.underkoen.adventofcode.general.tuple.BiHolder;
import nl.underkoen.adventofcode.utils.StreamUtils;
import org.apache.commons.lang3.function.TriFunction;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface EStream<T> extends Stream<T> {
    static <T> EStream<T> of(Stream<T> stream) {
        if (stream instanceof EStream<T> s) return s;
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

    static EStream<EBoolStream> of(Boolean[][] array) {
        return of(Arrays.stream(array))
                .map(Arrays::stream)
                .map(EBoolStream::of);
    }

    static EStream<EBoolStream> of(boolean[][] array) {
        return of(Arrays.stream(array))
                .map(Booleans::asList)
                .map(EBoolStream::of);
    }

    static EStream<ELongStream> of(Long[][] array) {
        return of(Arrays.stream(array))
                .map(Arrays::stream)
                .map(ELongStream::of);
    }

    static EStream<ELongStream> of(long[][] array) {
        return of(Arrays.stream(array))
                .map(Longs::asList)
                .map(ELongStream::of);
    }

    static EStream<Character> of(String s) {
        return of(s.chars().mapToObj(c -> (char) c));
    }

    static <K, V> EStream<BiHolder<K, V>> of(Map<K, V> map) {
        return EStream.of(map.entrySet())
                .map(BiHolder::new);
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

    //TODO would be nice if this could return an LongStream if this was orignal an LongStream,
    //TODO that will also have the result of making extensions easier for new types.
    default EStream<EStream<T>> grouped(long amount) {
        return StreamUtils.grouped(this, amount);
    }

    default <R> EStream<R> mapPairs(BiFunction<T, T, R> mapper) {
        return StreamUtils.mapPairs(this, mapper);
    }

    default void forEachPair(BiConsumer<T, T> consumer) {
        StreamUtils.forEachPair(this, consumer);
    }

    default EStream<T> duplicates() {
        return StreamUtils.duplicates(this);
    }

    default EStream<T> duplicates(int amount) {
        return StreamUtils.duplicates(this, amount);
    }

    default EStream<T> filter(Predicate<T> predicate, Consumer<T> onRemove) {
        return StreamUtils.filter(this, predicate, onRemove);
    }

    default EStream<T> reverse() {
        return StreamUtils.reverse(this);
    }

    default EBoolStream mapToBool(PredicateFunction<T> mapper) {
        return EBoolStream.of(this.map(mapper));
    }

    default EBoolStream flatMapToBool(Function<T, Stream<Boolean>> mapper) {
        return EBoolStream.of(this.flatMap(mapper));
    }

    default EBoolStream mapMultiToBool(BiConsumer<T, Consumer<Boolean>> mapper) {
        return EBoolStream.of(this.mapMulti(mapper));
    }

    default ELongStream mapToELong(Function<T, Long> mapper) {
        return ELongStream.of(this.map(mapper));
    }

    default ELongStream flatMapToELong(Function<T, Stream<Long>> mapper) {
        return ELongStream.of(this.flatMap(mapper));
    }

    default ELongStream mapMultiELong(BiConsumer<T, Consumer<Long>> mapper) {
        return ELongStream.of(this.mapMulti(mapper));
    }

    default List<T> toMutable() {
        return this.collect(Collectors.toList());
    }

    default ESet<T> toSet() {
        return ESet.of(this.collect(Collectors.toSet()));
    }

    default <K, V> Map<K, V> toMap(Function<T, K> keyMap, Function<T, V> valueMapper) {
        return this.collect(Collectors.toMap(keyMap, valueMapper));
    }

    default <V> Map<T, V> toMap(Function<T, V> valueMapper) {
        return this.collect(Collectors.toMap(k -> k, valueMapper));
    }

    default LongMapCounter<T> counted() {
        return collect(Collectors.toMap(k -> k, k -> 1L, Long::sum, LongMapCounter::new));
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
