package nl.underkoen.adventofcode.solutions.year2019;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Created by Under_Koen on 01/12/2019.
 */
public class Day02 extends Solution {
    @Getter private final int day = 2;
    @Getter private final int year = 2019;

    public static Map<Integer, BiFunction<Integer, Integer, Integer>> methods = Map.of(
            1, Integer::sum,
            2, (i1, i2) -> i1 * i2
    );

    public static int process(int[] program, int noun, int verb) {
        program = Arrays.copyOf(program, program.length);
        program[1] = noun;
        program[2] = verb;

        for (int i = 0; i < program.length; i += 4) {
            int method = program[i];
            if (method == 99) return program[0];
            if (!methods.containsKey(method)) continue;

            int arg1 = program[program[i + 1]];
            int arg2 = program[program[i + 2]];
            int resultAddress = program[i + 3];

            program[resultAddress] = methods.get(method).apply(arg1, arg2);
        }
        return 0;
    }

    @Override
    public long[] getCorrectOutput() {
        return new long[]{4330636, 6086};
    }

    @Override
    protected void run(List<String> input) {
        String line = input.get(0);
        input = Arrays.asList(line.split(","));
        int[] numbers = input.stream().mapToInt(Integer::parseInt).toArray();

        a = process(numbers, 12, 2);
        for (int j = 0; j < 100; j++) {
            for (int k = 0; k < 100; k++) {
                int result = process(numbers, j, k);
                if (result == 19690720) {
                    b = 100 * j + k;
                    return;
                }
            }
        }
    }
}
