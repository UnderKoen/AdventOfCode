package nl.underkoen.adventofcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String, String> orbits = new HashMap<>();

        input.stream()
                .map(s -> s.split("\\)"))
                .forEach(s -> orbits.put(s[1], s[0]));

        a = orbits.keySet().stream()
                .map(s -> getAllParents(orbits, s))
                .mapToInt(List::size)
                .sum();

        List<String> parentsYou = getAllParents(orbits, "YOU");
        List<String> parentsSanta = getAllParents(orbits, "SAN");

        for (int i = 0; i < parentsYou.size(); i++) {
            String current = parentsYou.get(i);
            b = i + parentsSanta.indexOf(current);
            if (b != i - 1) break;
        }
    }

    private static List<String> getAllParents(Map<String, String> orbits, String begin) {
        List<String> parents = new ArrayList<>();
        for (; orbits.containsKey(begin); parents.add(begin = orbits.get(begin))) ;
        return parents;
    }
}
