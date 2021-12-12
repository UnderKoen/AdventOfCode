package nl.underkoen.adventofcode.solutions.year2021;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.map.collection.HashMapSet;
import nl.underkoen.adventofcode.general.map.collection.MapSet;
import nl.underkoen.adventofcode.general.tuple.BiHolder;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day12 extends Solution {
    @Getter private final int day = 12;
    @Getter private final int year = 2021;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{3485, 85062};
    }

    @Override
    protected void run(Input input) {
        MapSet<String, String> map = new HashMapSet<>();

        input.asSplit("-")
                .mapPairs(BiHolder::new)
                .forEach(b -> map.add(b.getKey(), b.getValue()));

        map.invert().forEach((s, strings) -> strings.forEach(s2 -> map.add(s, s2)));

        Set<String> test = new HashSet<>();

        paths(map, List.of("start"), "start", test, true);
        a = test.size();

        test.clear();
        paths(map, List.of("start"), "start", test, false);
        b = test.size();
    }

    public static long paths(MapSet<String, String> map, List<String> path, String last, Set<String> paths, boolean doubleVisit) {
        if ("end".equals(last)) {
            paths.add(String.join(",", path));
            return 1;
        }
        long l = 0;
        for (String s : map.get(last)) {
            if (s.equals("start")) continue;
            boolean b = doubleVisit;
            if (s.toLowerCase().equals(s) && path.contains(s)) {
                if (doubleVisit) continue;
                else b = true;
            }
            ;
            List<String> p = new ArrayList<>(path);
            p.add(s);
            l += paths(map, p, s, paths, b);
        }
        return l;
    }
}