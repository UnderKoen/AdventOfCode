package nl.underkoen.adventofcode.opcode;

/**
 * Created by Under_Koen on 09/12/2019.
 */
public interface Mode {
    long getPosition(long[] program, int position, long arg);

    class PositionMode implements Mode {
        @Override
        public long getPosition(long[] program, int position, long arg) {
            return program[position + (int) arg];
        }
    }

    class ImmediateMode implements Mode {
        @Override
        public long getPosition(long[] program, int position, long arg) {
            return position + arg;
        }
    }

    class RelativeMode implements Mode {
        @Override
        public long getPosition(long[] program, int position, long arg) {
            long r = program[position + (int) arg];
            return OpcodeRunner.relative + r;
        }
    }
}