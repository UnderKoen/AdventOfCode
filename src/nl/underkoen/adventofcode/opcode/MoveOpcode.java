package nl.underkoen.adventofcode.opcode;

import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;

/**
 * Created by Under_Koen on 07/12/2019.
 */
public class MoveOpcode implements Opcode {
    private IntPredicate move;

    public MoveOpcode(IntPredicate move) {
        this.move = move;
    }

    @Override
    public int execute(IntUnaryOperator getArg, int i, int[] program, int[] result) {
        if (move.test(getArg.applyAsInt(1))) return getArg.applyAsInt(2);
        return i + 3;
    }
}
