package nl.underkoen.adventofcode.solutions.year2020;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;

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
        char[][] trees = InputUtils.as2dArray(input);

        a = check(trees, 3, 1);
        b = check(trees, 1, 1) * a * check(trees, 5, 1) * check(trees, 7, 1) * check(trees, 1, 2);
    }

    public long check(char[][] trees, int dx, int dy) {
        long count = 0;

        int len = trees[0].length;
        for (int y = 0, x = 0; y < trees.length; y += dy, x += dx) {
            if (trees[y][x % len] == '#') count++;
        }
    
        return count;
    }
}