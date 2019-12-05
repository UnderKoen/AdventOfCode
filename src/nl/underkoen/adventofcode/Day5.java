package nl.underkoen.adventofcode;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Created by Under_Koen on 04/12/2019.
 */
public class Day5 {
    public static Map<Integer, BiFunction<Integer, Integer, Integer>> methods = Map.of(
            1, Integer::sum,
            2, (i1, i2) -> i1 * i2,
            7, (i1, i2) -> (i1 < i2) ? 1 : 0,
            8, (i1, i2) -> (i1.equals(i2)) ? 1 : 0
    );

    public static void main(String[] args) {
        List<String> input = Utils.getInput(5);
        String line = input.get(0);
        input = Arrays.asList(line.split(","));
        int[] numbers = input.stream().mapToInt(Integer::parseInt).toArray();
        int a = process(numbers, 1, true);
        int b = process(numbers, 5, false);

        System.out.printf("Result day4a:\n%s\n\n", a);
        System.out.printf("Result day4b:\n%s", b);
    }

    public static int process(int[] program, int input, boolean output) {
        program = Arrays.copyOf(program, program.length);

        int r = -1;
        for (int i = 0; i < program.length;) {
            int method = program[i];
            int optcode = method % 100;

            if (optcode == 99) return r;

            int mode = method / 100;
            boolean position1 = mode % 10 != 1;
            boolean position2 = mode / 10 != 1;

            if (optcode == 3) {
                program[program[i + 1]] = input;
                i += 2;
                continue;
            }
            if (optcode == 4) {
                int out = (position1) ? program[program[i + 1]] : program[i + 1];
                r = out;
                if (output) System.out.println(out);
                i += 2;
                continue;
            }

            int arg1 = (position1) ? program[program[i + 1]] : program[i + 1];
            int arg2 = (position2) ? program[program[i + 2]] : program[i + 2];

            if (optcode == 5) {
                if (arg1 == 0) {
                    i += 3;
                    continue;
                }
                i = arg2;
                continue;
            }

            if (optcode == 6) {
                if (arg1 != 0) {
                    i += 3;
                    continue;
                }
                i = arg2;
                continue;
            }

            int resultAddres = program[i + 3];

            program[resultAddres] = methods.get(optcode).apply(arg1, arg2);
            i += 4;
        }
        return r;
    }
}
