package nl.underkoen.adventofcode;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.IntBinaryOperator;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;

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

    public static final boolean PRINT = false;

    public static Map<Integer, Opcode> opcodes = Map.of(
            1, new ResultOpcode(Integer::sum),
            2, new ResultOpcode((i1, i2) -> i1 * i2),
            3, (args, i, program, input, output) -> {
                program[program[i + 1]] = input;
                return i + 2;
            },
            4, (args, i, program, input, output) -> {
                output[0] = args.applyAsInt(1);
                if (PRINT) System.out.println(output[0]);
                return i + 2;
            },
            5, new MoveOpcode(i -> i != 0),
            6, new MoveOpcode(i -> i == 0),
            7, new ResultOpcode((i1, i2) -> (i1 < i2) ? 1 : 0),
            8, new ResultOpcode((i1, i2) -> (i1 == i2) ? 1 : 0)
    );

    @Override
    void run(List<String> input) {
        String line = input.get(0);
        input = Arrays.asList(line.split(","));
        int[] numbers = input.stream().mapToInt(Integer::parseInt).toArray();
        a = process(numbers, 1);
        b = process(numbers, 5);
    }

    public static int process(int[] program, int input) {
        program = Arrays.copyOf(program, program.length);

        int[] r = new int[1];
        for (int i = 0; i < program.length; ) {
            int method = program[i];
            int optcode = method % 100;

            if (optcode == 99) break;

            int mode = method / 100;
            Boolean[] modes = Utils.reverse(Integer.toString(mode)).chars().mapToObj(c -> c == 48).toArray(Boolean[]::new);
            int j = i;
            int[] p = program;
            IntUnaryOperator getArg = (arg) -> {
                boolean position = (modes.length >= arg) ? modes[arg - 1] : true;
                return (position) ? p[p[j + arg]] : p[j + arg];
            };

            Opcode opcode = opcodes.get(optcode);
            i = opcode.execute(getArg, i, program, input, r);
        }
        return r[0];
    }

    interface Opcode {
        int execute(IntUnaryOperator getArg, int i, int[] program, int input, int[] output);
    }

    static class MoveOpcode implements Opcode {
        IntPredicate move;

        public MoveOpcode(IntPredicate move) {
            this.move = move;
        }

        @Override
        public int execute(IntUnaryOperator getArg, int i, int[] program, int input, int[] output) {
            if (move.test(getArg.applyAsInt(1))) return getArg.applyAsInt(2);
            return i + 3;
        }
    }

    static class ResultOpcode implements Opcode {
        IntBinaryOperator calculate;

        public ResultOpcode(IntBinaryOperator calculate) {
            this.calculate = calculate;
        }

        @Override
        public int execute(IntUnaryOperator getArg, int i, int[] program, int input, int[] output) {
            program[program[i + 3]] = calculate.applyAsInt(getArg.applyAsInt(1), getArg.applyAsInt(2));
            return i + 4;
        }
    }
}