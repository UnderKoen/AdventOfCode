package nl.underkoen.adventofcode.solutions.year2020;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;
import nl.underkoen.adventofcode.utils.MapUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day10 extends Solution {
    @Getter private final int day = 10;
    @Getter private final int year = 2020;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{2244, 3947645370368L};
    }

    @Override
    protected void run(List<String> input) {
        Set<Long> nums = InputUtils.asNumberList(input).collect(Collectors.toSet());
        Map<Long, List<Long>> amount = new HashMap<>();

        for (Long num : nums) {
            for (long option = num + 1; option <= num + 3; option++) {
                if (nums.contains(option)) MapUtils.add(amount, num, option);
            }
            if (num > 0 && num <= 3) MapUtils.add(amount, 0L, num);
        }

        long count1 = 0, count3 = 1;

        for (Map.Entry<Long, List<Long>> entry : amount.entrySet()) {
            long diff = entry.getValue().get(0) - entry.getKey();
            if (diff == 1) count1++;
            else if (diff == 3) count3++;
        }

        a = count1 * count3;
        b = count(amount, 0);
    }

    private final Map<Long, Long> cache = new HashMap<>();

    public long count(Map<Long, List<Long>> all, long current) {
        if (!all.containsKey(current)) return 1L;
        if (cache.containsKey(current)) return cache.get(current);
        long options = 0;

        for (Long num : all.get(current)) {
            long j = count(all, num);
            cache.put(num, j);
            options += j;
        }

        return options;
    }
}