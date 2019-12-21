package nl.underkoen.adventofcode;

import java.util.List;

import static nl.underkoen.adventofcode.opcode.OpcodeRunner.parse;
import static nl.underkoen.adventofcode.opcode.OpcodeRunner.process;

/**
 * Created by Under_Koen on 08/12/2019.
 */
public class Day09 extends AdventOfCode {
    @Override
    int getDay() {
        return 9;
    }

    @Override
    public long[] getCorrectOutput() {
        return new long[]{4080871669L, 75202};
    }

    @Override
    void run(List<String> input) {
        long[] program = parse(input);
        a = process(program, 1);
        b = process(program, 2);
    }
}
