package nl.underkoen.adventofcode.utils;

import com.google.common.collect.Streams;
import nl.underkoen.adventofcode.general.map.counter.HashMapCounter;
import nl.underkoen.adventofcode.general.map.counter.MapCounter;
import nl.underkoen.adventofcode.general.stream.EStream;
import nl.underkoen.adventofcode.general.tuple.BiHolder;
import org.apache.commons.lang3.function.TriFunction;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamUtils {
    public static <T, R> EStream<R> mapWithPrev(Stream<T> s, BiFunction<T, T, R> mapper) {
        boolean parallel = s.isParallel();
        Spliterator<T> spliterator = s.spliterator();

        return EStream.of(
                StreamSupport.stream(new Spliterators.AbstractSpliterator<R>(
                        spliterator.estimateSize(),
                        spliterator.characteristics() & ~(Spliterator.SIZED | Spliterator.SUBSIZED)) {
                    Consumer<? super R> current;
                    T prev;
                    final Consumer<T> adapter = t -> {
                        if (prev != null) {
                            current.accept(mapper.apply(prev, t));
                        }
                        prev = t;
                    };

                    @Override
                    public boolean tryAdvance(Consumer<? super R> action) {
                        current = action;
                        try {
                            return spliterator.tryAdvance(adapter);
                        } finally {
                            current = null;
                        }
                    }
                }, parallel).onClose(s::close)
        );
    }

    public static <T, R> EStream<R> mapWithPrev(Stream<T> s, TriFunction<T, T, T, R> mapper) {
        boolean parallel = s.isParallel();
        Spliterator<T> spliterator = s.spliterator();

        return EStream.of(
                StreamSupport.stream(new Spliterators.AbstractSpliterator<R>(
                        spliterator.estimateSize(),
                        spliterator.characteristics() & ~(Spliterator.SIZED | Spliterator.SUBSIZED)) {
                    Consumer<? super R> current;
                    T prev1;
                    T prev2;
                    final Consumer<T> adapter = t -> {
                        if (prev1 != null && prev2 != null) {
                            current.accept(mapper.apply(prev2, prev1, t));
                        }
                        prev2 = prev1;
                        prev1 = t;
                    };

                    @Override
                    public boolean tryAdvance(Consumer<? super R> action) {
                        current = action;
                        try {
                            return spliterator.tryAdvance(adapter);
                        } finally {
                            current = null;
                        }
                    }
                }, parallel).onClose(s::close)
        );
    }

    public static <T> EStream<BiHolder<Integer, T>> indexed(Stream<T> s) {
        return EStream.of(Streams.mapWithIndex(s, (o, i) -> new BiHolder<>((int) i, o)));
    }

    public static <T> EStream<EStream<T>> grouped(Stream<T> s, long amount) {
        boolean parallel = s.isParallel();
        Spliterator<T> spliterator = s.spliterator();

        return EStream.of(
                StreamSupport.stream(new Spliterators.AbstractSpliterator<EStream<T>>(
                        spliterator.estimateSize(),
                        spliterator.characteristics() & ~(Spliterator.SIZED | Spliterator.SUBSIZED)) {
                    Consumer<? super EStream<T>> current;
                    Stream.Builder<T> builder = Stream.builder();
                    long i = 0;
                    final Consumer<T> adapter = t -> {
                        builder.accept(t);
                        if (++i >= amount) {
                            i = 0;
                            current.accept(EStream.of(builder.build()));
                            builder = Stream.builder();
                        }
                    };

                    @Override
                    public boolean tryAdvance(Consumer<? super EStream<T>> action) {
                        current = action;
                        try {
                            return spliterator.tryAdvance(adapter);
                        } finally {
                            current = null;
                        }
                    }
                }, parallel).onClose(s::close)
        );
    }

    public static <T, R> EStream<R> mapPairs(Stream<T> s, BiFunction<T, T, R> mapper) {
        return EStream.of(s)
                .grouped(2)
                .map(EStream::toList)
                .map(l -> mapper.apply(l.get(0), l.get(1)));
    }

    public static <T> void forEachPair(Stream<T> s, BiConsumer<T, T> consumer) {
        StreamUtils.mapPairs(s, BiHolder::new)
                .forEach(p -> consumer.accept(p.getKey(), p.getValue()));
    }

    public static <T> EStream<T> duplicates(Stream<T> s) {
        return duplicates(s, 2);
    }

    public static <T> EStream<T> duplicates(Stream<T> s, int amount) {
        boolean parallel = s.isParallel();
        Spliterator<T> spliterator = s.spliterator();

        return EStream.of(
                StreamSupport.stream(new Spliterators.AbstractSpliterator<T>(
                        spliterator.estimateSize(),
                        spliterator.characteristics() & ~(Spliterator.SIZED | Spliterator.SUBSIZED | Spliterator.DISTINCT)) {
                    Consumer<? super T> current;
                    final MapCounter<T, Integer> map = new HashMapCounter<>(0);
                    final Consumer<T> adapter = t -> {
                        if (map.increase(t) + 1 == amount) {
                            current.accept(t);
                        }
                    };

                    @Override
                    public boolean tryAdvance(Consumer<? super T> action) {
                        current = action;
                        try {
                            return spliterator.tryAdvance(adapter);
                        } finally {
                            current = null;
                        }
                    }
                }, parallel).onClose(s::close)
        );
    }

    public static <T> EStream<T> filter(Stream<T> s, Predicate<T> predicate, Consumer<T> onRemove) {
        return EStream.of(s)
                .filter(t -> {
                    if (predicate.test(t)) {
                        return true;
                    } else {
                        onRemove.accept(t);
                        return false;
                    }
                });
    }

    public static <T> EStream<T> reverse(Stream<T> s) {
        return EStream.of(s)
                .indexed()
                .sorted(Comparator.comparingInt(b -> -b.getKey()))
                .map(BiHolder::getValue);
    }
}
