package nl.underkoen.adventofcode.solutions.year2022;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.solutions.Solution;

public class Day01 extends Solution {
    @Getter private final int day = 1;
    @Getter private final int year = 2022;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{70374, 204610};
    }

    @Override
    protected void run(Input input) {
        a = input.asSubInputs()
                .map(s -> s.asNumbers().mapToLong(d -> d).sum()).mapToLong(d -> d)
                .max()
                .orElse(0);

        b = -input.asSubInputs()
                .map(s -> s.asNumbers().mapToLong(d -> d).sum()).mapToLong(d -> -d)
                .sorted()
                .limit(3)
                .sum();
    }
}