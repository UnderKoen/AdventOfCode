package nl.underkoen.adventofcode.solutions.year2019;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.general.Position;

import java.util.*;
import java.util.function.Consumer;

/**
 * Created by Under_Koen on 02/12/2019.
 */
public class Day03 extends Solution {
    @Getter private final int day = 3;
    @Getter private final int year = 2019;

    private static final Map<Character, Consumer<Position>> directions = Map.of(
            'R', l -> l.addX(1),
            'L', l -> l.addX(-1),
            'U', l -> l.addY(1),
            'D', l -> l.addY(-1)
    );

    @Override
    public long[] getCorrectOutput() {
        return new long[]{403, 4158};
    }

    @Override
    protected void run(List<String> input) {
        Map<Position, Integer> points = new HashMap<>();
        Map<Position, Integer> dup = new HashMap<>();

        for (String line : input) {
            String[] path = line.split(",");

            int steps = 1;
            Position position = new Position();
            Set<Position> linePoints = new HashSet<>();

            for (String code : path) {
                for (int amount = Integer.parseInt(code.substring(1)) + steps--; ++steps < amount; ) {
                    directions.get(code.charAt(0)).accept(position);
                    Position point = position.copy();
                    if (!linePoints.add(point)) continue;

                    Integer old;
                    if ((old = points.put(point, steps)) != null) dup.put(point, steps + old);
                }
            }
        }

        a = dup.keySet().stream()
                .mapToLong(Position::distanceOrigin)
                .min()
                .orElse(0);

        b = Collections.min(dup.values());
    }
}