package nl.underkoen.adventofcode.solutions.year2019;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.solutions.year2019.opcode.OpcodeRunner;

import java.util.List;

import static nl.underkoen.adventofcode.solutions.year2019.opcode.OpcodeRunner.processAscii;

/**
 * Created by Under_Koen on 24/12/2019.
 */
public class Day25 extends Solution {
    @Getter private final int day = 25;
    @Getter private final int year = 2019;

    @Override
    protected void run(List<String> input) {
        long[] program = OpcodeRunner.parse(input);
        String s = String.join("\n", input.subList(1, input.size())) + "\n";
        OpcodeRunner.processAscii(program, s, true);
    }
}
