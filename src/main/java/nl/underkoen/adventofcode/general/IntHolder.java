package nl.underkoen.adventofcode.general;

import lombok.*;

/**
 * Created by Under_Koen on 13/12/2019.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class IntHolder {
    private int value = 0;

    public int getValue() {
        return value;
    }

    public int addValue(int value) {
        int old = this.value;
        setValue(getValue() + value);
        return old;
    }
}
