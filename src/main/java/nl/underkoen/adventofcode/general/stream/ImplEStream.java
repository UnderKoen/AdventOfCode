package nl.underkoen.adventofcode.general.stream;

import lombok.RequiredArgsConstructor;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

@RequiredArgsConstructor
public class ImplEStream<T> implements EStream<T> {
    private final Stream<T> stream;

    @Override
    public EStream<T> filter(Predicate<? super T> predicate) {
        return new ImplEStream<>(stream.filter(predicate));
    }

    @Override
    public <R> EStream<R> map(Function<? super T, ? extends R> mapper) {
        return new ImplEStream<>(stream.map(mapper));
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super T> mapper) {
        return stream.mapToInt(mapper);
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super T> mapper) {
        return stream.mapToLong(mapper);
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
        return stream.mapToDouble(mapper);
    }

    @Override
    public <R> EStream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return new ImplEStream<>(stream.flatMap(mapper));
    }

    @Override
    public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        return stream.flatMapToInt(mapper);
    }

    @Override
    public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        return stream.flatMapToLong(mapper);
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        return stream.flatMapToDouble(mapper);
    }

    @Override
    public <R> EStream<R> mapMulti(BiConsumer<? super T, ? super Consumer<R>> mapper) {
        return new ImplEStream<>(stream.mapMulti(mapper));
    }

    @Override
    public EStream<T> distinct() {
        return new ImplEStream<>(stream.distinct());
    }

    @Override
    public EStream<T> sorted() {
        return new ImplEStream<>(stream.sorted());
    }

    @Override
    public EStream<T> sorted(Comparator<? super T> comparator) {
        return new ImplEStream<>(stream.sorted(comparator));
    }

    @Override
    public EStream<T> peek(Consumer<? super T> action) {
        return new ImplEStream<>(stream.peek(action));
    }

    @Override
    public EStream<T> limit(long maxSize) {
        return new ImplEStream<>(stream.limit(maxSize));
    }

    @Override
    public EStream<T> skip(long n) {
        return new ImplEStream<>(stream.skip(n));
    }

    @Override
    public EStream<T> takeWhile(Predicate<? super T> predicate) {
        return new ImplEStream<>(stream.takeWhile(predicate));
    }

    @Override
    public EStream<T> dropWhile(Predicate<? super T> predicate) {
        return new ImplEStream<>(stream.dropWhile(predicate));
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        stream.forEach(action);
    }

    @Override
    public void forEachOrdered(Consumer<? super T> action) {
        stream.forEach(action);
    }

    @Nonnull
    @Override
    public Object[] toArray() {
        return stream.toArray();
    }

    @Nonnull
    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        return stream.toArray(generator);
    }

    @Override
    public T reduce(T identity, BinaryOperator<T> accumulator) {
        return stream.reduce(identity, accumulator);
    }

    @Nonnull
    @Override
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        return stream.reduce(accumulator);
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) {
        return stream.reduce(identity, accumulator, combiner);
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
        return stream.collect(supplier, accumulator, combiner);
    }

    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        return stream.collect(collector);
    }

    @Nonnull
    @Override
    public Optional<T> min(Comparator<? super T> comparator) {
        return stream.min(comparator);
    }

    @Nonnull
    @Override
    public Optional<T> max(Comparator<? super T> comparator) {
        return stream.max(comparator);
    }

    @Override
    public long count() {
        return stream.count();
    }

    @Override
    public boolean anyMatch(Predicate<? super T> predicate) {
        return stream.anyMatch(predicate);
    }

    @Override
    public boolean allMatch(Predicate<? super T> predicate) {
        return stream.allMatch(predicate);
    }

    @Override
    public boolean noneMatch(Predicate<? super T> predicate) {
        return stream.noneMatch(predicate);
    }

    @Nonnull
    @Override
    public Optional<T> findFirst() {
        return stream.findFirst();
    }

    @Nonnull
    @Override
    public Optional<T> findAny() {
        return stream.findAny();
    }

    @Nonnull
    @Override
    public Iterator<T> iterator() {
        return stream.iterator();
    }

    @Nonnull
    @Override
    public Spliterator<T> spliterator() {
        return stream.spliterator();
    }

    @Override
    public boolean isParallel() {
        return stream.isParallel();
    }

    @Nonnull
    @Override
    public EStream<T> sequential() {
        return new ImplEStream<>(stream.sequential());
    }

    @Nonnull
    @Override
    public EStream<T> parallel() {
        return new ImplEStream<>(stream.parallel());
    }

    @Nonnull
    @Override
    public EStream<T> unordered() {
        return new ImplEStream<>(stream.unordered());
    }

    @Nonnull
    @Override
    public EStream<T> onClose(Runnable closeHandler) {
        return new ImplEStream<>(stream.onClose(closeHandler));
    }

    @Override
    public void close() {
        stream.close();
    }

    @Override
    public IntStream mapMultiToInt(BiConsumer<? super T, ? super IntConsumer> mapper) {
        return stream.mapMultiToInt(mapper);
    }

    @Override
    public LongStream mapMultiToLong(BiConsumer<? super T, ? super LongConsumer> mapper) {
        return stream.mapMultiToLong(mapper);
    }

    @Override
    public DoubleStream mapMultiToDouble(BiConsumer<? super T, ? super DoubleConsumer> mapper) {
        return stream.mapMultiToDouble(mapper);
    }

    @Override
    public List<T> toList() {
        return stream.toList();
    }

    @Override
    public int hashCode() {
        return stream.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return stream.equals(obj);
    }

    @Override
    public String toString() {
        return stream.toString();
    }
}
