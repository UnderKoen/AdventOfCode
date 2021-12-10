package nl.underkoen.adventofcode.solutions.year2021;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.position.Position;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.*;

public class Day09 extends Solution {
    @Getter private final int day = 9;
    @Getter private final int year = 2021;

    public static void add(Position position, Map<Position, Character> map, Set<Position> set) {
        if (map.get(position) == '9') return;
        if (!set.add(position)) return;

        for (Position n : position.getDirectNeighbours()) {
            if (!map.containsKey(n)) continue;
            add(n, map, set);
        }
    }

    @Override
    public long[] getCorrectOutput() {
        return new long[]{486, 1059300};
    }

    @Override
    protected void run(Input input) {
        Map<Position, Character> map = input.asCharMap();

        for (Position position : map.keySet()) {
            int depth = Character.getNumericValue(map.get(position));

            boolean lowPoint = position.getDirectNeighbours().stream()
                    .filter(map::containsKey)
                    .map(map::get)
                    .map(Character::getNumericValue)
                    .allMatch(depth2 -> depth < depth2);

            if (lowPoint) a += depth + 1;
        }

        List<Long> sizes = new ArrayList<>();
        Set<Position> done = new HashSet<>();
        for (Position position : map.keySet()) {
            if (done.contains(position)) continue;
            Set<Position> basin = new HashSet<>();

            add(position, map, basin);

            done.addAll(basin);

            sizes.add((long) basin.size());
        }

        sizes.sort(Comparator.comparingLong(v -> (long) v).reversed());

        b = sizes.get(0) * sizes.get(1) * sizes.get(2);
    }
}