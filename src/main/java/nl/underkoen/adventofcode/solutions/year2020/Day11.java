package nl.underkoen.adventofcode.solutions.year2020;

import lombok.Getter;
import nl.underkoen.adventofcode.general.Position;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;

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
        Map<Position, Boolean> original = InputUtils.mapChar(input, (c, p) -> {
            if (c == 'L') return p;
            return null;
        })
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(p -> p, p -> false));

        Map<Position, Boolean> places = new HashMap<>(original);

        while (true) {
            List<Position> inverts = new ArrayList<>();
            for (Position place : places.keySet()) {
                int count = 0;
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (i == 0 && j == 0) continue;
                        Position check = place.copyAdd(i, j);
                        if (!places.containsKey(check)) continue;
                        if (places.get(check)) count++;
                    }
                }
                if (places.get(place)) {
                    if (count >= 4) inverts.add(place);
                } else {
                    if (count == 0) inverts.add(place);
                }
            }

            if (inverts.isEmpty()) break;

            for (Position invert : inverts) {
                places.put(invert, !places.get(invert));
            }
        }

        a = places.values().stream().filter(b -> b).count();
        places = original;

        while (true) {
            List<Position> inverts = new ArrayList<>();
            for (Position place : places.keySet()) {
                int count = 0;
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (i == 0 && j == 0) continue;
                        for (int k = 1; k < 1000; k++) {
                            Position check = place.copyAdd(i * k, j * k);
                            if (!places.containsKey(check)) continue;
                            if (places.get(check)) count++;
                            break;

                        }
                    }
                }
                if (places.get(place)) {
                    if (count >= 5) inverts.add(place);
                } else {
                    if (count == 0) inverts.add(place);
                }
            }

            if (inverts.isEmpty()) break;

            for (Position invert : inverts) {
                places.put(invert, !places.get(invert));
            }
        }

        b = places.values().stream().filter(b -> b).count();
    }
}