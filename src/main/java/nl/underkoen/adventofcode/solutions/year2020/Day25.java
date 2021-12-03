package nl.underkoen.adventofcode.solutions.year2020;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;

import java.util.List;
import java.util.stream.Collectors;

public class Day25 extends Solution {
    @Getter private final int day = 25;
    @Getter private final int year = 2020;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{7032853, 0};
    }

    @Override
    protected void run(Input input) {
        List<Long> nums = InputUtils.asNumberList(input)
                .collect(Collectors.toList());

        long num1 = nums.get(0);
        long num2 = nums.get(1);

        long v = 1;
        long l1 = 1;
        while ((v = (7 * v) % 20201227) != num1) l1++;

        a = 1;
        for (int i = 0; i < l1; i++) {
            a = (num2 * a) % 20201227;
        }
    }
}