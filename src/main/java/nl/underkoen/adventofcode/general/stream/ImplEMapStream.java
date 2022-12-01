package nl.underkoen.adventofcode.general.stream;

import nl.underkoen.adventofcode.general.tuple.BiHolder;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

class ImplEMapStream<K, V> extends ImplEStream<BiHolder<K, V>> implements EMapStream<K, V> {
    public ImplEMapStream(Stream<BiHolder<K, V>> stream) {
        super(stream);
    }

    @Override
    public EMapStream<K, V> filter(Predicate<? super BiHolder<K, V>> predicate) {
        return EMapStream.of(super.filter(predicate));
    }

    @Override
    public EMapStream<K, V> distinct() {
        return EMapStream.of(super.distinct());
    }

    @Override
    public EMapStream<K, V> sorted() {
        return EMapStream.of(super.sorted());
    }

    @Override
    public EMapStream<K, V> sorted(Comparator<? super BiHolder<K, V>> comparator) {
        return EMapStream.of(super.sorted(comparator));
    }

    @Override
    public EMapStream<K, V> peek(Consumer<? super BiHolder<K, V>> action) {
        return EMapStream.of(super.peek(action));
    }

    @Override
    public EMapStream<K, V> limit(long maxSize) {
        return EMapStream.of(super.limit(maxSize));
    }

    @Override
    public EMapStream<K, V> skip(long n) {
        return EMapStream.of(super.skip(n));
    }

    @Override
    public EMapStream<K, V> takeWhile(Predicate<? super BiHolder<K, V>> predicate) {
        return EMapStream.of(super.takeWhile(predicate));
    }

    @Override
    public EMapStream<K, V> dropWhile(Predicate<? super BiHolder<K, V>> predicate) {
        return EMapStream.of(super.dropWhile(predicate));
    }

    @Nonnull
    @Override
    public EMapStream<K, V> sequential() {
        return EMapStream.of(super.sequential());
    }

    @Nonnull
    @Override
    public EMapStream<K, V> parallel() {
        return EMapStream.of(super.parallel());
    }

    @Nonnull
    @Override
    public EMapStream<K, V> unordered() {
        return EMapStream.of(super.unordered());
    }

    @Nonnull
    @Override
    public EMapStream<K, V> onClose(Runnable closeHandler) {
        return EMapStream.of(super.onClose(closeHandler));
    }
}
