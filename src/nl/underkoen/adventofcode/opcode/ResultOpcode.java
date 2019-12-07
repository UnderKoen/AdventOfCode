package nl.underkoen.adventofcode.opcode;

import java.util.function.IntBinaryOperator;
import java.util.function.IntUnaryOperator;

/**
 * Created by Under_Koen on 07/12/2019.
 */
public class ResultOpcode implements Opcode {
    private IntBinaryOperator calculate;

    public ResultOpcode(IntBinaryOperator calculate) {
        this.calculate = calculate;
    }

    @Override
    public int execute(IntUnaryOperator getArg, int i, int[] program, int[] result) {
        program[program[i + 3]] = calculate.applyAsInt(getArg.applyAsInt(1), getArg.applyAsInt(2));
        return i + 4;
    }

}
