package nl.underkoen.adventofcode.solutions.year2024;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.position.Position;
import nl.underkoen.adventofcode.general.sets.ESet;
import nl.underkoen.adventofcode.general.sets.HashESet;
import nl.underkoen.adventofcode.general.tuple.BiHolder;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day06 extends Solution {
    @Getter private final int day = 6;
    @Getter private final int year = 2024;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{};
    }

//    @Override
//    public String[] getCorrectOutputText() {
//        return new String[]{};
//    }

    @Override
    protected void run(Input input) {
        Map<Position, Character> map = input.asCharMap();

        Position pos = map.entrySet().stream().filter(e -> e.getValue() == '^').findFirst().orElseThrow().getKey().copy();

        List<Position> dirs = List.of(new Position(0, -1), new Position(1, 0), new Position(0, 1), new Position(-1, 0));
        int dirIndex = 0;
        Position dir = dirs.get(dirIndex);
        ESet<Position> visited = new HashESet<>();
        while (map.containsKey(pos)) {
            pos.add(dir);
            if (map.containsKey(pos) && map.get(pos) == '#') {
                pos.sub(dir);
                dirIndex = (dirIndex + 1) % 4;
                dir = dirs.get(dirIndex);
            }
            visited.add(pos.copy());
        }

        a = visited.size();

        b = map.entrySet().stream().filter(e -> e.getValue() == '.').map(Map.Entry::getKey).filter(p -> {
            HashMap<Position, Character> copy = new HashMap<>(map);
            copy.put(p, '#');
            return isLoop(copy);
        }).count();
    }

    public static boolean isLoop(Map<Position, Character> map) {
        Position pos = map.entrySet().stream().filter(e -> e.getValue() == '^').findFirst().orElseThrow().getKey().copy();

        List<Position> dirs = List.of(new Position(0, -1), new Position(1, 0), new Position(0, 1), new Position(-1, 0));
        int dirIndex = 0;
        Position dir = dirs.get(dirIndex);
        ESet<BiHolder<Position, Integer>> visited = new HashESet<>();
        while (map.containsKey(pos)) {
            pos.add(dir);
            if (map.containsKey(pos) && map.get(pos) == '#') {
                pos.sub(dir);
                dirIndex = (dirIndex + 1) % 4;
                dir = dirs.get(dirIndex);
            }
            if (!visited.add(new BiHolder<>(pos.copy(), dirIndex))) {
                return true;
            }
        }
        return false;
    }
}