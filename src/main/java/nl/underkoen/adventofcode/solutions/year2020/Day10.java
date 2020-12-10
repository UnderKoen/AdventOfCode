package nl.underkoen.adventofcode.solutions.year2020;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;
import nl.underkoen.adventofcode.utils.MapUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        List<Long> nums = InputUtils.asNumberList(input).sorted(Long::compareTo).collect(Collectors.toList());
        List<Long> thingies = thing(0, nums, new ArrayList<>());
        long q = thingies.stream().filter(l -> l == 1).count();
        long e = thingies.stream().filter(l -> l == 3).count() + 1;
        a = q * e;

        Map<Long, List<Long>> amount = new HashMap<>();
        for (Long num : nums) {
            for (Long c : nums) {
                if (num > c && num <= c + 3) MapUtils.add(amount, c, num);
            }
            if (num > 0 && num <= 3) MapUtils.add(amount, 0L, num);
        }

        b = count(amount, 0);
    }

    Map<Long, Long> cache = new HashMap<>();

    public long count(Map<Long, List<Long>> all, long current) {
        if (cache.containsKey(current)) return cache.get(current);
        if (!all.containsKey(current)) return 1L;
        long a = all.get(current).size();

        for (Long aLong : all.get(current)) {
            long j = count(all, aLong);
            cache.put(aLong, j);
            a += j - 1;
        }

        return a;
    }

    public List<Long> thing(long current, List<Long> todo, List<Long> done) {
        if (todo.stream().anyMatch(l -> l < current)) return null;
        if (todo.isEmpty()) return done;
        for (Long num : todo) {
            if (num > current && num <= current + 3) {
                List<Long> longs = new ArrayList<>(done);
                longs.add(num - current);
                List<Long> lsit = new ArrayList<>(todo);
                lsit.remove(num);
                longs = thing(num, lsit, longs);
                if (longs != null) return longs;
            }
        }
        return null;
    }
}