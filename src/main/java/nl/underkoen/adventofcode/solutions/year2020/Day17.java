package nl.underkoen.adventofcode.solutions.year2020;

import lombok.Getter;
import nl.underkoen.adventofcode.general.BiHolder;
import nl.underkoen.adventofcode.general.position.Position;
import nl.underkoen.adventofcode.general.position.Position4D;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;
import nl.underkoen.adventofcode.utils.MapUtils;

import java.util.*;
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
        List<Position4D> positions = InputUtils.mapChar(input, (c, p) -> c == '#' ? p : null)
                .filter(Objects::nonNull)
                .map(Position4D::new)
                .collect(Collectors.toList());

        System.out.println(Position.max(positions));

        Map<Position, List<Position>> map = new HashMap<>();
//        map.put(new Position(0, 0), positions);

        for (int i = 0; i < 6; i++) {
            Map<Position, List<Position>> clone = new HashMap<>();
            Map<BiHolder<Position, Position>, Long> amount = new HashMap<>();
            for (Map.Entry<Position, List<Position>> longListEntry : map.entrySet()) {
                long z = longListEntry.getKey().getX();
                long w = longListEntry.getKey().getY();

                for (Position position : longListEntry.getValue()) {
                    long x = position.getX();
                    long y = position.getY();
                    for (int dx = -1; dx <= 1; dx++) {
                        for (int dy = -1; dy <= 1; dy++) {
                            for (int dz = -1; dz <= 1; dz++) {
                                for (int dw = -1; dw <= 1; dw++) {
                                    if (dx == 0 && dy == 0 && dz == 0 && dw == 0) continue;
                                    BiHolder<Position, Position> p = new BiHolder<>(new Position(z + dz, w + dw), new Position(x + dx, y + dy));
                                    MapUtils.increaseLong(amount, p);
                                }
                            }
                        }
                    }
                }
            }

            for (Map.Entry<BiHolder<Position, Position>, Long> entry : amount.entrySet()) {
                long count = entry.getValue();
                Position d2 = entry.getKey().getKey();
                Position pos = entry.getKey().getValue();

                boolean active = false;
                if (map.containsKey(d2) && map.get(d2).contains(pos)) active = true;

                if (active) {
                    if (count == 2 || count == 3) {
                        MapUtils.add(clone, d2, pos);
                    }
                } else {
                    if (count == 3) {
                        MapUtils.add(clone, d2, pos);
                    }
                }
            }
            map = clone;
        }

        a = map.values().stream()
                .mapToLong(Collection::size)
                .sum();
    }
}