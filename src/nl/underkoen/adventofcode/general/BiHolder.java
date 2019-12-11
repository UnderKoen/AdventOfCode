package nl.underkoen.adventofcode.general;

import java.util.Map;

/**
 * Created by Under_Koen on 11/12/2019.
 */
public class BiHolder<T, U> implements Map.Entry<T, U> {
    private T key;
    private U value;

    public BiHolder() {
    }

    public BiHolder(T key, U value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public T getKey() {
        return key;
    }

    public T setKey(T value) {
        T old = this.key;
        this.key = value;
        return old;
    }

    @Override
    public U getValue() {
        return value;
    }

    @Override
    public U setValue(U value) {
        U old = this.value;
        this.value = value;
        return old;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BiHolder)) return false;

        BiHolder<?, ?> biHolder = (BiHolder<?, ?>) o;

        if (getKey() != null ? !getKey().equals(biHolder.getKey()) : biHolder.getKey() != null) return false;
        return getValue() != null ? getValue().equals(biHolder.getValue()) : biHolder.getValue() == null;
    }

    @Override
    public int hashCode() {
        int result = getKey() != null ? getKey().hashCode() : 0;
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        return result;
    }
}
