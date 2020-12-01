package nl.underkoen.adventofcode.solutions.year2018;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;

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
        List<Long> numbers = InputUtils.asNumberList(input);
        numbers.forEach(n -> a += n);

        int freq = 0, i = 0;
        Set<Integer> seen = new HashSet<>();
        while (seen.add(freq)) freq += numbers.get(i++ % numbers.size());

        b = freq;
    }
}