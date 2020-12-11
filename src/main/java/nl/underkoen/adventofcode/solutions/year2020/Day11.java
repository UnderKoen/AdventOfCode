package nl.underkoen.adventofcode.solutions.year2020;

import lombok.Getter;
import nl.underkoen.adventofcode.general.Position;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;
import nl.underkoen.adventofcode.utils.MapUtils;

import java.util.*;
import java.util.stream.Collectors;

public class Day11 extends Solution {
    @Getter private final int day = 11;
    @Getter private final int year = 2020;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{2261, 2039};
    }

    @Override
    protected void run(List<String> input) {
        Map<Position, Boolean> places = InputUtils.mapChar(input, (c, p) -> c == 'L' ? p : null)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(p -> p, p -> false));

        Map<Position, List<Position>> neighbours = new HashMap<>();
        Map<Position, List<Position>> neighboursB = new HashMap<>();

        for (Position place : places.keySet()) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if (dx == 0 && dy == 0) continue;
                    Position check = place.copyAdd(dx, dy);
                    if (places.containsKey(check)) MapUtils.add(neighbours, place, check);

                    for (int k = 1; k < 100; k++) {
                        check = place.copyAdd(dx * k, dy * k);
                        if (!places.containsKey(check)) continue;
                        MapUtils.add(neighboursB, place, check);
                        break;
                    }
                }
            }
        }

        a = calculate(new HashMap<>(places), neighbours, 4);
        b = calculate(new HashMap<>(places), neighboursB, 5);

        System.out.println(time / 1000000.0);
    }

    public static long time = 0;

    public long calculate(Map<Position, Boolean> places, Map<Position, List<Position>> neighbours, int tolerance) {
        while (true) {
            Map<Position, Long> count = new HashMap<>();
            for (Position place : places.keySet()) {
                if (places.get(place)) neighbours.get(place).forEach(n ->  MapUtils.increaseLong(count, n));
            }

            boolean changed = false;

            for (Position place : places.keySet()) {
                long c = count.getOrDefault(place, 0L);
                boolean b = places.get(place);
                if (b && c >= tolerance) {
                    places.put(place, false);
                    changed = true;
                } else if (!b && c == 0) {
                    places.put(place, true);
                    changed = true;
                }
            }

            if (!changed) break;
        }

        return places.values().stream()
                .filter(b -> b)
                .count();
    }
}