package nl.underkoen.adventofcode.solutions.year2019;


import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;

import java.util.List;

/**
 * Created by Under_Koen on 01/12/2019.
 */
public class Day01 extends Solution {
    @Getter private final int day = 1;
    @Getter private final int year = 2019;

    public static long calculate(long i) {
        long fuel = Math.max(i / 3 - 2, 0);
        return fuel + (fuel > 0 ? calculate(fuel) : 0);
    }

    @Override
    public long[] getCorrectOutput() {
        return new long[]{3471229, 5203967};
    }

    @Override
    protected void run(List<String> input) {
        a = InputUtils.asNumberList(input)
                .stream()
                .mapToLong(i -> i / 3 - 2)
                .sum();

        b = InputUtils.asNumberList(input)
                .stream()
                .mapToLong(Day01::calculate)
                .sum();
    }
}
