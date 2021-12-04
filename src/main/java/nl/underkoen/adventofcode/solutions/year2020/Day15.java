package nl.underkoen.adventofcode.solutions.year2020;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
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
    protected void run(Input input) {
        List<Long> nums = InputUtils.asNumbers(input).collect(Collectors.toList());
        Map<Long, Long> done = new HashMap<>();

        long prev = 0;
        long i = 0;
        for (Long num : nums) {
            done.put(num, ++i);
            prev = num;
        }

        while (i < 30000000) {
            long diff = i - done.getOrDefault(prev, i);
            done.put(prev, i++);
            prev = diff;
            if (i == 2020) a = prev;
        }

        b = prev;
    }
}