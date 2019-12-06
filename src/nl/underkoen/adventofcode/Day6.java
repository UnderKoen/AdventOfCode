package nl.underkoen.adventofcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Under_Koen on 05/12/2019.
 */
public class Day6 extends AdventOfCode {
    @Override
    int getDay() {
        return 6;
    }

    @Override
    public int[] getCorrectOutput() {
        return new int[]{171213, 292};
    }

    @Override
    void run(List<String> input) {
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

    private static List<String> getAllParents(Map<String, String> orbits, String begin) {
        List<String> parents = new ArrayList<>();
        while (orbits.containsKey(begin)) parents.add(begin = orbits.get(begin));
        return parents;
    }
}
