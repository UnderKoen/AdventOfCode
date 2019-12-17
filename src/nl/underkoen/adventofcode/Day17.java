package nl.underkoen.adventofcode;

import nl.underkoen.adventofcode.general.IntHolder;
import nl.underkoen.adventofcode.general.Position;
import nl.underkoen.adventofcode.opcode.OutputOpcode;

import java.util.ArrayList;
import java.util.List;

import static nl.underkoen.adventofcode.opcode.OpcodeRunner.parse;
import static nl.underkoen.adventofcode.opcode.OpcodeRunner.process;

/**
 * Created by Under_Koen on 16/12/2019.
 */
public class Day17 extends AdventOfCode {
    @Override
    int getDay() {
        return 17;
    }

    public static int getNeighbours(List<Position> positions, Position position) {
        int n = 0;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if ((x == 0) == (y == 0)) continue;
                if (positions.contains(position.copyAdd(x, y))) n++;
            }
        }
        return n;
    }

    @Override
    public long[] getCorrectOutput() {
        return new long[]{5056, 942367};
    }

    @Override
    void run(List<String> input) {
        OutputOpcode.setDefaultPrint(false);

        long[] program = parse(input);

        Position position = new Position();
        Position loc = new Position();
        List<Position> lines = new ArrayList<>();
        process(program, () -> 0, l -> {
            char c = (char) l;
            position.addX(1);
            if (c == '\n') {
                position.setX(0);
                position.addY(1);
            } else if (c == '#') {
                lines.add(position.copyAdd(-1, 0));
            } else if (c != '.') {
                loc.set(position);
                loc.addX(-1);
                lines.add(loc);
            }
        });

        a = lines.stream()
                .filter(p -> getNeighbours(lines, p) == 4)
                .mapToLong(p -> p.getX() * p.getY())
                .sum();

        program[0] = 2;
        position.set(0, 0);

        char[] a = "R,12,L,8,R,6".toCharArray();
        char[] bC = "L,8,R,8,R,6,R,12".toCharArray();
        char[] c = "R,12,L,6,R,6,R,8,R,6".toCharArray();

        String chainS = "A,A,C,B,A,B,A,C,B,C";
        char[] chain = chainS.toCharArray();

        List<Character> cmd = new ArrayList<>();
        for (char ch : chain) cmd.add(ch);
        cmd.add('\n');
        for (char ch : a) cmd.add(ch);
        cmd.add('\n');
        for (char ch : bC) cmd.add(ch);
        cmd.add('\n');
        for (char ch : c) cmd.add(ch);
        cmd.add('\n');
        cmd.add('n');
        cmd.add('\n');

        IntHolder i = new IntHolder(0);
        b = process(program, () -> cmd.get(i.addValue(1)).charValue(), l -> {
        });
    }
}
