package nl.underkoen.adventofcode;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Created by Under_Koen on 01/12/2019.
 */
public class Day2 extends AdventOfCode {
    public static Map<Integer, BiFunction<Integer, Integer, Integer>> methods = Map.of(
            1, Integer::sum,
            2, (i1, i2) -> i1 * i2
    );

    @Override
    int getDay() {
        return 2;
    }

    @Override
    public int[] getCorrectOutput() {
        return new int[]{4330636, 6086};
    }

    @Override
    void run(List<String> input) {
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
            int resultAddres = program[i + 3];

            program[resultAddres] = methods.get(method).apply(arg1, arg2);
        }
        return 0;
    }
}
