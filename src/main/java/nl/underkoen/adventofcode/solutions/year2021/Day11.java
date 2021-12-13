package nl.underkoen.adventofcode.solutions.year2021;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.map.counter.LongMapCounter;
import nl.underkoen.adventofcode.general.position.Position;
import nl.underkoen.adventofcode.general.tuple.BiHolder;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.HashSet;
import java.util.Set;

public class Day11 extends Solution {
    @Getter private final int day = 11;
    @Getter private final int year = 2021;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{1647, 348};
    }

    @Override
    protected void run(Input input) {
        LongMapCounter<Position> map = new LongMapCounter<>(
                input.mapChar((c, p) -> new BiHolder<>(p, Character.getNumericValue(c)))
                        .map(p -> p.mapValue(v -> (long) v))
                        .collect(BiHolder.toMap())
        );


        for (int i = 0; b == 0; i++) {
            map.keySet().forEach(map::increase);

            Set<Position> flashes = new HashSet<>();
            boolean changed;
            do {
                changed = false;
                for (Position position : map.keySet()) {
                    if (map.get(position) <= 9 || !flashes.add(position)) continue;

                    changed = true;
                    position.getNeighbours().stream()
                            .filter(map::containsKey)
                            .forEach(map::increase);
                }
            } while (changed);

            if (flashes.size() == map.size()) {
                b = i + 1;
                return;
            }

            if (i < 100) a += flashes.size();
            for (Position flash : flashes) {
                map.put(flash, 0L);
            }
        }
    }
}