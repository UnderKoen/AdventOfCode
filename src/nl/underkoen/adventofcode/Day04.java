package nl.underkoen.adventofcode;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by Under_Koen on 03/12/2019.
 */
public class Day04 extends AdventOfCode {
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
    int getDay() {
        return 4;
    }

    @Override
    public long[] getCorrectOutput() {
        return new long[]{1955, 1319};
    }

    @Override
    void run(List<String> input) {
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

