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
     * Reworked https://rosettacode.org/wiki/Chinese_remainder_theorem#Java
     */
    public static long chineseRemainder(List<Long> n, List<Long> a) {
        long prod = n.stream().reduce(1L, (i, j) -> i * j);

        long p, sm = 0;
        for (int i = 0; i < n.size(); i++) {
            p = prod / n.get(i);
            sm += a.get(i) * mulInv(p, n.get(i)) * p;
        }
        return sm % prod;
    }

    public static long mulInv(long a, long b) {
        if (b == 1) return 1;

        long b0 = b;
        long x0 = 0;
        long x1 = 1;


        while (a > 1) {
            long q = a / b;
            long amb = a % b;
            a = b;
            b = amb;
            long xqx = x1 - q * x0;
            x1 = x0;
            x0 = xqx;
        }

        if (x1 < 0)
            x1 += b0;

        return x1;
    }
}
