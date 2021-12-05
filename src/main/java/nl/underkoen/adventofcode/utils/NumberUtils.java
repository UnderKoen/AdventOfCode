package nl.underkoen.adventofcode.utils;

import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Contract;

import java.util.List;
import java.util.function.BiFunction;

@UtilityClass
public class NumberUtils {
    public static final NumberComputation addition = new NumberComputation(
            Integer::sum,
            Double::sum,
            Float::sum,
            Long::sum,
            (s1, s2) -> (short) (s1 + s2),
            (b1, b2) -> (byte) (b1 + b2));
    public static final NumberComputation subtraction = new NumberComputation(
            (i1, i2) -> (i1 - i2),
            (d1, d2) -> (d1 - d2),
            (f1, f2) -> (f1 - f2),
            (l1, l2) -> (l1 - l2),
            (s1, s2) -> (short) (s1 - s2),
            (b1, b2) -> (byte) (b1 - b2));
    public static final NumberComputation multiplication = new NumberComputation(
            (i1, i2) -> (i1 * i2),
            (d1, d2) -> (d1 * d2),
            (f1, f2) -> (f1 * f2),
            (l1, l2) -> (l1 * l2),
            (s1, s2) -> (short) (s1 * s2),
            (b1, b2) -> (byte) (b1 * b2));
    public static final NumberComputation division = new NumberComputation(
            (i1, i2) -> (i1 / i2),
            (d1, d2) -> (d1 / d2),
            (f1, f2) -> (f1 / f2),
            (l1, l2) -> (l1 / l2),
            (s1, s2) -> (short) (s1 / s2),
            (b1, b2) -> (byte) (b1 / b2));
    public static final NumberComputation modulo = new NumberComputation(
            (i1, i2) -> (i1 % i2),
            (d1, d2) -> (d1 % d2),
            (f1, f2) -> (f1 % f2),
            (l1, l2) -> (l1 % l2),
            (s1, s2) -> (short) (s1 % s2),
            (b1, b2) -> (byte) (b1 % b2));

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

    public static long gcd(long a, long b) {
        while (b > 0) b = a % (a = b);
        return a;
    }

    public static long gcd(long[] input) {
        long result = input[0];
        for (int i = 1; i < input.length; i++) result = gcd(result, input[i]);
        return result;
    }

    @RequiredArgsConstructor
    public static final class NumberComputation {
        private final BiFunction<Integer, Integer, Integer> intComputation;
        private final BiFunction<Double, Double, Double> doubleComputation;
        private final BiFunction<Float, Float, Float> floatComputation;
        private final BiFunction<Long, Long, Long> longComputation;
        private final BiFunction<Short, Short, Short> shortComputation;
        private final BiFunction<Byte, Byte, Byte> byteComputation;

        @SuppressWarnings("unchecked")
        @Contract(pure = true, value = "_, null -> fail; null, _ -> fail")
        public <T extends Number> T compute(T a, Number b) {
            if (a == null || b == null) throw new NullPointerException();

            if (a instanceof Double) {
                return (T) doubleComputation.apply(a.doubleValue(), b.doubleValue());
            } else if (a instanceof Float) {
                return (T) floatComputation.apply(a.floatValue(), b.floatValue());
            } else if (a instanceof Long) {
                return (T) longComputation.apply(a.longValue(), b.longValue());
            } else if (a instanceof Short) {
                return (T) shortComputation.apply(a.shortValue(), b.shortValue());
            } else if (a instanceof Byte) {
                return (T) byteComputation.apply(a.byteValue(), b.byteValue());
            } else {
                return (T) intComputation.apply(a.intValue(), b.intValue());
            }
        }
    }
}
