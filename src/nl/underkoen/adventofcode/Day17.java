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
        String cmdS = "";

        String[] pieces = path.split(",(?!\\d)");
        List<String> aList = new ArrayList<>();
        for (String aPiece : pieces) {
            aList.add(aPiece);
            String aS = String.join(",", aList);
            String p = path.replace(aS, "A");
            List<String> bList = new ArrayList<>();
            if (aS.length() > 20) continue;
            for (String bPiece : p.split(",(?!\\d)")) {
                if (bPiece.contains("A")) continue;
                bList.add(bPiece);
                String bS = String.join(",", bList);
                String bP = p.replace(bS, "B");
                List<String> cList = new ArrayList<>();
                if (bS.length() > 20) continue;
                for (String cPiece : bP.split(",(?!\\d)")) {
                    if (cPiece.contains("A") || cPiece.contains("B")) continue;
                    cList.add(cPiece);
                    String cS = String.join(",", cList);
                    String cP = bP.replace(cS, "C");
                    if (cS.length() > 20) continue;
                    if (cP.split("[RL]").length == 1) {
                        cmdS = String.format("%s\n%s\n%s\n%s\nn\n", cP, aS, bS, cS);
                    }
                }
            }
        }

        List<Character> cmd = cmdS.chars().mapToObj(c -> (char) c).collect(Collectors.toList());

        IntHolder i = new IntHolder();
        b = process(program, () -> cmd.get(i.addValue(1)), l -> {
        });
    }
}
