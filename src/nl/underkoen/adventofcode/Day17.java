package nl.underkoen.adventofcode;

import nl.underkoen.adventofcode.general.IntHolder;
import nl.underkoen.adventofcode.general.Position;
import nl.underkoen.adventofcode.opcode.OutputOpcode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static nl.underkoen.adventofcode.opcode.OpcodeRunner.parse;
import static nl.underkoen.adventofcode.opcode.OpcodeRunner.process;

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
        List<String> part = new ArrayList<>();
        for (String piece : path.split(",(?!\\d)")) {
            if (piece.length() == 1) continue;
            part.add(piece);
            String text = String.join(",", part);
            if (text.length() > 20) continue;
            String replaced = path.replace(text, Character.toString(c));
            String s = getCommandos(replaced, max - 1, (char) (c - 1));
            if (s == null) continue;
            return s + "\n" + text;
        }
        return null;
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
        OutputOpcode.setDefaultPrint(false);
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

            int dx = (dir - 2) % 2;
            int dy = (dir - 1) % 2;

            int i;
            for (i = 0; lines.contains(loc.copyAdd(dx, dy)); i++) loc.add(dx, dy);
            path.add(Integer.toString(i));
        }

        String cmdS = getCommandos(String.join(",", path), 3, 'C') + "\nn\n";
        List<Character> cmd = cmdS.chars().mapToObj(c -> (char) c).collect(Collectors.toList());

        IntHolder i = new IntHolder();
        b = process(program, () -> cmd.get(i.addValue(1)), l -> {
        });
    }
}
