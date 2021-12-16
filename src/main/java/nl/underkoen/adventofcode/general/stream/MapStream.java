package nl.underkoen.adventofcode.general.stream;

import nl.underkoen.adventofcode.general.tuple.BiHolder;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.Map;
import java.util.function.*;
import java.util.stream.Stream;

public interface MapStream<K, V> extends EStream<BiHolder<K, V>> {
    static <K, V> MapStream<K, V> of(Stream<BiHolder<K, V>> stream) {
        return new ImplMapStream<>(stream);
    }

    static <K, V> MapStream<K, V> of(Map<K, V> map) {
        return of(map.entrySet().stream().map(BiHolder::new));
    }

    static <K, V> MapStream<K, V> of(Stream<K> stream, Function<K, V> valueMapper) {
        return of(stream.map(BiHolder.hold(valueMapper)));
    }

    default Map<K, V> toMap() {
        return this.collect(BiHolder.toMap());
    }

    default <R> EStream<R> map(BiFunction<K, V, R> mapper) {
        return map(b -> mapper.apply(b.getKey(), b.getValue()));
    }

    default <R> MapStream<R, V> mapKey(BiFunction<K, V, R> mapper) {
        return map(mapper, (k, v) -> v);
    }

    default <R> MapStream<R, V> mapKey(Function<K, R> mapper) {
        return map((k, v) -> mapper.apply(k), (k, v) -> v);
    }

    default <R> MapStream<K, R> mapValue(BiFunction<K, V, R> mapper) {
        return map((k, v) -> k, mapper);
    }

    default <R> MapStream<K, R> mapValue(Function<V, R> mapper) {
        return map((k, v) -> k, (k, v) -> mapper.apply(v));
    }

    default <RK, RV> MapStream<RK, RV> map(BiFunction<K, V, RK> keyMapper, BiFunction<K, V, RV> valueMapper) {
        return MapStream.of(map((k, v) -> new BiHolder<>(keyMapper.apply(k, v), valueMapper.apply(k, v))));
    }

    default <RK, RV> MapStream<RK, RV> map(Function<K, RK> keyMapper, Function<V, RV> valueMapper) {
        return map((k, v) -> keyMapper.apply(k), (k, v) -> valueMapper.apply(v));
    }

    default MapStream<K, V> mapMulti(TriConsumer<K, V, BiConsumer<K, V>> mapper) {
        return MapStream.of(mapMulti((pair, consumer) -> mapper.accept(pair.getKey(), pair.getValue(), (k, v) -> consumer.accept(new BiHolder<>(k, v)))));
    }

    default MapStream<V, K> swap() {
        return map((k, v) -> v, (k, v) -> k);
    }

    default EStream<K> keys() {
        return map((k, v) -> k);
    }

    default EStream<V> values() {
        return map((k, v) -> v);
    }

    //=====EStream=====
    @Override
    default MapStream<K, V> duplicates() {
        return MapStream.of(EStream.super.duplicates());
    }

    @Override
    default MapStream<K, V> duplicates(int amount) {
        return MapStream.of(EStream.super.duplicates(amount));
    }

    @Override
    default MapStream<K, V> filter(Predicate<BiHolder<K, V>> predicate, Consumer<BiHolder<K, V>> onRemove) {
        return MapStream.of(EStream.super.filter(predicate, onRemove));
    }

    @Override
    default MapStream<K, V> reverse() {
        return MapStream.of(EStream.super.reverse());
    }

    //=====Original=====
    @Override
    MapStream<K, V> filter(Predicate<? super BiHolder<K, V>> predicate);

    @Override
    MapStream<K, V> distinct();

    @Override
    MapStream<K, V> sorted();

    @Override
    MapStream<K, V> sorted(Comparator<? super BiHolder<K, V>> comparator);

    @Override
    MapStream<K, V> peek(Consumer<? super BiHolder<K, V>> action);

    @Override
    MapStream<K, V> limit(long maxSize);

    @Override
    MapStream<K, V> skip(long n);

    @Override
    MapStream<K, V> takeWhile(Predicate<? super BiHolder<K, V>> predicate);

    @Override
    MapStream<K, V> dropWhile(Predicate<? super BiHolder<K, V>> predicate);

    @Nonnull
    @Override
    MapStream<K, V> sequential();

    @Nonnull
    @Override
    MapStream<K, V> parallel();

    @Nonnull
    @Override
    MapStream<K, V> unordered();

    @Nonnull
    @Override
    MapStream<K, V> onClose(Runnable closeHandler);

    @FunctionalInterface
    interface TriConsumer<T1, T2, T3> {
        void accept(T1 t1, T2 t2, T3 t3);
    }
}
