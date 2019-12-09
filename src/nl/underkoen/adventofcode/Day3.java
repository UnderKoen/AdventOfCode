package nl.underkoen.adventofcode;

import java.util.*;
import java.util.function.Consumer;

/**
 * Created by Under_Koen on 02/12/2019.
 */
public class Day3 extends AdventOfCode {
    public static int calculateDistance(List<Integer> list) {
        return Math.abs(list.get(0)) + Math.abs(list.get(1));
    }

    @Override
    int getDay() {
        return 3;
    }

    @Override
    public long[] getCorrectOutput() {
        return new long[]{403, 4158};
    }

    @Override
    void run(List<String> input) {
        Map<Character, Consumer<Integer[]>> directions = Map.of('R', l -> l[0]++, 'L', l -> l[0]--, 'U', l -> l[1]++, 'D', l -> l[1]--);

        Map<List<Integer>, Integer> points = new HashMap<>();
        Map<List<Integer>, Integer> dup = new HashMap<>();

        for (String line : input) {
            String[] path = line.split(",");

            int steps = 1;
            Integer[] position = new Integer[]{0, 0};
            Set<List<Integer>> linePoints = new HashSet<>();

            for (String code : path) {
                for (int amount = Integer.parseInt(code.substring(1)) + steps--; ++steps < amount; ) {
                    directions.get(code.charAt(0)).accept(position);

                    List<Integer> point = List.of(position);
                    if (!linePoints.add(point)) continue;

                    Integer old;
                    if ((old = points.put(point, steps)) != null) dup.put(point, steps + old);
                }
            }
        }

        a = dup.keySet().stream()
                .mapToInt(Day3::calculateDistance)
                .min()
                .orElse(0);

        b = Collections.min(dup.values());
    }
}