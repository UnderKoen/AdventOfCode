package nl.underkoen.adventofcode.solutions.year2021;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.map.collection.HashMapSet;
import nl.underkoen.adventofcode.general.map.collection.MapSet;
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
                .forEachPair(map::add);

        map.addAll(map.invert());

        a = paths(map, List.of("start"), "start", new HashSet<>(), true);
        b = paths(map, List.of("start"), "start", new HashSet<>(), false);
    }

    public static long paths(MapSet<String, String> map, List<String> path, String last, Set<String> paths, boolean doubleVisit) {
        long l = 0;
        if ("end".equals(last)) {
            if (paths.add(String.join(",", path))) l++;
            return l;
        }
        for (String s : map.get(last)) {
            if (s.equals("start")) continue;
            boolean b = doubleVisit;
            if (s.toLowerCase().equals(s) && path.contains(s)) {
                if (doubleVisit) continue;
                else b = true;
            }

            List<String> p = new ArrayList<>(path);
            p.add(s);
            l += paths(map, p, s, paths, b);
        }
        return l;
    }
}