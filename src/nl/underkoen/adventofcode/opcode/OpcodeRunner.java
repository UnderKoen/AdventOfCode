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
                    return i + 2;
                }
        );
    }

    public static Map<Character, Mode> getDefaultModes() {
        return Map.of(
                '0', new Mode.PositionMode(),
                '1', new Mode.ImmediateMode(),
                '2', new Mode.RelativeMode()
        );
    }

    public static long process(long[] program, Map<Integer, Opcode> opcodes, Map<Character, Mode> modes) {
        program = Arrays.copyOf(program, program.length);

        relative = 0;
        Map<Long, Long> storage = new HashMap<>();

        long[] r = new long[1];
        for (int i = 0; i < program.length; ) {
            long method = program[i];
            long optcode = method % 100;

            if (optcode == 99) break;

            long mode = method / 100;
            char[] modeChars = Utils.reverse(Long.toString(mode)).toCharArray();

            int j = i;
            long[] p = program;

            LongUnaryOperator getArgPos = (arg) -> {
                char modeC = arg > modeChars.length ? '0' : modeChars[(int) arg - 1];
                if (modes.containsKey(modeC)) {
                    return modes.get(modeC).getPosition(p, j, arg);
                } else {
                    System.out.println("ERROR: Unkown mode: " + modeC);
                    return 0;
                }
            };

            LongUnaryOperator getArg = getArgPos.andThen((arg) -> {
                if (arg >= p.length) {
                    return storage.getOrDefault(arg, 0L);
                }
                return p[(int) arg];
            });

            Opcode opcode = opcodes.get((int) optcode);
            i = opcode.execute(getArg, getArgPos, i, program, r, storage);
        }

        return r[0];
    }

    public static long process(long[] program, LongSupplier input, LongConsumer output) {
        return process(program, getDefaultOpcodes(input, output), getDefaultModes());
    }

    public static long process(long[] program, long input) {
        return process(program, () -> input, p -> {
        });
    }

    public static long process(long[] program, long[] input) {
        Stack<Long> inputS = new Stack<>();
        for (long i : input) inputS.add(i);
        return process(program, inputS::pop, p -> {
        });
    }


    public static long[] parse(List<String> input) {
        String line = input.get(0);
        input = Arrays.asList(line.split(","));
        return input.stream().mapToLong(Long::parseLong).toArray();
    }
}
