package nl.underkoen.adventofcode.solutions.year2019;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.List;

import static nl.underkoen.adventofcode.solutions.year2019.opcode.OpcodeRunner.parse;
import static nl.underkoen.adventofcode.solutions.year2019.opcode.OpcodeRunner.process;

/**
 * Created by Under_Koen on 04/12/2019.
 */
public class Day05 extends Solution {
    @Getter private final int day = 5;
    @Getter private final int year = 2019;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{5346030, 513116};
    }

    @Override
    protected void run(List<String> input) {
        long[] numbers = parse(input);
        a = process(numbers, 1);
        b = process(numbers, 5);
    }
}