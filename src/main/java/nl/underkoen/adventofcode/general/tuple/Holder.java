package nl.underkoen.adventofcode.general.tuple;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by Under_Koen on 11/12/2019.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Holder<T> {
    private T value;

    public T setValue(T value) {
        T old = this.value;
        this.value = value;
        return old;
    }
}
