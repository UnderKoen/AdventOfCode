package nl.underkoen.adventofcode.solutions.year2020;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day06 extends Solution {
    @Getter private final int day = 6;
    @Getter private final int year = 2020;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{6775, 3356};
    }

    @Override
    protected void run(List<String> input) {
        for (List<String> subInput : InputUtils.asSubInputs(input)) {
            List<Set<Character>> chars = subInput.stream()
                    .map(String::chars)
                    .map(s -> s.mapToObj(i -> (char) i))
                    .map(s -> s.collect(Collectors.toSet()))
                    .collect(Collectors.toList());

            a += chars.stream()
                    .flatMap(Collection::stream)
                    .distinct()
                    .count();

            for (int i = 1; i < chars.size(); i++) {
                chars.get(0).retainAll(chars.get(i));
            }

            b += chars.get(0).size();
        }
    }
}