package nl.underkoen.adventofcode.solutions.year2021;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.map.counter.LongMapCounter;
import nl.underkoen.adventofcode.solutions.Solution;

public class Day06 extends Solution {
    @Getter private final int day = 6;
    @Getter private final int year = 2021;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{386755, 1732731810807L};
    }

    @Override
    protected void run(Input input) {
        LongMapCounter<Long> timer = new LongMapCounter<>();
        input.asLineNumbers().flatMap(s -> s)
                .forEach(timer::increase);

        for (int day = 1; day <= 256; day++) {
            long newFish = timer.getOrDefault(0L, 0L);

            for (long i = 0; i < 9; i++) {
                long num = timer.getOrDefault(i + 1, 0L);
                timer.put(i, num);
            }

            timer.increase(8L, newFish);
            timer.increase(6L, newFish);

            if (day == 80) a = timer.sum();
        }

        b = timer.sum();
    }
}