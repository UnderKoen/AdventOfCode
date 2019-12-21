package nl.underkoen.adventofcode;

import java.util.List;

import static nl.underkoen.adventofcode.opcode.OpcodeRunner.parse;
import static nl.underkoen.adventofcode.opcode.OpcodeRunner.processAscii;

/**
 * Created by Under_Koen on 20/12/2019.
 */
public class Day21 extends AdventOfCode {
    @Override
    int getDay() {
        return 21;
    }

    @Override
    public long[] getCorrectOutput() {
        return new long[]{19361023, 1141457530};
    }

    @Override
    void run(List<String> input) {
        long[] program = parse(input);

        String script = "OR A J\n" +
                "AND B J\n" +
                "AND C J\n" +
                "NOT J J\n" +
                "AND D J\n" +
                "WALK\n";

        a = processAscii(program, script);

        script = "OR F T\n" +
                "OR I T\n" +
                "AND E T\n" +
                "OR H T\n" +
                "OR A J\n" +
                "AND B J\n" +
                "AND C J\n" +
                "NOT J J\n" +
                "AND D J\n" +
                "AND T J\n" +
                "RUN\n";

        b = processAscii(program, script);
    }
}
