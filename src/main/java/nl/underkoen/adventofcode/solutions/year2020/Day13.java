package nl.underkoen.adventofcode.solutions.year2020;

import lombok.Getter;
import nl.underkoen.adventofcode.general.BiHolder;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;
import nl.underkoen.adventofcode.utils.NumberUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Day13 extends Solution {
    @Getter private final int day = 13;
    @Getter private final int year = 2020;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{6559, 626670513163231L};
    }

    @Override
    protected void run(List<String> input) {
        long initial = Long.parseLong(input.get(0));
        String[] parts = input.get(1).split(",");

        List<Long> nums = new ArrayList<>();
        List<Long> mods = new ArrayList<>();

        a = InputUtils.asIndexedStream(Arrays.asList(parts))
                .filter(h -> !h.getValue().equals("x"))
                .map(BiHolder.keepKey(h -> Long.parseLong(h.getValue())))
                .map(BiHolder.keepValue(h -> h.getValue() - h.getKey()))
                .peek(h -> {
                    nums.add(h.getValue());
                    mods.add(h.getKey());
                })
                .map(BiHolder.keepValue(h -> h.getValue() - (initial % h.getValue())))
                .min(Comparator.comparingLong(BiHolder::getKey))
                .orElseThrow()
                .reduce((id, diff) -> id * diff);

        b = NumberUtils.chineseRemainder(nums, mods);
    }
}