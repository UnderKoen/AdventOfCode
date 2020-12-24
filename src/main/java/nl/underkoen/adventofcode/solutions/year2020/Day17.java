package nl.underkoen.adventofcode.solutions.year2020;

import lombok.Getter;
import nl.underkoen.adventofcode.general.position.Position;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.ConwayUtils;
import nl.underkoen.adventofcode.utils.InputUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Day17 extends Solution {
    @Getter private final int day = 17;
    @Getter private final int year = 2020;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{207, 2308};
    }

    @Override
    protected void run(List<String> input) {
        Set<Position> positions = InputUtils.mapChar(input, (c, p) -> c == '#' ? p : null)
                .filter(Objects::nonNull)
                .peek(p -> p.setDimensions(3))
                .collect(Collectors.toSet());

        a = ConwayUtils.calculate(positions, 6, (n, s, p) -> n == 3 || n == 2 && s).size();

        positions.forEach(p -> p.setDimensions(4));
        positions = new HashSet<>(positions);
        b = ConwayUtils.calculate(positions, 6, (n, s, p) -> n == 3 || n == 2 && s).size();
    }
}