package nl.underkoen.adventofcode.solutions.year2019;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Under_Koen on 11/12/2019.
 */
public class Day12 extends Solution {
    @Getter private final int day = 12;
    @Getter private final int year = 2019;

    public static long lcm(long l1, long l2) {
        long lMax = Math.max(l1, l2);
        long lMin = lMax == l1 ? l2 : l1;
        long lcm = lMax;
        while (lcm % lMin != 0) lcm += lMax;
        return lcm;
    }

    @Override
    public long[] getCorrectOutput() {
        return new long[]{8960, 314917503970904L};
    }

    @Override
    protected void run(Input input) {
        Map<Long[], int[]> planets = InputUtils.asAllNumbers(input)
                .map(l -> l.toArray(Long[]::new))
                .collect(Collectors.toMap(n -> n, n -> new int[]{0, 0, 0}));

        long[] pos = new long[]{0L, 0L, 0L};
        List<Set<String>> posDup = Arrays.asList(new HashSet<>(), new HashSet<>(), new HashSet<>());

        for (long i = 0; pos[0] == 0 || pos[1] == 0 || pos[2] == 0; i++) {
            StringBuilder[] posB = new StringBuilder[]{new StringBuilder(), new StringBuilder(), new StringBuilder()};
            for (Long[] p : planets.keySet()) {
                int[] v = planets.get(p);
                if (i == 1000) {
                    a += (Math.abs(p[0]) + Math.abs(p[1]) + Math.abs(p[2])) * (Math.abs(v[0]) + Math.abs(v[1]) + Math.abs(v[2]));
                }
                for (int j = 0; j < 3; j++) {
                    posB[j].append(String.format("%s %s,", p[j], v[j]));
                }
            }

            for (int j = 0; j < 3; j++) {
                if (pos[j] == 0 && !posDup.get(j).add(posB[j].toString())) {
                    pos[j] = i;
                }
            }

            for (Long[] p1 : planets.keySet()) {
                int[] v = planets.get(p1);
                for (Long[] p2 : planets.keySet()) {
                    for (int j = 0; j < 3; j++) v[j] += p2[j].compareTo(p1[j]);
                }
            }

            for (Map.Entry<Long[], int[]> e : planets.entrySet()) {
                for (int j = 0; j < 3; j++) e.getKey()[j] += e.getValue()[j];
            }
        }

        b = lcm(lcm(pos[0], pos[1]), pos[2]);
    }
}
