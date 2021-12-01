package nl.underkoen.adventofcode.utils;

import nl.underkoen.adventofcode.general.stream.EStream;
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
}
