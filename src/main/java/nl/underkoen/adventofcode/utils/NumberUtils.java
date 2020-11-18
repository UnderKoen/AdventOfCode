package nl.underkoen.adventofcode.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class NumberUtils {
    public long toNumber(int[] digits) {
        return toNumber(digits, digits.length);
    }

    public long toNumber(int[] digits, int max) {
        long r = 0;
        int until = Math.min(digits.length, max) - 1;
        for (int i = 0; i <= until; i++) {
            r += Math.pow(10, until - i) * digits[i];
        }
        return r;
    }
}
