package nl.underkoen.adventofcode.solutions.year2019;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Under_Koen on 05/12/2019.
 */
public class Day06 extends Solution {
    @Getter private final int day = 6;
    @Getter private final int year = 2019;

    private static List<String> getAllParents(Map<String, String> orbits, String begin) {
        List<String> parents = new ArrayList<>();
        while (orbits.containsKey(begin)) parents.add(begin = orbits.get(begin));
        return parents;
    }

    @Override
    public long[] getCorrectOutput() {
        return new long[]{171213, 292};
    }

    @Override
    protected void run(Input input) {
        Map<String, String> orbits = input.stream()
                .map(s -> s.split("\\)"))
                .collect(Collectors.toMap(s -> s[1], s -> s[0]));

        a = orbits.keySet().stream()
                .map(s -> getAllParents(orbits, s))
                .mapToInt(List::size)
                .sum();

        List<String> parentsYou = getAllParents(orbits, "YOU");
        List<String> parentsSanta = getAllParents(orbits, "SAN");

        b = parentsYou.stream()
                .filter(parentsSanta::contains)
                .mapToInt(s -> parentsYou.indexOf(s) + parentsSanta.indexOf(s))
                .min()
                .orElse(0);
    }
}
