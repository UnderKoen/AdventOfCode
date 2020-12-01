package nl.underkoen.adventofcode.solutions.year2018;

import lombok.Getter;
import nl.underkoen.adventofcode.general.Position;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;
import nl.underkoen.adventofcode.utils.MapUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day06 extends Solution {
    @Getter private final int day = 6;
    @Getter private final int year = 2018;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{3933, 41145};
    }

    @Override
    protected void run(List<String> input) {
        List<Position> positions = InputUtils.asPositionList(input)
                .collect(Collectors.toList());

        Position min = Position.min(positions);
        Position max = Position.max(positions);

        Map<Position, Position> closest = new HashMap<>();


        Position.between(min, max).forEach(pos -> {
            List<Position> options = getOptions(pos, positions);

            if (options.size() != 1) return;

            closest.put(pos, options.get(0));
        });

        Map<Position, Integer> matches = new HashMap<>();
        closest.forEach((p, point) -> MapUtils.IncreaseInt(matches, point));

        Position.between(min, max).stream()
                .filter(p -> p.getX() == min.getX() || p.getX() == max.getX() || p.getY() == min.getY() || p.getY() == max.getY())
                .forEach(pos -> {
                    List<Position> options = getOptions(pos, positions);

                    if (options.size() != 1) return;

                    matches.remove(options.get(0));
                });

        a = matches.values().stream()
                .mapToInt(v -> v)
                .max()
                .orElseThrow();

        b = Position.between(min, max).stream()
                .map(pos -> positions.stream()
                        .mapToLong(p -> p.distance(pos))
                        .sum())
                .filter(l -> l < 10000)
                .count();
    }

    public List<Position> getOptions(Position pos, List<Position> positions) {
        long best = positions.stream()
                .mapToLong(p -> p.distance(pos))
                .min()
                .orElseThrow();

        return positions.stream()
                .filter(p -> p.distance(pos) == best)
                .collect(Collectors.toList());
    }
}