package nl.underkoen.adventofcode.solutions.year2020;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day15 extends Solution {
    @Getter private final int day = 15;
    @Getter private final int year = 2020;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{289, 1505722};
    }

    @Override
    protected void run(List<String> input) {
        List<Long> longs = InputUtils.asNumberList(input).collect(Collectors.toList());
        Map<Long, Long> test = new HashMap<>();
        long last = 0;
        long last2 = 0;
        for (long i = 0; i < 30000000; i++) {
            if (longs.size() > i) {
                last = longs.get((int) i);
            } else {
                last = i - test.getOrDefault(last, i - 1) - 1;
            }
            test.put(last2, i - 1);
            last2 = last;
            if (i == 2019) a = last;
        }
        b = last;
    }
}