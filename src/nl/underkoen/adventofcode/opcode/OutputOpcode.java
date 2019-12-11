package nl.underkoen.adventofcode.opcode;

import java.util.Map;
import java.util.function.LongConsumer;
import java.util.function.LongUnaryOperator;

/**
 * Created by Under_Koen on 07/12/2019.
 */
public class OutputOpcode implements Opcode {
    public static boolean defaultPrint = true;

    private boolean print;
    private LongConsumer output;

    public OutputOpcode(LongConsumer output) {
        this(defaultPrint, output);
    }

    public OutputOpcode(boolean print, LongConsumer output) {
        this.print = print;
        this.output = output;
    }


    @Override
    public int execute(LongUnaryOperator getArg, LongUnaryOperator getArgPos, int i, long[] program, long[] result, Map<Long, Long> storage) {
        long out = getArg.applyAsLong(1);
        if (print) System.out.printf("OUTPUT: %s\n", out);
        output.accept(out);
        result[0] = out;
        return i + 2;
    }
}
