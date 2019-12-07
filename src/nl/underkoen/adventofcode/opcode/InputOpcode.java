package nl.underkoen.adventofcode.opcode;

import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;

/**
 * Created by Under_Koen on 07/12/2019.
 */
public class InputOpcode implements Opcode {
    private IntSupplier input;

    public InputOpcode(IntSupplier input) {
        this.input = input;
    }

    @Override
    public int execute(IntUnaryOperator getArg, int i, int[] program, int[] result) {
        program[program[i + 1]] = input.getAsInt();
        return i + 2;
    }
}
