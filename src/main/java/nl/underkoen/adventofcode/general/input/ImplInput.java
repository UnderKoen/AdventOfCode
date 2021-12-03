package nl.underkoen.adventofcode.general.input;

import lombok.RequiredArgsConstructor;
import nl.underkoen.adventofcode.general.stream.EStream;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

@RequiredArgsConstructor
public class ImplInput implements Input {
    private final List<String> list;

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    public Iterator<String> iterator() {
        return list.iterator();
    }

    public Object[] toArray() {
        return list.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }

    public boolean add(String e) {
        return list.add(e);
    }

    public boolean remove(Object o) {
        return list.remove(o);
    }

    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    public boolean addAll(Collection<? extends String> c) {
        return list.addAll(c);
    }

    public boolean addAll(int index, Collection<? extends String> c) {
        return list.addAll(index, c);
    }

    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    public void replaceAll(UnaryOperator<String> operator) {
        list.replaceAll(operator);
    }

    public void sort(Comparator<? super String> c) {
        list.sort(c);
    }

    public void clear() {
        list.clear();
    }

    public String get(int index) {
        return list.get(index);
    }

    public String set(int index, String element) {
        return list.set(index, element);
    }

    public void add(int index, String element) {
        list.add(index, element);
    }

    public String remove(int index) {
        return list.remove(index);
    }

    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    public ListIterator<String> listIterator() {
        return list.listIterator();
    }

    public ListIterator<String> listIterator(int index) {
        return list.listIterator(index);
    }

    public List<String> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }

    public Spliterator<String> spliterator() {
        return list.spliterator();
    }

    public <T> T[] toArray(IntFunction<T[]> generator) {
        return list.toArray(generator);
    }

    @Override
    public boolean removeIf(Predicate<? super String> filter) {
        return list.removeIf(filter);
    }

    @Override
    public EStream<String> stream() {
        return EStream.of(list.stream());
    }

    @Override
    public EStream<String> parallelStream() {
        return EStream.of(list.parallelStream());
    }

    @Override
    public void forEach(Consumer<? super String> action) {
        list.forEach(action);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return list.equals(obj);
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
