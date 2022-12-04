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
        return new long[]{};
    }

    @Override
    protected void run(Input input) {
        for (List<Long> line : input.asAllNumbers().map(EStream::toList).toList()) {
            ESet<Long> l1 = ELongStream.of(line.get(0), -line.get(1) + 1).toSet();
            ESet<Long> l2 = ELongStream.of(line.get(2), -line.get(3) + 1).toSet();

            if (l1.containsAll(l2)) a++;
            else if (l2.containsAll(l1)) a++;

            if(l1.stream().anyMatch(l2::contains)) b++;
            else if(l2.stream().anyMatch(l1::contains)) b++;
        }
    }
}