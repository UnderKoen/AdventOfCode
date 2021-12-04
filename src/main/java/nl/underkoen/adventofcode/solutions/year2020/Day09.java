package nl.underkoen.adventofcode.solutions.year2020;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day09 extends Solution {
    @Getter private final int day = 9;
    @Getter private final int year = 2020;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{1930745883, 268878261};
    }

    @Override
    protected void run(Input input) {
        List<Long> numbers = InputUtils.asNumbers(input).collect(Collectors.toList());

        for (int i = 25; i < numbers.size(); i++) {
            long num = numbers.get(i);
            List<Long> sub = numbers.subList(Math.max(i - 25, 0), i);

            boolean valid = sub.stream().anyMatch(j -> sub.contains(num - j));
            if (!valid) {
                a = num;

                for (int upper = i - 1; upper > 0; upper--) {
                    long sum = 0;
                    for (int lower = upper; lower > 0; lower--) {
                        sum += numbers.get(lower);
                        if (sum == a) {
                            List<Long> longs = numbers.subList(lower, upper + 1);
                            b = Collections.min(longs) + Collections.max(longs);
                            return;
                        } else if (sum > a) break;
                    }
                }
            }
        }
    }
}