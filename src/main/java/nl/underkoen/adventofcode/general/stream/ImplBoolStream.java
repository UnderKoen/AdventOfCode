package nl.underkoen.adventofcode.general.stream;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

class ImplBoolStream extends ImplEStream<Boolean> implements BoolStream {
    public ImplBoolStream(Stream<Boolean> stream) {
        super(stream);
    }

    @Override
    public BoolStream filter(Predicate<? super Boolean> predicate) {
        return BoolStream.of(super.filter(predicate));
    }

    @Override
    public BoolStream distinct() {
        return BoolStream.of(super.distinct());
    }

    @Override
    public BoolStream sorted() {
        return BoolStream.of(super.sorted());
    }

    @Override
    public BoolStream sorted(Comparator<? super Boolean> comparator) {
        return BoolStream.of(super.sorted(comparator));
    }

    @Override
    public BoolStream peek(Consumer<? super Boolean> action) {
        return BoolStream.of(super.peek(action));
    }

    @Override
    public BoolStream limit(long maxSize) {
        return BoolStream.of(super.limit(maxSize));
    }

    @Override
    public BoolStream skip(long n) {
        return BoolStream.of(super.skip(n));
    }

    @Override
    public BoolStream takeWhile(Predicate<? super Boolean> predicate) {
        return BoolStream.of(super.takeWhile(predicate));
    }

    @Override
    public BoolStream dropWhile(Predicate<? super Boolean> predicate) {
        return BoolStream.of(super.dropWhile(predicate));
    }

    @Nonnull
    @Override
    public BoolStream sequential() {
        return BoolStream.of(super.sequential());
    }

    @Nonnull
    @Override
    public BoolStream parallel() {
        return BoolStream.of(super.parallel());
    }

    @Nonnull
    @Override
    public BoolStream unordered() {
        return BoolStream.of(super.unordered());
    }

    @Nonnull
    @Override
    public BoolStream onClose(Runnable closeHandler) {
        return BoolStream.of(super.onClose(closeHandler));
    }
}
