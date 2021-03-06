package nl.underkoen.adventofcode.solutions.year2019.opcode;

import lombok.Value;

import java.util.Map;
import java.util.function.LongBinaryOperator;
import java.util.function.LongUnaryOperator;

/**
 * Created by Under_Koen on 07/12/2019.
 */
@Value
public class ResultOpcode implements Opcode {
    LongBinaryOperator calculate;

    @Override
    public int execute(LongUnaryOperator getArg, LongUnaryOperator getArgPos, int i, long[] program, long[] result, Map<Long, Long> storage) {
        long pos = getArgPos.applyAsLong(3);
        long val = calculate.applyAsLong(getArg.applyAsLong(1), getArg.applyAsLong(2));

        if (pos >= program.length) {
            storage.put(pos, val);
        } else {
            program[(int) pos] = val;
        }
        return i + 4;
    }

}
