package nl.underkoen.adventofcode.solutions.year2022;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.List;

public class Day01 extends Solution {
    @Getter private final int day = 1;
    @Getter private final int year = 2022;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{70374, 204610};
    }

    @Override
    protected void run(Input input) {
        List<Long> nums = input.asSubInputs()
                .mapToELong(s -> s.asNumbers().sum())
                .sorted()
                .reverse()
                .toList();

        a = nums.get(0);

        b = nums.get(0) + nums.get(1) + nums.get(2);
    }
}