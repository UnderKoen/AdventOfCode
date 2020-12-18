package nl.underkoen.adventofcode.utils;

import lombok.experimental.UtilityClass;

import java.util.List;

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

    /**
     * Finds a number where each modulo of mods gives remainders
     * Reworked https://rosettacode.org/wiki/Chinese_remainder_theorem#Java
     */
    public static long chineseRemainder(List<Long> remainders, List<Long> mods) {
        if (remainders.size() != mods.size()) throw new IllegalArgumentException("Both list should be the same size");
        long product = remainders.stream().reduce(1L, (i, j) -> i * j);

        long p, sm = 0;
        for (int i = 0; i < remainders.size(); i++) {
            p = product / remainders.get(i);
            sm += mods.get(i) * mulInv(p, remainders.get(i)) * p;
        }
        return sm % product;
    }

    public static long mulInv(long a, long b) {
        if (b == 1) return 1;

        long b0 = b, x0 = 0, x1 = 1;

        while (a > 1) {
            long q = a / b;
            b = a % (a = b);
            x0 = x1 - q * (x1 = x0);
        }

        return x1 < 0 ? x1 + b0 : x1;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Number> T addNumbers(T a, Number b) {
        if (a instanceof Double) {
            return (T) (Double) (a.doubleValue() + b.doubleValue());
        } else if (a instanceof Float) {
            return (T) (Float) (a.floatValue() + b.floatValue());
        } else if (a instanceof Long) {
            return (T) (Long) (a.longValue() + b.longValue());
        } else {
            return (T) (Integer) (a.intValue() + b.intValue());
        }
    }

    public static <T extends Number> T increase(T a) {
        return addNumbers(a, 1);
    }
}
