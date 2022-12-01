package nl.underkoen.adventofcode.general.stream;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

class ImplEBoolStream extends ImplEStream<Boolean> implements EBoolStream {
    public ImplEBoolStream(Stream<Boolean> stream) {
        super(stream);
    }

    @Override
    public EBoolStream filter(Predicate<? super Boolean> predicate) {
        return EBoolStream.of(super.filter(predicate));
    }

    @Override
    public EBoolStream distinct() {
        return EBoolStream.of(super.distinct());
    }

    @Override
    public EBoolStream sorted() {
        return EBoolStream.of(super.sorted());
    }

    @Override
    public EBoolStream sorted(Comparator<? super Boolean> comparator) {
        return EBoolStream.of(super.sorted(comparator));
    }

    @Override
    public EBoolStream peek(Consumer<? super Boolean> action) {
        return EBoolStream.of(super.peek(action));
    }

    @Override
    public EBoolStream limit(long maxSize) {
        return EBoolStream.of(super.limit(maxSize));
    }

    @Override
    public EBoolStream skip(long n) {
        return EBoolStream.of(super.skip(n));
    }

    @Override
    public EBoolStream takeWhile(Predicate<? super Boolean> predicate) {
        return EBoolStream.of(super.takeWhile(predicate));
    }

    @Override
    public EBoolStream dropWhile(Predicate<? super Boolean> predicate) {
        return EBoolStream.of(super.dropWhile(predicate));
    }

    @Nonnull
    @Override
    public EBoolStream sequential() {
        return EBoolStream.of(super.sequential());
    }

    @Nonnull
    @Override
    public EBoolStream parallel() {
        return EBoolStream.of(super.parallel());
    }

    @Nonnull
    @Override
    public EBoolStream unordered() {
        return EBoolStream.of(super.unordered());
    }

    @Nonnull
    @Override
    public EBoolStream onClose(Runnable closeHandler) {
        return EBoolStream.of(super.onClose(closeHandler));
    }
}
