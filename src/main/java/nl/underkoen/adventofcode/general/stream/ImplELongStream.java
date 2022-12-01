package nl.underkoen.adventofcode.general.stream;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

class ImplELongStream extends ImplEStream<Long> implements ELongStream {
    public ImplELongStream(Stream<Long> stream) {
        super(stream);
    }

    @Override
    public ELongStream filter(Predicate<? super Long> predicate) {
        return ELongStream.of(super.filter(predicate));
    }

    @Override
    public ELongStream distinct() {
        return ELongStream.of(super.distinct());
    }

    @Override
    public ELongStream sorted() {
        return ELongStream.of(super.sorted());
    }

    @Override
    public ELongStream sorted(Comparator<? super Long> comparator) {
        return ELongStream.of(super.sorted(comparator));
    }

    @Override
    public ELongStream peek(Consumer<? super Long> action) {
        return ELongStream.of(super.peek(action));
    }

    @Override
    public ELongStream limit(long maxSize) {
        return ELongStream.of(super.limit(maxSize));
    }

    @Override
    public ELongStream skip(long n) {
        return ELongStream.of(super.skip(n));
    }

    @Override
    public ELongStream takeWhile(Predicate<? super Long> predicate) {
        return ELongStream.of(super.takeWhile(predicate));
    }

    @Override
    public ELongStream dropWhile(Predicate<? super Long> predicate) {
        return ELongStream.of(super.dropWhile(predicate));
    }

    @Nonnull
    @Override
    public ELongStream sequential() {
        return ELongStream.of(super.sequential());
    }

    @Nonnull
    @Override
    public ELongStream parallel() {
        return ELongStream.of(super.parallel());
    }

    @Nonnull
    @Override
    public ELongStream unordered() {
        return ELongStream.of(super.unordered());
    }

    @Nonnull
    @Override
    public ELongStream onClose(Runnable closeHandler) {
        return ELongStream.of(super.onClose(closeHandler));
    }
}
