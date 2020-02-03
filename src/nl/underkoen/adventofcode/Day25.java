package nl.underkoen.adventofcode;

import java.util.List;

import static nl.underkoen.adventofcode.opcode.OpcodeRunner.parse;
import static nl.underkoen.adventofcode.opcode.OpcodeRunner.processAscii;

/**
 * Created by Under_Koen on 24/12/2019.
 */
public class Day25 extends AdventOfCode {
    @Override
    int getDay() {
        return 25;
    }

    @Override
    void run(List<String> input) {
        long[] program = parse(input);
        String s = String.join("\n", input.subList(1, input.size())) + "\n";
        processAscii(program, s, true);
    }
}
