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
public class BiHolder<T, U> implements Map.Entry<T, U> {
    private T key;
    private U value;

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

    public T setKey(T value) {
        T old = this.key;
        this.key = value;
        return old;
    }

    @Override
    public U setValue(U value) {
        U old = this.value;
        this.value = value;
        return old;
    }

    public <K> BiHolder<K, U> mapKey(Function<T, K> map) {
        return map(map, v -> v);
    }

    public <V> BiHolder<T, V> mapValue(Function<U, V> map) {
        return map(v -> v, map);
    }

    public <K, V> BiHolder<K, V> map(Function<T, K> keyMap, Function<U, V> valueMap) {
        return new BiHolder<>(keyMap.apply(key), valueMap.apply(value));
    }

    public <V> V reduce(BiFunction<T, U, V> reduce) {
        return reduce.apply(key, value);
    }

    @Override
    public String toString() {
        return "{" + key + ", " + value + "}";
    }
}
