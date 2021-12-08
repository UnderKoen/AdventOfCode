package nl.underkoen.adventofcode.utils;

import nl.underkoen.adventofcode.general.map.counter.HashMapCounter;
import nl.underkoen.adventofcode.general.map.counter.MapCounter;
import nl.underkoen.adventofcode.general.stream.EStream;
import nl.underkoen.adventofcode.general.tuple.BiHolder;
import org.apache.commons.lang3.function.TriFunction;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.function.Consumer;
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
        boolean parallel = s.isParallel();
        Spliterator<T> spliterator = s.spliterator();

        return EStream.of(
                StreamSupport.stream(new Spliterators.AbstractSpliterator<BiHolder<Integer, T>>(
                        spliterator.estimateSize(),
                        spliterator.characteristics() & ~(Spliterator.SIZED | Spliterator.SUBSIZED)) {
                    Consumer<? super BiHolder<Integer, T>> current;
                    int i;
                    final Consumer<T> adapter = t -> {
                        current.accept(new BiHolder<>(i++, t));
                    };

                    @Override
                    public boolean tryAdvance(Consumer<? super BiHolder<Integer, T>> action) {
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
        boolean parallel = s.isParallel();
        Spliterator<T> spliterator = s.spliterator();

        return EStream.of(
                StreamSupport.stream(new Spliterators.AbstractSpliterator<R>(
                        spliterator.estimateSize(),
                        spliterator.characteristics() & ~(Spliterator.SIZED | Spliterator.SUBSIZED)) {
                    Consumer<? super R> current;
                    T prev;
                    final Consumer<T> adapter = t -> {
                        if (prev == null) prev = t;
                        else {
                            current.accept(mapper.apply(prev, t));
                            prev = null;
                        }
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
                    final MapCounter<T, Integer> map = new HashMapCounter<T, Integer>(0);
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
}
