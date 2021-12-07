package nl.underkoen.adventofcode.solutions.year2021;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.List;
import java.util.stream.LongStream;

public class Day07 extends Solution {
    @Getter private final int day = 7;
    @Getter private final int year = 2021;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{359648, 100727924};
    }

    @Override
    protected void run(Input input) {
        List<Long> nums = input.asNumbers().toList();

        a = nums.stream().distinct()
                .mapToLong(l -> nums.stream()
                        .mapToLong(v -> Math.abs(l - v))
                        .sum()
                )
                .min()
                .orElse(0);

        b = nums.stream().distinct()
                .mapToLong(l -> nums.stream()
                        .mapToLong(v -> Math.abs(l - v))
                        .map(Day07::summation)
                        .sum()
                )
                .min()
                .orElse(0);
    }

    public static long summation(long i) {
        return LongStream.range(1, i + 1).sum();
    }
}