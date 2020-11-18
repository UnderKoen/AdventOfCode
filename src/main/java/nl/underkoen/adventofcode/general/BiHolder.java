package nl.underkoen.adventofcode.general;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;
import java.util.function.Function;


/**
 * Created by Under_Koen on 11/12/2019.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class BiHolder<T, U> implements Map.Entry<T, U> {
    public static <K, V> Function<K, BiHolder<K, V>> hold(Function<K, V> function) {
        return t -> new BiHolder<>(t, function.apply(t));
    }

    public static <K, V, O> Function<BiHolder<O, V>, BiHolder<K, V>> keepValue(Function<BiHolder<O, V>, K> function) {
        return t -> new BiHolder<>(function.apply(t), t.getValue());
    }

    public static <K, V, O> Function<BiHolder<K, O>, BiHolder<K, V>> keepKey(Function<BiHolder<K, O>, V> function) {
        return t -> new BiHolder<>(t.getKey(), function.apply(t));
    }

    private T key;
    private U value;

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
}
