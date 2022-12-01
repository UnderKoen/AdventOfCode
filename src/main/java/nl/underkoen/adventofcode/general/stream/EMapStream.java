package nl.underkoen.adventofcode.general.stream;

import nl.underkoen.adventofcode.general.tuple.BiHolder;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.Map;
import java.util.function.*;
import java.util.stream.Stream;

public interface EMapStream<K, V> extends EStream<BiHolder<K, V>> {
    static <K, V> EMapStream<K, V> of(Stream<BiHolder<K, V>> stream) {
        return new ImplEMapStream<>(stream);
    }

    static <K, V> EMapStream<K, V> of(Map<K, V> map) {
        return of(map.entrySet().stream().map(BiHolder::new));
    }

    static <K, V> EMapStream<K, V> of(Stream<K> stream, Function<K, V> valueMapper) {
        return of(stream.map(BiHolder.hold(valueMapper)));
    }

    default Map<K, V> toMap() {
        return this.collect(BiHolder.toMap());
    }

    default <R> EStream<R> map(BiFunction<K, V, R> mapper) {
        return map(b -> mapper.apply(b.getKey(), b.getValue()));
    }

    default <R> EMapStream<R, V> mapKey(BiFunction<K, V, R> mapper) {
        return map(mapper, (k, v) -> v);
    }

    default <R> EMapStream<R, V> mapKey(Function<K, R> mapper) {
        return map((k, v) -> mapper.apply(k), (k, v) -> v);
    }

    default <R> EMapStream<K, R> mapValue(BiFunction<K, V, R> mapper) {
        return map((k, v) -> k, mapper);
    }

    default <R> EMapStream<K, R> mapValue(Function<V, R> mapper) {
        return map((k, v) -> k, (k, v) -> mapper.apply(v));
    }

    default <RK, RV> EMapStream<RK, RV> map(BiFunction<K, V, RK> keyMapper, BiFunction<K, V, RV> valueMapper) {
        return EMapStream.of(map((k, v) -> new BiHolder<>(keyMapper.apply(k, v), valueMapper.apply(k, v))));
    }

    default <RK, RV> EMapStream<RK, RV> map(Function<K, RK> keyMapper, Function<V, RV> valueMapper) {
        return map((k, v) -> keyMapper.apply(k), (k, v) -> valueMapper.apply(v));
    }

    default EMapStream<K, V> mapMulti(TriConsumer<K, V, BiConsumer<K, V>> mapper) {
        return EMapStream.of(mapMulti((pair, consumer) -> mapper.accept(pair.getKey(), pair.getValue(), (k, v) -> consumer.accept(new BiHolder<>(k, v)))));
    }

    default EMapStream<V, K> swap() {
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
    default EMapStream<K, V> duplicates() {
        return EMapStream.of(EStream.super.duplicates());
    }

    @Override
    default EMapStream<K, V> duplicates(int amount) {
        return EMapStream.of(EStream.super.duplicates(amount));
    }

    @Override
    default EMapStream<K, V> filter(Predicate<BiHolder<K, V>> predicate, Consumer<BiHolder<K, V>> onRemove) {
        return EMapStream.of(EStream.super.filter(predicate, onRemove));
    }

    @Override
    default EMapStream<K, V> reverse() {
        return EMapStream.of(EStream.super.reverse());
    }

    //=====Original=====
    @Override
    EMapStream<K, V> filter(Predicate<? super BiHolder<K, V>> predicate);

    @Override
    EMapStream<K, V> distinct();

    @Override
    EMapStream<K, V> sorted();

    @Override
    EMapStream<K, V> sorted(Comparator<? super BiHolder<K, V>> comparator);

    @Override
    EMapStream<K, V> peek(Consumer<? super BiHolder<K, V>> action);

    @Override
    EMapStream<K, V> limit(long maxSize);

    @Override
    EMapStream<K, V> skip(long n);

    @Override
    EMapStream<K, V> takeWhile(Predicate<? super BiHolder<K, V>> predicate);

    @Override
    EMapStream<K, V> dropWhile(Predicate<? super BiHolder<K, V>> predicate);

    @Nonnull
    @Override
    EMapStream<K, V> sequential();

    @Nonnull
    @Override
    EMapStream<K, V> parallel();

    @Nonnull
    @Override
    EMapStream<K, V> unordered();

    @Nonnull
    @Override
    EMapStream<K, V> onClose(Runnable closeHandler);

    @FunctionalInterface
    interface TriConsumer<T1, T2, T3> {
        void accept(T1 t1, T2 t2, T3 t3);
    }
}
