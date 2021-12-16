package nl.underkoen.adventofcode.general.stream;

import nl.underkoen.adventofcode.general.tuple.BiHolder;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

class ImplMapStream<K, V> extends ImplEStream<BiHolder<K, V>> implements MapStream<K, V> {
    public ImplMapStream(Stream<BiHolder<K, V>> stream) {
        super(stream);
    }

    @Override
    public MapStream<K, V> filter(Predicate<? super BiHolder<K, V>> predicate) {
        return MapStream.of(super.filter(predicate));
    }

    @Override
    public MapStream<K, V> distinct() {
        return MapStream.of(super.distinct());
    }

    @Override
    public MapStream<K, V> sorted() {
        return MapStream.of(super.sorted());
    }

    @Override
    public MapStream<K, V> sorted(Comparator<? super BiHolder<K, V>> comparator) {
        return MapStream.of(super.sorted(comparator));
    }

    @Override
    public MapStream<K, V> peek(Consumer<? super BiHolder<K, V>> action) {
        return MapStream.of(super.peek(action));
    }

    @Override
    public MapStream<K, V> limit(long maxSize) {
        return MapStream.of(super.limit(maxSize));
    }

    @Override
    public MapStream<K, V> skip(long n) {
        return MapStream.of(super.skip(n));
    }

    @Override
    public MapStream<K, V> takeWhile(Predicate<? super BiHolder<K, V>> predicate) {
        return MapStream.of(super.takeWhile(predicate));
    }

    @Override
    public MapStream<K, V> dropWhile(Predicate<? super BiHolder<K, V>> predicate) {
        return MapStream.of(super.dropWhile(predicate));
    }

    @Nonnull
    @Override
    public MapStream<K, V> sequential() {
        return MapStream.of(super.sequential());
    }

    @Nonnull
    @Override
    public MapStream<K, V> parallel() {
        return MapStream.of(super.parallel());
    }

    @Nonnull
    @Override
    public MapStream<K, V> unordered() {
        return MapStream.of(super.unordered());
    }

    @Nonnull
    @Override
    public MapStream<K, V> onClose(Runnable closeHandler) {
        return MapStream.of(super.onClose(closeHandler));
    }
}
