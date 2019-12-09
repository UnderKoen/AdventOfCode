package nl.underkoen.adventofcode.opcode;

import nl.underkoen.adventofcode.Utils;

import java.util.*;
import java.util.function.LongConsumer;
import java.util.function.LongSupplier;
import java.util.function.LongUnaryOperator;

/**
 * Created by Under_Koen on 07/12/2019.
 */
public class OpcodeRunner {
    public static int relative = 0;

    public static Map<Integer, Opcode> getDefaultOpcodes(LongSupplier input, LongConsumer output) {
        return Map.of(
                1, new ResultOpcode(Long::sum),
                2, new ResultOpcode((i1, i2) -> i1 * i2),
                3, new InputOpcode(input),
                4, new OutputOpcode(output),
                5, new MoveOpcode(i -> i != 0),
                6, new MoveOpcode(i -> i == 0),
                7, new ResultOpcode((i1, i2) -> (i1 < i2) ? 1 : 0),
                8, new ResultOpcode((i1, i2) -> (i1 == i2) ? 1 : 0),
                9, (getArg, getArgPos, i, program, result, storage) -> {
                    relative += getArg.applyAsLong(1);
                    return (int) i + 2;
                }
        );
    }

    public static long process(long[] program, Map<Integer, Opcode> opcodes) {
        relative = 0;
        program = Arrays.copyOf(program, program.length);

        Map<Long, Long> storage = new HashMap<>();

        long[] r = new long[1];
        for (int i = 0; i < program.length; ) {
            long method = program[i];
            long optcode = method % 100;

            if (optcode == 99) break;

            long mode = method / 100;
            char[] modes = Utils.reverse(Long.toString(mode)).toCharArray();
            int j = i;
            long[] p = program;
            LongUnaryOperator getArgPos = (arg) -> {
                char position = arg > modes.length ? '0' : modes[(int) arg - 1];
                long pos = 0;
                switch (position) {
                    case '0':
                        pos = p[(int) (j + arg)];
                        break;
                    case '1':
                        pos = j + arg;
                        break;
                    case '2':
                        long r2 = p[(int) (j + arg)];
                        pos = relative + r2;
                        break;
                    default:
                        System.out.println("ERROR");
                        return 0;
                }

                return pos;
            };

            LongUnaryOperator getArg = (arg) -> {
                long pos = getArgPos.applyAsLong(arg);

                if (pos >= p.length) {
                    return storage.getOrDefault(pos, 0L);
                }

                return p[(int) pos];
            };

            Opcode opcode = opcodes.get((int) optcode);
            i = opcode.execute(getArg, getArgPos, i, program, r, storage);
        }

        return r[0];
    }

    public static long process(long[] program, LongSupplier input, LongConsumer output) {
        return process(program, getDefaultOpcodes(input, output));
    }

    public static long process(long[] program, long input) {
        return process(program, getDefaultOpcodes(() -> input, p -> {
        }));
    }

    public static long process(long[] program, long[] input) {
        Stack<Long> inputS = new Stack<>();
        for (long i : input) inputS.add(i);
        return process(program, getDefaultOpcodes(inputS::pop, p -> {
        }));
    }


    public static long[] parse(List<String> input) {
        String line = input.get(0);
        input = Arrays.asList(line.split(","));
        return input.stream().mapToLong(Long::parseLong).toArray();
    }
}
