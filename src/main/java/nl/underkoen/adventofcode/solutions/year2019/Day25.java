package nl.underkoen.adventofcode.solutions.year2019;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.solutions.year2019.opcode.OpcodeRunner;

/**
 * Created by Under_Koen on 24/12/2019.
 */
public class Day25 extends Solution {
    @Getter private final int day = 25;
    @Getter private final int year = 2019;

    @Override
    protected void run(Input input) {
        long[] program = OpcodeRunner.parse(input);
        String s = String.join("\n", input.subList(1, input.size())) + "\n";
        OpcodeRunner.processAscii(program, s, true);
        submit = false;
    }
}
