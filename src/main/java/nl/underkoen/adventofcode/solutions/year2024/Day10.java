package nl.underkoen.adventofcode.solutions.year2024;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.map.collection.HashMapList;
import nl.underkoen.adventofcode.general.map.collection.MapList;
import nl.underkoen.adventofcode.general.position.Position;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.*;

public class Day10 extends Solution {
    @Getter private final int day = 10;
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
        MapList<Character, Position> invert = new HashMapList<>();

        for (Position position : map.keySet()) {
            invert.add(map.get(position), position);
        }

        List<Position> list = invert.get('0');

        for (Position pos : list) {
            Queue<Position> queue = new LinkedList<>();
            queue.add(pos);
            Set<Position> topsReached = new HashSet<>();
            List<Position> skill = new ArrayList<>();

            while (!queue.isEmpty()) {
                Position p = queue.poll();

                int digit = Character.digit(map.get(p), 10);
                if (digit == 9) {
                    topsReached.add(p);
                    skill.add(p);
                    continue;
                }
                p.getDirectNeighbours().stream()
                        .filter(map::containsKey)
                        .filter(n -> Character.digit(map.get(n), 10) == digit + 1)
                        .forEach(queue::add);
            }

            a += topsReached.size();
            b += skill.size();
        }
    }
}