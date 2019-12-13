package nl.underkoen.adventofcode;

import nl.underkoen.adventofcode.general.BiHolder;

import java.util.*;
import java.util.Map.Entry;

/**
 * Created by Under_Koen on 09/12/2019.
 */
public class Day10 extends AdventOfCode {
    @Override
    int getDay() {
        return 10;
    }

    @Override
    public long[] getCorrectOutput() {
        return new long[]{214, 502};
    }

    @Override
    void run(List<String> input) {
        List<int[]> asteroids = new ArrayList<>();

        for (int y = 0; y < input.size(); y++) {
            char[] chars = input.get(y).toCharArray();
            for (int x = 0; x < chars.length; x++) {
                if (chars[x] == '#') asteroids.add(new int[]{x, y});
            }
        }

        Entry<int[], Long> best = asteroids.stream()
                .map(a -> new BiHolder<>(a, asteroids.stream()
                                .map(pos -> Math.atan2(a[1] - pos[1], a[0] - pos[0]))
                                .distinct()
                                .count()
                        )
                )
                .max(Comparator.comparingLong(Entry::getValue))
                .orElseThrow();

        a = best.getValue();

        Map<Double, int[]> degrees = asteroids.stream()
                .filter(pos -> !Arrays.equals(pos, best.getKey()))
                .map(a -> new BiHolder<>(a, Math.atan2(best.getKey()[1] - a[1], best.getKey()[0] - a[0])))
                .peek(e -> e.setValue(Math.toDegrees(e.getValue()) - 90))
                .peek(e -> e.setValue(e.getValue() < 0 ? e.getValue() + 360 : e.getValue()))
                .collect(HashMap::new, (map, i) -> {
                    if (map.containsKey(i.getValue())) i.setValue(i.getValue() + 360);
                    map.put(i.getValue(), i.getKey());
                }, (m1, m2) -> {
                });

        List<Double> ordered = new ArrayList<>(degrees.keySet());
        ordered.sort(Double::compareTo);

        int[] pos = degrees.get(ordered.get(199));
        b = pos[0] * 100 + pos[1];
    }
}
