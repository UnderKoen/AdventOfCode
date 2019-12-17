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
    public static boolean getNeighbours(List<Position> positions, Position position) {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if ((x == 0) == (y == 0)) continue;
                if (!positions.contains(position.copyAdd(x, y))) return false;
            }
        }
        return true;
    }

    @Override
    int getDay() {
        return 17;
    }

    @Override
    public long[] getCorrectOutput() {
        return new long[]{5056, 942367};
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
    void run(List<String> input) {
        OutputOpcode.setDefaultPrint(false);
        long[] program = parse(input);

        List<Position> lines = new ArrayList<>();

        Position position = new Position();
        process(program, () -> 0, l -> {
            switch ((char) l) {
                case '\n':
                    position.setX(0);
                    position.addY(1);
                    break;
                case '#':
                    lines.add(position.copy());
                default:
                    position.addX(1);
            }
        });

        a = lines.stream()
                .filter(p -> getNeighbours(lines, p))
                .mapToLong(p -> p.getX() * p.getY())
                .sum();

        program[0] = 2;

        String path = "R,12,L,8,R,6,R,12,L,8,R,6,R,12,L,6,R,6,R,8,R,6,L,8,R,8,R,6,R,12,R,12,L,8,R,6,L,8,R,8,R,6,R,12,R,12,L,8,R,6,R,12,L,6,R,6,R,8,R,6,L,8,R,8,R,6,R,12,R,12,L,6,R,6,R,8,R,6";
        String cmdS = getCommandos(path, 3, 'C') + "\nn\n";
        List<Character> cmd = cmdS.chars().mapToObj(c -> (char) c).collect(Collectors.toList());

        IntHolder i = new IntHolder();
        b = process(program, () -> cmd.get(i.addValue(1)), l -> {
        });
    }
}
