package nl.underkoen.adventofcode.opcode;

import nl.underkoen.adventofcode.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;

/**
 * Created by Under_Koen on 07/12/2019.
 */
public class OpcodeRunner {
    public static Map<Integer, Opcode> getDefaultOpcodes(IntSupplier input, IntConsumer output) {
        return Map.of(
                1, new ResultOpcode(Integer::sum),
                2, new ResultOpcode((i1, i2) -> i1 * i2),
                3, new InputOpcode(input),
                4, new OutputOpcode(output),
                5, new MoveOpcode(i -> i != 0),
                6, new MoveOpcode(i -> i == 0),
                7, new ResultOpcode((i1, i2) -> (i1 < i2) ? 1 : 0),
                8, new ResultOpcode((i1, i2) -> (i1 == i2) ? 1 : 0)
        );
    }

    public static int process(int[] program, Map<Integer, Opcode> opcodes) {
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
            i = opcode.execute(getArg, i, program, r);
        }
        return r[0];
    }

    public static int process(int[] program, IntSupplier input, IntConsumer output) {
        return process(program, getDefaultOpcodes(input, output));
    }

    public static int process(int[] program, int input) {
        return process(program, getDefaultOpcodes(() -> input, (p) -> {
        }));
    }

    public static int process(int[] program, int[] input) {
        Stack<Integer> inputS = new Stack<>();
        for (int i : input) inputS.add(i);
        return process(program, getDefaultOpcodes(inputS::pop, (p) -> {
        }));
    }


    public static int[] parse(List<String> input) {
        String line = input.get(0);
        input = Arrays.asList(line.split(","));
        return input.stream().mapToInt(Integer::parseInt).toArray();
    }
}
