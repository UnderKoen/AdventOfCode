package nl.underkoen.adventofcode.solutions.year2020;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.List;

public class Day03 extends Solution {
    @Getter private final int day = 3;
    @Getter private final int year = 2020;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{242, 2265549792L};
    }

    @Override
    protected void run(List<String> input) {
        a = check(input, 3, 1);
        b = check(input, 1, 1) * a * check(input, 5, 1) * check(input, 7, 1) * check(input, 1, 2);
    }

    public long check(List<String> trees, int dx, int dy) {
        long count = 0;

        int len = trees.get(0).length();
        for (int y = 0, x = 0; y < trees.size(); y += dy, x += dx) {
            if (trees.get(y).charAt(x % len) == '#') count++;
        }
    
        return count;
    }
}