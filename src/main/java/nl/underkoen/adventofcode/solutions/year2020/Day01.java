package nl.underkoen.adventofcode.solutions.year2020;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day01 extends Solution {
    @Getter private final int day = 1;
    @Getter private final int year = 2020;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{270144, 261342720};
    }

    @Override
    protected void run(List<String> input) {
        Set<Long> nums = InputUtils.asNumberList(input)
                .sorted(Long::compareTo)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        for (Long num1 : nums) {
            long num2 = 2020 - num1;
            if (a == 0 && nums.contains(num2)) {
                a = num1 * num2;
                if (b != 0) return;
            }

            for (Long num3 : nums) {
                long num4 = num2 - num3;

                if (b == 0 && nums.contains(num4)) {
                    b = num1 * num4 * num3;
                    if (a != 0) return;
                }
            }
        }
    }
}