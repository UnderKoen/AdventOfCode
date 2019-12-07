package nl.underkoen.adventofcode;

import nl.underkoen.adventofcode.opcode.OpcodeRunner;

import java.util.Arrays;
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
    public int[] getCorrectOutput() {
        return new int[]{5346030, 513116};
    }

    @Override
    void run(List<String> input) {
        String line = input.get(0);
        input = Arrays.asList(line.split(","));
        int[] numbers = input.stream().mapToInt(Integer::parseInt).toArray();
        a = OpcodeRunner.process(numbers, 1);
        b = OpcodeRunner.process(numbers, 5);
    }
}