package nl.underkoen.adventofcode.general.sets;

import lombok.RequiredArgsConstructor;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

@RequiredArgsConstructor
public class ImplESet<T> implements ESet<T> {
    private final Set<T> set;

    @Override
    public int size() {
        return set.size();
    }

    @Override
    public boolean isEmpty() {
        return set.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return set.contains(o);
    }

    @Nonnull
    @Override
    public Iterator<T> iterator() {
        return set.iterator();
    }

    @Nonnull
    @Override
    public Object[] toArray() {
        return set.toArray();
    }

    @Nonnull
    @Override
    public <T1> T1[] toArray(@Nonnull T1[] a) {
        return set.toArray(a);
    }

    @Override
    public boolean add(T t) {
        return set.add(t);
    }

    @Override
    public boolean remove(Object o) {
        return set.remove(o);
    }

    @Override
    public boolean containsAll(@Nonnull Collection<?> c) {
        return set.containsAll(c);
    }

    @Override
    public boolean addAll(@Nonnull Collection<? extends T> c) {
        return set.addAll(c);
    }

    @Override
    public boolean retainAll(@Nonnull Collection<?> c) {
        return set.retainAll(c);
    }

    @Override
    public boolean removeAll(@Nonnull Collection<?> c) {
        return set.removeAll(c);
    }

    @Override
    public void clear() {
        set.clear();
    }
}
