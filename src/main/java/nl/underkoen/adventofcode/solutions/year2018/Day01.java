package nl.underkoen.adventofcode.solutions.year2018;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day01 extends Solution {
    @Getter private final int day = 1;
    @Getter private final int year = 2018;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{510, 69074};
    }

    @Override
    protected void run(List<String> input) {
        a = input.stream()
                .mapToInt(v -> {
                    int i = Integer.parseInt(v.substring(1));
                    return v.charAt(0) == '+' ? i : -i;
                }).sum();

        int freq = 0;
        Set<Integer> seen = new HashSet<>();
        int i = 0;
        while (seen.add(freq)) {
            String v = input.get(i++);
            if (i >= input.size()) i = 0;
            int j = Integer.parseInt(v.substring(1));
            freq += v.charAt(0) == '+' ? j : -j;
        }

        b = freq;
    }
}