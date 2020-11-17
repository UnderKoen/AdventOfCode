package nl.underkoen.adventofcode.solutions.year2019;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by Under_Koen on 03/12/2019.
 */
public class Day04 extends Solution {
    @Getter private final int day = 4;
    @Getter private final int year = 2019;

    public static boolean check(int i, boolean a) {
        int s = -1;
        int[] doubles = new int[10];
        Arrays.fill(doubles, 0);
        for (int j = 0; j < 6; j++) {
            int n = (i / (int) Math.pow(10, 5.0 - j)) % 10;
            if (s == n) doubles[n]++;
            if (n < s) return false;
            s = n;
        }
        return Arrays.stream(doubles).anyMatch(p -> (a) ? p >= 1 : p == 1);
    }

    @Override
    public long[] getCorrectOutput() {
        return new long[]{1955, 1319};
    }

    @Override
    protected void run(List<String> input) {
        String[] range = input.get(0).split("-");
        int min = Integer.parseInt(range[0]) + 1;
        int max = Integer.parseInt(range[1]);

        a = (int) IntStream.range(min, max)
                .filter(i -> check(i, true))
                .count();

        b = (int) IntStream.range(min, max)
                .filter(i -> check(i, false))
                .count();
    }
}

