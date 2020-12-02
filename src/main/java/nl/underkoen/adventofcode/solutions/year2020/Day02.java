package nl.underkoen.adventofcode.solutions.year2020;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;

import java.util.List;
import java.util.stream.Collectors;

public class Day02 extends Solution {
    @Getter private final int day = 2;
    @Getter private final int year = 2020;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{519, 708};
    }

    @Override
    protected void run(List<String> input) {
        InputUtils.asRegexGroupList(input, "(\\d+)-(\\d+) ([a-z]): ([a-z]+)")
                .map(s -> s.collect(Collectors.toList()))
                .forEach(l -> {
                    long lower = Long.parseLong(l.get(0));
                    long upper = Long.parseLong(l.get(1));
                    char c = l.get(2).charAt(0);
                    String text = l.get(3);

                    long count = text.chars()
                            .filter(v -> v == c)
                            .count();

                    if (count >= lower && count <= upper) a++;
                    if ((text.charAt((int) lower - 1) == c) != (text.charAt((int) upper - 1) == c)) b++;
                });
    }
}