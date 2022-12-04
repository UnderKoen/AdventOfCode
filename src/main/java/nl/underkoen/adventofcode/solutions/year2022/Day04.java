package nl.underkoen.adventofcode.solutions.year2022;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.sets.ESet;
import nl.underkoen.adventofcode.general.sets.HashESet;
import nl.underkoen.adventofcode.general.stream.ELongStream;
import nl.underkoen.adventofcode.general.stream.EStream;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day04 extends Solution {
    @Getter private final int day = 4;
    @Getter private final int year = 2022;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{459, 779};
    }

    @Override
    protected void run(Input input) {
        input.asNumbers("[-,]")
                .mapPairs(ELongStream::ofIncl)
                .map(EStream::toSet)
                .forEachPair((l1, l2) -> {
                    if (l1.containsAll(l2) || l2.containsAll(l1)) a++;
                    if (l1.containsAny(l2) || l2.containsAny(l1)) b++;
                });
    }
}