package nl.underkoen.adventofcode;

import nl.underkoen.adventofcode.general.Position;

import java.util.*;

/**
 * Created by Under_Koen on 23/12/2019.
 */
public class Day24 extends AdventOfCode {
    @Override
    int getDay() {
        return 24;
    }

    @Override
    public long[] getCorrectOutput() {
        return new long[]{30446641, 1985};
    }

    @Override
    void run(List<String> input) {
        Set<Position> og = new HashSet<>();
        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);
            for (int x = 0; x < input.size(); x++) {
                char c = line.charAt(x);
                if (c == '#') og.add(new Position(x, y));
            }
        }

        Set<Position> bugs = og;
        Set<Set<Position>> states = new HashSet<>();
        do {
            Set<Position> next = new HashSet<>();
            for (int y = 0; y < 5; y++) {
                for (int x = 0; x < 5; x++) {
                    int n = 0;
                    Position pos = new Position(x, y);
                    for (int i = 0; i < 4; i++) {
                        int dx = (i - 2) % 2;
                        int dy = (i - 1) % 2;
                        if (bugs.contains(pos.copyAdd(dx, dy))) n++;
                    }
                    if (n == 1 || n == 2 && !bugs.contains(pos)) next.add(pos);
                }
            }
            bugs = next;
        } while (states.add(bugs));

        int i = 0;
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                if (bugs.contains(new Position(x, y))) {
                    a += Math.pow(2, i);
                }
                i++;
            }
        }

        HashMap<Integer, Set<Position>> depths = new HashMap<>();
        for (int j = -100; j <= 100; j++) {
            depths.put(j, new HashSet<>());
        }
        depths.put(0, og);

        for (int j = 0; j < 200; j++) {
            HashMap<Integer, Set<Position>> newDepths = new HashMap<>();
            for (Map.Entry<Integer, Set<Position>> e : depths.entrySet()) {
                int depth = e.getKey();
                Set<Position> next = new HashSet<>();

                for (int y = 0; y < 5; y++) {
                    for (int x = 0; x < 5; x++) {
                        if (x == 2 && y == 2) continue;
                        Position pos = new Position(x, y);

                        int neighbours = 0;
                        for (int r = 0; r < 4; r++) {
                            int dx = (r - 2) % 2;
                            int dy = (r - 1) % 2;
                            Position a = pos.copyAdd(dx, dy);

                            if (a.getX() == 2 && a.getY() == 2) {
                                Set<Position> t = depths.getOrDefault(depth + 1, new HashSet<>());

                                for (int k = 0; k < 5; k++) {
                                    Position c = new Position(dy == 0 ? dx * -2 + 2 : k, dx == 0 ? dy * -2 + 2 : k);
                                    if (t.contains(c)) neighbours++;
                                }
                            } else if (a.getX() < 0 || a.getX() > 4 || a.getY() < 0 || a.getY() > 4) {
                                Set<Position> t = depths.getOrDefault(depth - 1, new HashSet<>());

                                if (t.contains(new Position(2 + dx, 2 + dy))) neighbours++;
                            } else if (e.getValue().contains(a)) neighbours++;
                        }
                        if (neighbours == 1 || neighbours == 2 && !e.getValue().contains(pos)) next.add(pos);
                    }
                }
                newDepths.put(depth, next);
            }
            depths = newDepths;
        }

        b = depths.values().stream()
                .mapToLong(Collection::size)
                .sum();
    }
}
