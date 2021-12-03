package nl.underkoen.adventofcode.solutions.year2015;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.solutions.Solution;

public class Day01 extends Solution {
    @Getter private final int day = 1;
    @Getter private final int year = 2015;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{};
    }

    @Override
    protected void run(Input input) {
        int i = 1;
        for (char c : input.get(0).toCharArray()) {
            if (c == '(') a++;
            else {
                a--;
                if (a == -1 && b == 0) b = i;
            }
            i++;
        }
    }
}