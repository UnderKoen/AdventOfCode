package nl.underkoen.adventofcode.solutions.year2021;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.solutions.Solution;

public class Day01 extends Solution {
    @Getter private final int day = 1;
    @Getter private final int year = 2021;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{1529, 1567};
    }

    @Override
    protected void run(Input input) {
        a = input.asNumbersStream()
                .mapWithPrev((l1, l2) -> l1 < l2)
                .filter(b -> b)
                .count();

        b = input.asNumbersStream()
                .mapWithPrev((l1, l2, l3) -> l1 + l2 + l3)
                .mapWithPrev((l1, l2) -> l1 < l2)
                .filter(b -> b)
                .count();
    }
}