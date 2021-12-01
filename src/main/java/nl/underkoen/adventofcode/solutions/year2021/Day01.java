package nl.underkoen.adventofcode.solutions.year2021;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;
import nl.underkoen.adventofcode.utils.StreamUtils;

import java.util.List;
import java.util.stream.Stream;

public class Day01 extends Solution {
    @Getter private final int day = 1;
    @Getter private final int year = 2021;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{1529, 1567};
    }

    @Override
    protected void run(List<String> input) {
        List<Long> nums = InputUtils.asNumberList(input).toList();

        a = StreamUtils.mapWithPrev(nums.stream(), (l1, l2) -> l1 < l2)
                .filter(b -> b)
                .count();

        Stream<Long> window = StreamUtils.mapWithPrev(nums.stream(), (l1, l2, l3) -> l1 + l2 + l3);
        b = StreamUtils.mapWithPrev(window, (l1, l2) -> l1 < l2)
                .filter(b -> b)
                .count();
    }
}