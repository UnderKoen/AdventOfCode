package nl.underkoen.adventofcode;

import java.util.*;

/**
 * Created by Under_Koen on 11/12/2019.
 */
public class Day12 extends AdventOfCode {
    public static long lcm(long number1, long number2) {
        if (number1 == 0 || number2 == 0) {
            return 0;
        }
        long absNumber1 = Math.abs(number1);
        long absNumber2 = Math.abs(number2);
        long absHigherNumber = Math.max(absNumber1, absNumber2);
        long absLowerNumber = Math.min(absNumber1, absNumber2);
        long lcm = absHigherNumber;
        while (lcm % absLowerNumber != 0) {
            lcm += absHigherNumber;
        }
        return lcm;
    }

    @Override
    int getDay() {
        return 12;
    }

    @Override
    public long[] getCorrectOutput() {
        return new long[]{8960, 314917503970904L};
    }

    @Override
    void run(List<String> input) {
        Map<int[], int[]> planets = new HashMap<>();
        for (String planet : input) {
            int[] pos = new int[3];
            int i = 1;
            for (String piece : planet.split("[<>(, )=]")) {
                if (i++ % 3 == 0) {
                    pos[(i / 3) - 1] = Integer.parseInt(piece);
                }
            }
            planets.put(pos, new int[]{0, 0, 0});
        }

        Long x = null, y = null, z = null;
        int c = 0;
        boolean xd = false;
        Set<String> xS = new HashSet<>();
        boolean yd = false;
        Set<String> yS = new HashSet<>();
        boolean zd = false;
        Set<String> zS = new HashSet<>();

        int e = 0;
        long i = 0;
        while (c < 3) {
            StringBuilder xB = new StringBuilder();
            StringBuilder yB = new StringBuilder();
            StringBuilder zB = new StringBuilder();
            for (int[] p : planets.keySet()) {
                int[] v = planets.get(p);
                if (i == 1000) {
                    e += (Math.abs(p[0]) + Math.abs(p[1]) + Math.abs(p[2])) * (Math.abs(v[0]) + Math.abs(v[1]) + Math.abs(v[2]));
                }
                xB.append(String.format("%s %s,", p[0], v[0]));
                yB.append(String.format("%s %s,", p[1], v[1]));
                zB.append(String.format("%s %s,", p[2], v[2]));
            }

            if (!xd && !xS.add(xB.toString())) {
                xd = true;
                x = i;
                c++;
            }

            if (!yd && !yS.add(yB.toString())) {
                yd = true;
                y = i;
                c++;
            }

            if (!zd && !zS.add(zB.toString())) {
                zd = true;
                z = i;
                c++;
            }

            Set<int[]> done = new HashSet<>();
            for (int[] p1 : planets.keySet()) {
                int[] v1 = planets.get(p1);
                done.add(p1);
                for (int[] p2 : planets.keySet()) {
                    if (done.contains(p2)) continue;
                    int[] v2 = planets.get(p2);
                    if (p1[0] > p2[0]) {
                        v1[0]--;
                        v2[0]++;
                    } else if (p1[0] < p2[0]) {
                        v1[0]++;
                        v2[0]--;
                    }

                    if (p1[1] > p2[1]) {
                        v1[1]--;
                        v2[1]++;
                    } else if (p1[1] < p2[1]) {
                        v1[1]++;
                        v2[1]--;
                    }

                    if (p1[2] > p2[2]) {
                        v1[2]--;
                        v2[2]++;
                    } else if (p1[2] < p2[2]) {
                        v1[2]++;
                        v2[2]--;
                    }
                }
            }

            for (int[] p : new HashSet<>(planets.keySet())) {
                int[] v = planets.remove(p);
                p[0] += v[0];
                p[1] += v[1];
                p[2] += v[2];
                planets.put(p, v);
            }
            i++;
        }

        a = e;
        b = lcm(lcm(x, y), z);
    }
}
