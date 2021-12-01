package nl.underkoen.adventofcode.solutions.year2021;

import lombok.Getter;
import nl.underkoen.adventofcode.general.stream.EStream;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;

import java.util.List;

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

        a = EStream.of(nums)
                .mapWithPrev((l1, l2) -> l1 < l2)
                .filter(b -> b)
                .count();

        b = EStream.of(nums)
                .mapWithPrev((l1, l2, l3) -> l1 + l2 + l3)
                .mapWithPrev((l1, l2) -> l1 < l2)
                .filter(b -> b)
                .count();
    }
}