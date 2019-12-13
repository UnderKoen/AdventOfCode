package nl.underkoen.adventofcode.general;

/**
 * Created by Under_Koen on 13/12/2019.
 */
public class IntHolder {
    private int value;

    public IntHolder() {
    }

    public IntHolder(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public int setValue(int value) {
        int old = this.value;
        this.value = value;
        return old;
    }

    public int addValue(int value) {
        return setValue(getValue() + value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntHolder)) return false;

        IntHolder intHolder = (IntHolder) o;

        return getValue() == intHolder.getValue();
    }

    @Override
    public int hashCode() {
        return getValue();
    }
}
