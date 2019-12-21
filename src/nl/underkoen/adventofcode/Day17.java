package nl.underkoen.adventofcode;

import nl.underkoen.adventofcode.general.BiHolder;
import nl.underkoen.adventofcode.general.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static nl.underkoen.adventofcode.opcode.OpcodeRunner.*;

/**
 * Created by Under_Koen on 16/12/2019.
 */
public class Day17 extends AdventOfCode {
    public static boolean[] getNeighbours(List<Position> positions, Position position) {
        boolean[] neighbours = new boolean[4];
        for (int dir = 0; dir < 4; dir++) {
            int dx = (dir - 2) % 2;
            int dy = (dir - 1) % 2;
            neighbours[dir] = positions.contains(position.copyAdd(dx, dy));
        }
        return neighbours;
    }

    public static String getCommandos(String path, int max, char c) {
        if (max == 0) return (path.contains("R") || path.contains("L")) ? null : path;

        StringBuilder b = new StringBuilder();
        return Arrays.stream(path.split(",(?!\\d)"))
                .filter(s -> s.length() != 1)
                .map(s -> b.append(b.length() == 0 ? "" : ",").append(s).toString())
                .filter(s -> s.length() <= 20)
                .map(BiHolder.hold(s -> path.replace(s, Character.toString(c))))
                .map(BiHolder.keepKey(e -> getCommandos(e.getValue(), max - 1, (char) (c - 1))))
                .filter(e -> e.getValue() != null)
                .map(e -> e.getValue() + "\n" + e.getKey())
                .findAny()
                .orElse(null);
    }

    @Override
    int getDay() {
        return 17;
    }

    @Override
    public long[] getCorrectOutput() {
        return new long[]{5056, 942367};
    }

    @Override
    void run(List<String> input) {
        long[] program = parse(input);

        List<Position> lines = new ArrayList<>();

        Position loc = new Position();
        Position position = new Position();
        process(program, () -> 0, l -> {
            switch ((char) l) {
                case '\n':
                    position.setX(0);
                    position.addY(1);
                    break;
                case '^':
                    loc.set(position);
                case '#':
                    lines.add(position.copy());
                default:
                    position.addX(1);
            }
        });

        a = lines.stream()
                .filter(p -> Utils.all(getNeighbours(lines, p)))
                .mapToLong(p -> p.getX() * p.getY())
                .sum();

        program[0] = 2;

        List<String> path = new ArrayList<>();

        int dir = 0;
        while (true) {
            boolean[] n = getNeighbours(lines, loc);
            int r = (dir + 3) % 4;
            int l = (dir + 1) % 4;

            dir = n[r] ? r : l;
            if (!n[l] && !n[r]) break;
            path.add(n[r] ? "R" : "L");

            int i = 0;
            for (int dx = (dir - 2) % 2, dy = (dir - 1) % 2; lines.contains(loc.copyAdd(dx, dy)); i++) loc.add(dx, dy);
            path.add(Integer.toString(i));
        }

        String cmdS = getCommandos(String.join(",", path), 3, 'C') + "\nn\n";
        b = processAscii(program, cmdS);
    }
}
