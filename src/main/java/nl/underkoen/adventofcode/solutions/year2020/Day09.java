package nl.underkoen.adventofcode.solutions.year2020;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day09 extends Solution {
    @Getter private final int day = 9;
    @Getter private final int year = 2020;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{1930745883, 268878261};
    }

    @Override
    protected void run(List<String> input) {
        List<Long> numbers = InputUtils.asNumberList(input).collect(Collectors.toList());

        for (int i = 25; i < numbers.size(); i++) {
            long num = numbers.get(i);
            List<Long> sub = numbers.subList(Math.max(i - 25, 0), i);

            boolean valid = sub.stream().anyMatch(z -> sub.contains(num - z));
            if (!valid) {
                a = num;
                break;
            }
        }

        for (int i = 0; i < numbers.size(); i++) {
            int z = 0;
            for (int i1 = i; i1 < numbers.size(); i1++) {
                z += numbers.get(i1);

                List<Long> longs = numbers.subList(i, i1 + 1);
                if (z == a) {
                    System.out.println(i);
                    System.out.println(i1);
                    b = longs.stream().mapToLong(v -> v).min().getAsLong() + longs.stream().mapToLong(v -> v).max().getAsLong();
                    return;
                }
                else if (z > a) break;
            }
        }
    }
}