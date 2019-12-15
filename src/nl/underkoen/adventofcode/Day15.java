package nl.underkoen.adventofcode;

import nl.underkoen.adventofcode.general.Position;
import nl.underkoen.adventofcode.opcode.OutputOpcode;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static nl.underkoen.adventofcode.opcode.OpcodeRunner.parse;
import static nl.underkoen.adventofcode.opcode.OpcodeRunner.process;

/**
 * Created by Under_Koen on 15/12/2019.
 */
public class Day15 extends AdventOfCode {
    public static void checkDistance(Map<Position, Integer> dis, Set<Position> all, Position current, int index) {
        all.remove(current);
        dis.put(current, index);

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if ((x == 0) == (y == 0)) continue;
                Position pos = current.copyAdd(x, y);
                if (all.contains(pos)) checkDistance(dis, all, pos, index + 1);
            }
        }
    }

    public static void printMap(Map<Position, Integer> map) {
        Position min = map.keySet().stream().reduce((p, p2) -> {
            long x = Math.min(p.getX(), p2.getX());
            long y = Math.min(p.getY(), p2.getY());
            return new Position(x, y);
        }).orElseThrow();

        Position max = map.keySet().stream().reduce((p, p2) -> {
            long x = Math.max(p.getX(), p2.getX());
            long y = Math.max(p.getY(), p2.getY());
            return new Position(x, y);
        }).orElseThrow();

        LongStream.range(min.getY(), max.getY() + 1)
                .mapToObj(y -> LongStream.range(min.getX(), max.getX() + 1)
                        .mapToObj(x -> new Position(x, y))
                        .mapToLong(pos -> map.getOrDefault(pos, 0))
                        .mapToObj(l -> l == 0 ? "##" : l == 2 ? "||" : l == 4 ? "==" : "  ")
                        .reduce(String::concat)
                        .orElse("")
                )
                .forEachOrdered(System.out::println);
    }

    @Override
    int getDay() {
        return 15;
    }

    @Override
    void run(List<String> input) {
        OutputOpcode.setDefaultPrint(false);

        long[] program = parse(input);

        Position position = new Position();
        Map<Position, Integer> dir = new HashMap<>();
        Map<Position, Integer> map = new HashMap<>();

        try {
            process(program, () -> dir.getOrDefault(position, 1), l -> {
                int d = dir.getOrDefault(position, 1);
                Position nPos = position.copy();
                switch (d) {
                    case 1:
                        nPos.addY(1);
                        break;
                    case 2:
                        nPos.addY(-1);
                        break;
                    case 3:
                        nPos.addX(-1);
                        break;
                    case 4:
                        nPos.addX(1);
                        break;
                }

                dir.put(position.copy(), d % 4 + 1);

                switch ((int) l) {
                    case 0:
                        map.put(nPos, 0);
                        break;
                    case 1:
                        position.set(nPos);
                        map.put(nPos, 1);
                        break;
                    case 2:
                        position.set(nPos);
                        map.put(nPos, 2);

                        Position min = map.keySet().stream().reduce((p, p2) -> {
                            long x = Math.min(p.getX(), p2.getX());
                            long y = Math.min(p.getY(), p2.getY());
                            return new Position(x, y);
                        }).orElseThrow();

                        Position max = map.keySet().stream().reduce((p, p2) -> {
                            long x = Math.max(p.getX(), p2.getX());
                            long y = Math.max(p.getY(), p2.getY());
                            return new Position(x, y);
                        }).orElseThrow();

                        long t = LongStream.range(min.getY(), max.getY() + 1)
                                .flatMap(y -> LongStream.range(min.getX(), max.getX() + 1)
                                        .map(x -> (long) map.getOrDefault(new Position(x, y), 10)))
                                .filter(v -> v == 10)
                                .count();

                        if (t == 21) throw new IllegalArgumentException();
                }
            });
        } catch (IllegalArgumentException ignored) {
        }

        System.out.println(position);

        map.put(new Position(), 4);
        printMap(map);

        Map<Position, Integer> dis = new HashMap<>();

        Set<Position> all = map.keySet().stream()
                .filter(p -> map.get(p) != 0)
                .collect(Collectors.toSet());

        checkDistance(dis, all, new Position(), 0);

        a = dis.get(position);

        dis.clear();
        all = map.keySet().stream()
                .filter(p -> map.get(p) != 0)
                .collect(Collectors.toSet());

        checkDistance(dis, all, position, 0);
        b = Collections.max(dis.values());
    }
}
