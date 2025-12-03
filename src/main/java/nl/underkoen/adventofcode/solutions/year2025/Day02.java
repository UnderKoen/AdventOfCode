package nl.underkoen.adventofcode.solutions.year2025;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.stream.ELongStream;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class Day02 extends Solution {
    @Getter private final int day = 2;
    @Getter private final int year = 2025;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{
                30209762079L,
                43872163557L
        };
    }

    @Override
    protected void run(Input input) {
        List<Long> all = input.asLineNumbers("[-,]").findFirst().orElseThrow().toList();

        List<Predicate<Long>> ranges = new ArrayList<>();
        ELongStream.of(all).forEachPair((from, to) -> {
            ranges.add(num -> num >= from && num <= to);
        });

        String max = ELongStream.of(all).max().orElseThrow().toString();
        int maxLength = max.length() / 2 + 1;

        Set<Long> found = new HashSet<>();

        for (Long i = 1L; i <= Long.parseLong("9".repeat(maxLength)); i++) {
            String s = i.toString();
            int maxReps = (max.length() / s.length());
            for (int j = 2; j <= maxReps; j++) {
                s += i;

                try {
                    long num = Long.parseLong(s);
                    if (ranges.stream().anyMatch(r -> r.test(num))) {
                        if (found.add(num)) {
                            if (j == 2) a += num;
                            b += num;
                        }
                    }
                } catch (Exception e) {
                    break;
                }
            }
        }
    }


}