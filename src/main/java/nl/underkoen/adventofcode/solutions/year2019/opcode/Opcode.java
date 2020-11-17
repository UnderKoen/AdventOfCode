package nl.underkoen.adventofcode.solutions.year2019.opcode;

import java.util.Map;
import java.util.function.LongUnaryOperator;

/**
 * Created by Under_Koen on 07/12/2019.
 */
public interface Opcode {
    int execute(LongUnaryOperator getArg,
                LongUnaryOperator getArgPos,
                int i,
                long[] program,
                long[] result,
                Map<Long, Long> storage);
}
