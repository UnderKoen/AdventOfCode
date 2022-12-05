package nl.underkoen.adventofcode.general.map.collection;

import nl.underkoen.adventofcode.general.sets.ESet;

import java.util.HashSet;
import java.util.Map;
import java.util.Stack;

public class HashMapStack<K, V> extends AbstractMapCollection<K, V, Stack<V>> implements MapStack<K, V> {
    public HashMapStack() {
        super();
    }

    public HashMapStack(Map<? extends K, ? extends Stack<V>> m) {
        super(m);
    }

    @Override
    protected Stack<V> createCollection() {
        return new Stack<>();
    }

    @Override
    protected Stack<V> copyCollection(Stack<V> original) {
        Stack<V> stack = new Stack<>();
        stack.addAll(original);
        return stack;
    }

    @Override
    protected MapCollection<K, V, Stack<V>> constructMap() {
        return new HashMapStack<>();
    }

    @Override
    public MapStack<V, K> invert() {
        return invert(HashMapStack::new);
    }

    @Override
    public MapStack<K, V> shallowCopy() {
        return new HashMapStack<>(this);
    }

    @Override
    public MapStack<K, V> deepCopy() {
        HashMapStack<K, V> map = new HashMapStack<>();

        deepCopyOver(map);

        return map;
    }
}
