package nl.underkoen.adventofcode;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Under_Koen on 01/12/2019.
 */
public class Day2 {
    public static void main(String[] args) {
        List<String> input = Utils.getInput(2);
        String line = input.get(0);
        input = Arrays.asList(line.split(","));
        int[] numbers = input.stream().mapToInt(Integer::parseInt).toArray();
        System.out.println("Result day2a:");
        System.out.println(process(numbers, 12, 2));

        for (int j = 0; j < 100; j++) {
            for (int k = 0; k < 100; k++) {
                numbers = input.stream().mapToInt(Integer::parseInt).toArray();
                int result = process(numbers, j, k);
                if (result == 19690720) {
                    System.out.println("\nResult day2b:");
                    System.out.println(j);
                    System.out.println(k);
                }
            }
        }
    }

    public static int process(int[] program, int noun, int verb) {
        program[1] = noun;
        program[2] = verb;

        for (int i = 0; i < program.length; i += 4) {
            switch (program[i]) {
                case 1:
                    int i1 = program[i + 1];
                    int i2 = program[i + 2];
                    int i3 = program[i + 3];
                    program[i3] = program[i1] + program[i2];
                    break;
                case 2:
                    i1 = program[i + 1];
                    i2 = program[i + 2];
                    i3 = program[i + 3];
                    program[i3] = program[i1] * program[i2];
                    break;
                case 99:
                    return program[0];
                default:
                    break;
            }
        }
        return 0;
    }
}
