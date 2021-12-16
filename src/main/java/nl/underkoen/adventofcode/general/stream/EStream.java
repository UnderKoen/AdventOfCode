package nl.underkoen.adventofcode.general.stream;

import com.google.common.primitives.Booleans;
import nl.underkoen.adventofcode.general.map.counter.LongMapCounter;
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

    default Set<T> toSet() {
        return this.collect(Collectors.toSet());
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
