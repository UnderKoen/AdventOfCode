package nl.underkoen.adventofcode.general.map.collection;

import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;

public class HashMapQueue<K, V> extends AbstractMapCollection<K, V, Queue<V>> implements MapQueue<K, V> {
    public HashMapQueue() {
        super();
    }

    public HashMapQueue(Map<? extends K, ? extends Queue<V>> m) {
        super(m);
    }

    @Override
    protected Queue<V> createCollection() {
        return new ArrayDeque<>();
    }

    @Override
    protected Queue<V> copyCollection(Queue<V> original) {
        return new ArrayDeque<>(original);
    }

    @Override
    protected MapCollection<K, V, Queue<V>> constructMap() {
        return new HashMapQueue<>();
    }

    @Override
    public MapQueue<V, K> invert() {
        return invert(HashMapQueue::new);
    }

    @Override
    public MapQueue<K, V> shallowCopy() {
        return new HashMapQueue<>(this);
    }

    @Override
    public MapQueue<K, V> deepCopy() {
        HashMapQueue<K, V> map = new HashMapQueue<>();

        deepCopyOver(map);

        return map;
    }
}
