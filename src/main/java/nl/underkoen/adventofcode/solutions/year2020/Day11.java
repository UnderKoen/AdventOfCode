package nl.underkoen.adventofcode.solutions.year2020;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.position.Position;
import nl.underkoen.adventofcode.general.tuple.Holder;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;
import nl.underkoen.adventofcode.utils.MapUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day11 extends Solution {
    public static List<Position> directions = List.of(new Position(1, 0), new Position(-1, 1), new Position(0, 1), new Position(1, 1));
    @Getter private final int day = 11;
    @Getter private final int year = 2020;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{2261, 2039};
    }

    @Override
    protected void run(Input input) {
        Map<Position, Boolean> places = InputUtils.mapChar(input, (c, p) -> c == 'L' ? p : null)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(p -> p, p -> false));

        Map<Position, List<Position>> neighbours = new HashMap<>();
        Map<Position, List<Position>> neighboursB = new HashMap<>();

        Position min = new Position();
        Position max = new Position(input.get(0).length(), input.size());

        for (Position place : places.keySet()) {
            for (Position direction : directions) {
                Position check = place.copyAdd(direction);
                if (!check.inside(min, max)) continue;

                if (places.containsKey(check)) {
                    MapUtils.add(neighbours, place, check);
                    MapUtils.add(neighbours, check, place);
                    MapUtils.add(neighboursB, place, check);
                    MapUtils.add(neighboursB, check, place);
                } else do {
                    if (!places.containsKey(check.add(direction))) continue;

                    MapUtils.add(neighboursB, place, check);
                    MapUtils.add(neighboursB, check, place);
                    break;
                } while (check.inside(min, max));
            }
        }

        a = calculate(places, neighbours, 4);
        b = calculate(places, neighboursB, 5);
    }

    public long calculate(Map<Position, Boolean> places, Map<Position, List<Position>> neighbours, int tolerance) {
        places.replaceAll((p, b) -> false);

        Holder<Boolean> changed = new Holder<>(true);
        while (changed.setValue(false)) {
            Map<Position, Long> count = new HashMap<>();
            places.forEach((place, b) -> {
                if (b) neighbours.get(place).forEach(n -> MapUtils.increaseLong(count, n));
            });

            places.forEach((place, b) -> {
                long c = count.getOrDefault(place, 0L);
                if (b && c >= tolerance) {
                    places.put(place, false);
                    changed.setValue(true);
                } else if (!b && c == 0) {
                    places.put(place, true);
                    changed.setValue(true);
                }
            });
        }

        return places.values().stream()
                .filter(b -> b)
                .count();
    }
}