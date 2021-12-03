package nl.underkoen.adventofcode.solutions.year2015;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;

public class Day02 extends Solution {
    @Getter private final int day = 2;
    @Getter private final int year = 2015;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{};
    }

//    @Override
//    public String[] getCorrectOutputText() {
//        return new String[]{};
//    }

    @Override
    protected void run(Input input) {
        a = InputUtils.asRegexGroupList(input, "(\\d+)x(\\d+)x(\\d+)")
                .map(s -> s.mapToInt(Integer::parseInt)
                        .toArray())
                .map(a -> {
                    int s1 = 2 * a[0] * a[1];
                    int s2 = 2 * a[0] * a[2];
                    int s3 = 2 * a[1] * a[2];

                    return s1 + s2 + s3 + Math.min(s1, Math.min(s2, s3)) / 2;
                })
                .mapToInt(i -> i)
                .sum();

    }
}