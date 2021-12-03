package nl.underkoen.adventofcode.solutions.year2020;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.Set;
import java.util.stream.Collectors;

public class Day05 extends Solution {
    @Getter private final int day = 5;
    @Getter private final int year = 2020;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{826, 678};
    }

    @Override
    protected void run(Input input) {
        Set<Long> ids = input.stream()
                .map(s -> s.replace('F', '0'))
                .map(s -> s.replace('B', '1'))
                .map(s -> s.replace('L', '0'))
                .map(s -> s.replace('R', '1'))
                .map(s -> Long.parseLong(s, 2))
                .peek(l -> a = l > a ? l : a)
                .collect(Collectors.toSet());

        b = ids.stream()
                .filter(l -> !ids.contains(l + 1))
                .filter(l -> ids.contains(l + 2))
                .findAny()
                .orElseThrow() + 1;
    }
}