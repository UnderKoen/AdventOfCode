package nl.underkoen.adventofcode.solutions.year2020;

import lombok.Getter;
import nl.underkoen.adventofcode.general.Position;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day03 extends Solution {
    @Getter private final int day = 3;
    @Getter private final int year = 2020;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{242, 2265549792L};
    }

    @Override
    protected void run(List<String> input) {
        List<Position> trees = InputUtils.mapChar(input, (c, p) -> c == '#' ? p : null)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        int max = input.size();

        a = check(trees, 3, 1, max);
        b = check(trees, 1, 1, max) * a * check(trees, 5, 1, max) * check(trees, 7, 1, max) * check(trees, 1, 2, max);
    }

    public long check(List<Position> trees, int dx, int dy, int max) {
        long count = 0;

        Position pos = new Position();
        for (int i = 0; i < max; i += dy) {
            if (trees.contains(pos)) count++;
            pos.add(dx, dy).setX(pos.getX() % 31);
        }

        return count;
    }
}