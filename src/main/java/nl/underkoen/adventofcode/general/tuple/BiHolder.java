package nl.underkoen.adventofcode.general.tuple;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;


/**
 * Created by Under_Koen on 11/12/2019.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class BiHolder<K, V> implements Map.Entry<K, V> {
    private K key;
    private V value;

    public static <K, V> Function<K, BiHolder<K, V>> hold(Function<K, V> function) {
        return t -> new BiHolder<>(t, function.apply(t));
    }

    public static <K, V, O> Function<Map.Entry<O, V>, BiHolder<K, V>> keepValue(Function<Map.Entry<O, V>, K> function) {
        return t -> new BiHolder<>(function.apply(t), t.getValue());
    }

    public static <K, V, O> Function<Map.Entry<K, O>, BiHolder<K, V>> keepKey(Function<Map.Entry<K, O>, V> function) {
        return t -> new BiHolder<>(t.getKey(), function.apply(t));
    }

    public static <K, V> Collector<BiHolder<K, V>, ?, Map<K, V>> toMap() {
        return Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue);
    }

    public BiHolder(Map.Entry<K, V> entry) {
        this(entry.getKey(), entry.getValue());
    }

    public K setKey(K value) {
        K old = this.key;
        this.key = value;
        return old;
    }

    @Override
    public V setValue(V value) {
        V old = this.value;
        this.value = value;
        return old;
    }

    public <M> BiHolder<M, V> mapKey(Function<K, M> map) {
        return map(map, v -> v);
    }

    public <M> BiHolder<K, M> mapValue(Function<V, M> map) {
        return map(v -> v, map);
    }

    public <M> BiHolder<K, M> mapValue(BiFunction<K, V, M> map) {
        return new BiHolder<>(key, map.apply(key, value));
    }

    public <MK, MV> BiHolder<MK, MV> map(Function<K, MK> keyMap, Function<V, MV> valueMap) {
        return new BiHolder<>(keyMap.apply(key), valueMap.apply(value));
    }

    public <R> R reduce(BiFunction<K, V, R> reduce) {
        return reduce.apply(key, value);
    }

    @Override
    public String toString() {
        return "{" + key + ", " + value + "}";
    }
}
