package nl.underkoen.adventofcode.solutions.year2019;

import lombok.Getter;
import nl.underkoen.adventofcode.general.position.Position;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.List;
import java.util.stream.IntStream;

import static nl.underkoen.adventofcode.solutions.year2019.opcode.OpcodeRunner.parse;
import static nl.underkoen.adventofcode.solutions.year2019.opcode.OpcodeRunner.process;

/**
 * Created by Under_Koen on 18/12/2019.
 */
public class Day19 extends Solution {
    @Getter private final int day = 19;
    @Getter private final int year = 2019;

    public static boolean isInTracker(long[] program, Position position) {
        return process(program, position.asArray()) == 1;
    }

    @Override
    public long[] getCorrectOutput() {
        return new long[]{114, 10671712};
    }

    @Override
    protected void run(List<String> input) {
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
