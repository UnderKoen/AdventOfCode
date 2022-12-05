package nl.underkoen.adventofcode.general.map.collection;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class HashMapCollection<K, V, C extends Collection<V>> extends AbstractMapCollection<K, V, C> implements MapCollection<K, V, C> {
    private final Supplier<C> createCollection;
    private final Function<C, C> copyCollection;

    public HashMapCollection(Supplier<C> createCollection, Function<C, C> copyCollection) {
        this.createCollection = createCollection;
        this.copyCollection = copyCollection;
    }

    public HashMapCollection(Map<? extends K, ? extends C> m, Supplier<C> createCollection, Function<C, C> copyCollection) {
        super(m);
        this.createCollection = createCollection;
        this.copyCollection = copyCollection;
    }

    @Override
    protected C createCollection() {
        return createCollection.get();
    }

    @Override
    protected C copyCollection(C original) {
        return copyCollection.apply(original);
    }

    @Override
    protected MapCollection<K, V, C> constructMap() {
        return new HashMapCollection<>(createCollection, copyCollection);
    }

    @Override
    public MapCollection<K, V, C> shallowCopy() {
        return new HashMapCollection<>(this, createCollection, copyCollection);
    }
}
