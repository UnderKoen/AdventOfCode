package nl.underkoen.adventofcode;

import nl.underkoen.adventofcode.opcode.OpcodeRunner;

import java.util.List;

/**
 * Created by Under_Koen on 04/12/2019.
 */
public class Day5 extends AdventOfCode {
    @Override
    int getDay() {
        return 5;
    }

    @Override
    public long[] getCorrectOutput() {
        return new long[]{5346030, 513116};
    }

    @Override
    void run(List<String> input) {
        long[] numbers = OpcodeRunner.parse(input);
        a = OpcodeRunner.process(numbers, 1);
        b = OpcodeRunner.process(numbers, 5);
    }
}