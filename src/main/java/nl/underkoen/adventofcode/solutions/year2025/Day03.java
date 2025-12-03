package nl.underkoen.adventofcode.solutions.year2025;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.stream.ELongStream;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.List;

public class Day03 extends Solution {
    @Getter private final int day = 3;
    @Getter private final int year = 2025;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{
                17087L,
                169019504359949L
        };
    }

//    @Override
//    public String[] getCorrectOutputText() {
//        return new String[]{};
//    }

    @Override
    protected void run(Input input) {
        input.asLineNumbers("").forEach(line -> {
            List<Long> numbers = line.toList();

            a += largestNumber(numbers, 2, 0);
            b += largestNumber(numbers, 12, 0);
        });

    }

    static long largestNumber(List<Long> numbers, int n, long prev) {
        if (n == 0) {
            return prev;
        }

        long largest = ELongStream.of(numbers).limit(numbers.size() - n + 1).max().orElseThrow();
        List<Long> remaining = ELongStream.of(numbers).dropWhile(l -> l != largest).dropN(1).toList();

        return largestNumber(remaining, n - 1, prev * 10 + largest);
    }
}