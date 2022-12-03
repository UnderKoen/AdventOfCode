package nl.underkoen.adventofcode.solutions.year2021;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.map.counter.HashMapCounter;
import nl.underkoen.adventofcode.general.map.counter.MapCounter;
import nl.underkoen.adventofcode.general.position.Position;
import nl.underkoen.adventofcode.general.position.multi.Line;
import nl.underkoen.adventofcode.solutions.Solution;

public class Day05 extends Solution {
    @Getter private final int day = 5;
    @Getter private final int year = 2021;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{7468, 22364};
    }

    @Override
    protected void run(Input input) {
        MapCounter<Position, Integer> count = new HashMapCounter<>(0);
        MapCounter<Position, Integer> countDiagonals = new HashMapCounter<>(0);

        input.asAllNumbers()
                .flatMap(s -> s)
                .mapPairs(Position::new)
                .mapPairs(Line::new)
                .peek(countDiagonals::increaseAll)
                .filter(Line::isHorizontal)
                .forEach(count::increaseAll);

        a = count.values().stream()
                .filter(i -> i >= 2)
                .count();

        b = countDiagonals.values().stream()
                .filter(i -> i >= 2)
                .count();
    }
}