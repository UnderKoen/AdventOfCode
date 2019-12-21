package nl.underkoen.adventofcode;

import nl.underkoen.adventofcode.general.Position;

import java.util.List;
import java.util.stream.IntStream;

import static nl.underkoen.adventofcode.opcode.OpcodeRunner.parse;
import static nl.underkoen.adventofcode.opcode.OpcodeRunner.process;

/**
 * Created by Under_Koen on 18/12/2019.
 */
public class Day19 extends AdventOfCode {
    public static boolean isInTracker(long[] program, Position position) {
        return process(program, position.asArray()) == 1;
    }

    @Override
    int getDay() {
        return 19;
    }

    @Override
    public long[] getCorrectOutput() {
        return new long[]{114, 10671712};
    }

    @Override
    void run(List<String> input) {
        long[] program = parse(input);
        a = IntStream.range(0, 50)
                .boxed()
                .flatMap(x -> IntStream.range(0, 50)
                        .mapToObj(y -> new Position(x, y))
                )
                .filter(p -> isInTracker(program, p))
                .count();

        Position pos = new Position(0, 10);
        while (true) {
            do {
                if (isInTracker(program, pos)) {
                    if (isInTracker(program, pos.copyAdd(99, -99))) {
                        b = pos.getX() * 10000 + (pos.getY() - 99);
                        return;
                    } else break;
                }
                pos.addX(1);
            } while (!isInTracker(program, pos));
            pos.addY(1);
        }
    }
}
