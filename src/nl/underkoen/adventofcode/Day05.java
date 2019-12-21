package nl.underkoen.adventofcode;

import java.util.List;

import static nl.underkoen.adventofcode.opcode.OpcodeRunner.parse;
import static nl.underkoen.adventofcode.opcode.OpcodeRunner.process;

/**
 * Created by Under_Koen on 04/12/2019.
 */
public class Day05 extends AdventOfCode {
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
        long[] numbers = parse(input);
        a = process(numbers, 1);
        b = process(numbers, 5);
    }
}