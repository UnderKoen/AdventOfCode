package nl.underkoen.adventofcode.solutions.year2019;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.List;

import static nl.underkoen.adventofcode.solutions.year2019.opcode.OpcodeRunner.parse;
import static nl.underkoen.adventofcode.solutions.year2019.opcode.OpcodeRunner.process;

/**
 * Created by Under_Koen on 08/12/2019.
 */
public class Day09 extends Solution {
    @Getter private final int day = 9;
    @Getter private final int year = 2019;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{4080871669L, 75202};
    }

    @Override
    protected void run(List<String> input) {
        long[] program = parse(input);
        a = process(program, 1);
        b = process(program, 2);
    }
}
