package nl.underkoen.adventofcode.solutions.year2019;

import lombok.Getter;
import nl.underkoen.adventofcode.general.position.Position;
import nl.underkoen.adventofcode.general.tuple.BiHolder;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * Created by Under_Koen on 09/12/2019.
 */
public class Day10 extends Solution {
    @Getter private final int day = 10;
    @Getter private final int year = 2019;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{214, 502};
    }

    @Override
    protected void run(List<String> input) {
        List<Position> asteroids = InputUtils.mapChar(input, (c, p) -> c == '#' ? p : null)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Entry<Position, Long> best = asteroids.stream()
                .map(a -> new BiHolder<>(a, asteroids.stream()
                                .map(a::atan2)
                                .distinct()
                                .count()
                        )
                )
                .max(Comparator.comparingLong(Entry::getValue))
                .orElseThrow();

        a = best.getValue();

        Position bestPos = best.getKey();

        Map<Double, Position> degrees = asteroids.stream()
                .filter(pos -> pos != bestPos)
                .map(BiHolder.hold(bestPos::atan2))
                .map(e -> e.mapValue(r -> Math.toDegrees(r) - 90))
                .map(e -> e.mapValue(d -> d < 0 ? d + 360 : d))
                .collect(HashMap::new, (map, i) -> {
                    if (map.containsKey(i.getValue())) i.setValue(i.getValue() + 360);
                    map.put(i.getValue(), i.getKey());
                }, (m1, m2) -> {
                });

        List<Double> ordered = new ArrayList<>(degrees.keySet());
        ordered.sort(Double::compareTo);

        Position pos = degrees.get(ordered.get(199));
        b = pos.getX() * 100 + pos.getY();
    }
}
