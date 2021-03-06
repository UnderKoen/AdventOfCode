package nl.underkoen.adventofcode.solutions.year2019.opcode;

import lombok.Value;

import java.util.Map;
import java.util.function.LongPredicate;
import java.util.function.LongUnaryOperator;

/**
 * Created by Under_Koen on 07/12/2019.
 */
@Value
public class MoveOpcode implements Opcode {
    LongPredicate move;

    @Override
    public int execute(LongUnaryOperator getArg, LongUnaryOperator getArgPos, int i, long[] program, long[] result, Map<Long, Long> storage) {
        if (move.test(getArg.applyAsLong(1))) return (int) getArg.applyAsLong(2);
        return i + 3;
    }
}
