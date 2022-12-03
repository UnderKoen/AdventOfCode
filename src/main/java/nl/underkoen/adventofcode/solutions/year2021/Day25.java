package nl.underkoen.adventofcode.solutions.year2021;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.position.Position;
import nl.underkoen.adventofcode.general.position.multi.Area;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Day25 extends Solution {
    @Getter private final int day = 25;
    @Getter private final int year = 2021;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{406, 0};
    }

    @Override
    protected void run(Input input) {
        Map<Position, Character> map = input.asCharMap();
        Area<Position> area = new Area<>(map.keySet());
        for (Position position : new HashSet<>(map.keySet())) {
            if (map.get(position) == '.') map.remove(position);
        }


        int hash;
        do {
            hash = map.hashCode();

            Map<Position, Character> map2 = new HashMap<>();
            for (Position position : map.keySet()) {
                Character c = map.get(position);
                if (c == 'v') {
                    map2.put(position, c);
                    continue;
                }
                Position add = position.copyAdd(1, 0);
                if (!add.inside(area)) add.setX(0);
                if (map.get(add) == null) map2.put(add, c);
                else map2.put(position, c);
            }

            map = map2;
            map2 = new HashMap<>();
            for (Position position : map.keySet()) {
                Character c = map.get(position);
                if (c == '>') {
                    map2.put(position, c);
                    continue;
                }
                Position add = position.copyAdd(0, 1);
                if (!add.inside(area)) add.setY(0);
                if (map.get(add) == null) map2.put(add, c);
                else map2.put(position, c);
            }

            map = map2;
            a++;
        } while (hash != map.hashCode());
    }
}