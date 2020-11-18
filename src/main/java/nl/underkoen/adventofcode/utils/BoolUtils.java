package nl.underkoen.adventofcode.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BoolUtils {
    public boolean any(boolean[] array) {
        for (boolean b : array) if (b) return true;
        return false;
    }

    public boolean all(boolean[] array) {
        for (boolean b : array) if (!b) return false;
        return true;
    }
}
