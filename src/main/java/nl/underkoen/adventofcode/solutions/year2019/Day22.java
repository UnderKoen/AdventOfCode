package nl.underkoen.adventofcode.solutions.year2019;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.tuple.BiHolder;
import nl.underkoen.adventofcode.solutions.Solution;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.function.ToLongBiFunction;

/**
 * Created by Under_Koen on 21/12/2019.
 */
public class Day22 extends Solution {
    @Getter private final int day = 22;
    @Getter private final int year = 2019;

    public static BiHolder<List<ToLongBiFunction<Long, Long>>, BiHolder<BigInteger, BigInteger>> convert(List<String> input, BigInteger cards) {
        List<ToLongBiFunction<Long, Long>> shuffle = new ArrayList<>();

        BigInteger a = BigInteger.ONE;
        BigInteger b = BigInteger.ZERO;

        for (String line : input) {
            String[] pieces = line.split(" (?=-?\\d)");
            switch (pieces[0]) {
                case "deal into new stack":
                    shuffle.add((p, s) -> s - p - 1);
                    a = a.negate().mod(cards);
                    b = b.add(a).mod(cards);
                    break;
                case "deal with increment":
                    int i = Integer.parseInt(pieces[1]);
                    shuffle.add((p, s) -> p * i % s);
                    a = a.multiply(BigInteger.valueOf(i).modInverse(cards)).mod(cards);
                    break;
                case "cut":
                    int j = Integer.parseInt(pieces[1]);
                    shuffle.add((p, s) -> (p + s - j) % s);
                    b = b.add(a.multiply(BigInteger.valueOf(j))).mod(cards);
                    break;
            }
        }
        return new BiHolder<>(shuffle, new BiHolder<>(a, b));
    }

    public static long calc(List<ToLongBiFunction<Long, Long>> shuffle, long pos, long size) {
        for (ToLongBiFunction<Long, Long> function : shuffle) pos = function.applyAsLong(pos, size);
        return pos;
    }

    public static long calcB(BiHolder<BigInteger, BigInteger> factors, BigInteger pos, BigInteger cards, BigInteger times) {
        BigInteger a = factors.getKey();
        BigInteger b = factors.getValue();

        BigInteger multiple = a.modPow(times, cards);
        BigInteger offset = b.multiply(BigInteger.ONE.subtract(multiple)).multiply(BigInteger.ONE.subtract(a).mod(cards).modInverse(cards)).mod(cards);
        return multiple.multiply(pos).add(offset).mod(cards).longValue();
    }

    @Override
    public long[] getCorrectOutput() {
        return new long[]{6129, 71345377301237L};
    }

    @Override
    protected void run(Input input) {
        BigInteger cards = BigInteger.valueOf(119315717514047L);
        BigInteger times = BigInteger.valueOf(101741582076661L);

        var shuffle = convert(input, cards);
        a = calc(shuffle.getKey(), 2019, 10007);
        b = calcB(shuffle.getValue(), BigInteger.valueOf(2020), cards, times);
    }
}
