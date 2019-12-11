package nl.underkoen.adventofcode.general;

/**
 * Created by Under_Koen on 11/12/2019.
 */
public class Holder<T> {
    private T value;

    public Holder() {
    }

    public Holder(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Holder)) return false;

        Holder<?> holder = (Holder<?>) o;

        return getValue() != null ? getValue().equals(holder.getValue()) : holder.getValue() == null;
    }

    @Override
    public int hashCode() {
        return getValue() != null ? getValue().hashCode() : 0;
    }
}
