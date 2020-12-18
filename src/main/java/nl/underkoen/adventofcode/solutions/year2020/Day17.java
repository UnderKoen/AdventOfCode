package nl.underkoen.adventofcode.solutions.year2020;

import lombok.Getter;
import nl.underkoen.adventofcode.general.position.PositionND;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;
import nl.underkoen.adventofcode.utils.PositionUtils;

import java.util.List;
import java.util.Map;
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
        Set<PositionND> positions = InputUtils.mapChar(input, (c, p) -> c == '#' ? p : null)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        a = calculate(positions, 3);
        b = calculate(positions, 4);
    }

    private long calculate(Set<PositionND> start, int dimensions) {
        Set<PositionND> positions = start.stream()
                .map(p -> p.copyWithDimension(dimensions))
                .collect(Collectors.toSet());

        for (int i = 0; i < 6; i++) {
            Map<PositionND, Long> amount = PositionUtils.countNeighbours(positions);
            Set<PositionND> f = positions;
            positions = amount.entrySet().stream()
                    .filter(e -> e.getValue() == 3 ||
                            e.getValue() == 2 && f.contains(e.getKey()))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toSet());
        }

        return positions.size();
    }
}