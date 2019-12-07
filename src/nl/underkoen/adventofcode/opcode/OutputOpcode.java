package nl.underkoen.adventofcode.opcode;

import java.util.function.IntConsumer;
import java.util.function.IntUnaryOperator;

/**
 * Created by Under_Koen on 07/12/2019.
 */
public class OutputOpcode implements Opcode {
    private boolean print;
    private IntConsumer output;

    public OutputOpcode(IntConsumer output) {
        this(false, output);
    }

    public OutputOpcode(boolean print, IntConsumer output) {
        this.print = print;
        this.output = output;
    }


    @Override
    public int execute(IntUnaryOperator getArg, int i, int[] program, int[] result) {
        int out = getArg.applyAsInt(1);
        if (print) System.out.println(out);
        output.accept(out);
        result[0] = out;
        return i + 2;
    }
}
