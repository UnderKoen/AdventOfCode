package nl.underkoen.adventofcode.opcode;

import java.util.function.IntUnaryOperator;

/**
 * Created by Under_Koen on 07/12/2019.
 */
public interface Opcode {
    int execute(IntUnaryOperator getArg, int i, int[] program, int[] result);
}
