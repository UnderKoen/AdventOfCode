package nl.underkoen.adventofcode.solutions.year2021;

import lombok.Data;
import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.HashSet;
import java.util.Set;

public class Day24 extends Solution {
    @Getter private final int day = 24;
    @Getter private final int year = 2021;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{93997999296912L, 81111379141811L};
    }

    @Override
    protected void run(Input input) {
        long[][] vars = new long[14][3];

        int count = 0;
        for (int i = 0; i < input.size(); i += 18) {
            vars[count][0] = Long.parseLong(input.get(i + 4).substring(6));
            vars[count][1] = Long.parseLong(input.get(i + 5).substring(6));
            vars[count][2] = Long.parseLong(input.get(i + 15).substring(6));

            count++;
        }

        for (int i = 9; i >= 1; i--) {
            String s = test(i, 0, 0, vars);
            if (s != null) {
                a = Long.parseLong(s);
                break;
            }
        }

        for (int i = 1; i <= 9; i++) {
            String s = reverse(i, 0, 0, vars);
            if (s != null) {
                b = Long.parseLong(s);
                break;
            }
            cache.clear();
        }
    }

    public static Set<State> cache = new HashSet<>(Integer.MAX_VALUE);

    public static String test(long w, long z, int depth, long[][] vars) {
        if (depth >= vars.length) return z == 0 ? "" : null;
        State state = new State(w, z, depth);
        if (cache.contains(state)) return null;

        long[] v = vars[depth];

        long x = z % 26 + v[1] != w ? 1 : 0;
        z /= v[0];
        z += (z * 25 + w + v[2]) * x;

        for (int i = 9; i > 0; i--) {
            String s = test(i, z, depth + 1, vars);
            if (s != null) return w + s;
        }
        cache.add(state);
        return null;
    }

    public static String reverse(long w, long z, int depth, long[][] vars) {
        if (depth >= vars.length) return z == 0 ? "" : null;
        State state = new State(w, z, depth);
        if (cache.contains(state)) return null;

        long[] v = vars[depth];

        long x = z % 26 + v[1] != w ? 1 : 0;
        z /= v[0];
        z += (z * 25 + w + v[2]) * x;

        for (int i = 1; i <= 9; i++) {
            String s = reverse(i, z, depth + 1, vars);
            if (s != null) return w + s;
        }
        cache.add(state);
        return null;
    }

    @Data
    public static class State {
        final long w;
        final long z;
        final int depth;
    }
}