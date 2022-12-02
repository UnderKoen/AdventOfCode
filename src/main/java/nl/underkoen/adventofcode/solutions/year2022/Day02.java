package nl.underkoen.adventofcode.solutions.year2022;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.solutions.Solution;

public class Day02 extends Solution {
    @Getter private final int day = 2;
    @Getter private final int year = 2022;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{13052, 13693};
    }

    @Override
    protected void run(Input input) {
        for (String line : input) {
            long x = Character.compare(line.charAt(0), 'A');
            long y = Character.compare(line.charAt(2), 'X');

            long d = (x - y + 3) % 3;

            a += y + 1;
            a += (d < 2 ? 1 - d : d) * 3;

            b += y * 3;

            if (--y < 0) y = 2;

            b += (x + y) % 3 + 1;
        }
    }
}