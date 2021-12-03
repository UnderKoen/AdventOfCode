package nl.underkoen.adventofcode.solutions.year2019;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.solutions.Solution;

import static nl.underkoen.adventofcode.solutions.year2019.opcode.OpcodeRunner.parse;
import static nl.underkoen.adventofcode.solutions.year2019.opcode.OpcodeRunner.processAscii;

/**
 * Created by Under_Koen on 20/12/2019.
 */
public class Day21 extends Solution {
    @Getter private final int day = 21;
    @Getter private final int year = 2019;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{19361023, 1141457530};
    }

    @Override
    protected void run(Input input) {
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
