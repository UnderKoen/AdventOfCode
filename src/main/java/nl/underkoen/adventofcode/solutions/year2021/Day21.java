package nl.underkoen.adventofcode.solutions.year2021;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.tuple.BiHolder;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day21 extends Solution {
    @Getter private final int day = 21;
    @Getter private final int year = 2021;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{678468, 131180774190079L};
    }

    @Override
    protected void run(Input input) {
        List<Long> start = input.asAllNumbers()
                .map(s -> s.skip(1).findAny().orElseThrow())
                .toList();

        long p1 = start.get(0);
        long p2 = start.get(1);


        BiHolder<Long, Long> chances = chances(true, p1, p2, 0, 0);
        b = Math.max(chances.getKey(), chances.getValue());

        long scoreP1 = 0;
        long scoreP2 = 0;

        long i = 0;
        while (scoreP1 < 1000 && scoreP2 < 1000) {
            if (i % 2 == 0) {
                p1 += ++i;
                p1 += ++i;
                p1 += ++i;

                p1 = p1 % 10;
                if (p1 == 0) p1 = 10;

                scoreP1 += p1;
            } else {
                p2 += ++i;
                p2 += ++i;
                p2 += ++i;

                p2 = p2 % 10;
                if (p2 == 0) p2 = 10;

                scoreP2 += p2;
            }
        }

        if (scoreP1 >= 1000) {
            a = scoreP2 * i;
        } else {
            a = scoreP1 * i;
        }
    }

    public static Map<Data, BiHolder<Long, Long>> cache = new HashMap<>();

    public static BiHolder<Long, Long> chances(boolean turn, long p1, long p2, long scoreP1, long scoreP2) {
        Data data = new Data(turn, p1, p2, scoreP1, scoreP2);
        if (cache.containsKey(data)) {
            return cache.get(data);
        }

        if (scoreP1 >= 21) {
            var t = new BiHolder<>(1L, 0L);
            cache.put(data, t);
            return t;
        }
        if (scoreP2 >= 21) {
            var t = new BiHolder<>(0L, 1L);
            cache.put(data, t);
            return t;
        }

        if (turn) {
            Long p1Win = 0L;
            Long p2Win = 0L;
            for (int r1 = 1; r1 <= 3; r1++) {
                for (int r2 = 1; r2 <= 3; r2++) {
                    for (int r3 = 1; r3 <= 3; r3++) {
                        int total = r1 + r2 + r3;
                        long test = increase(p1, total);
                        BiHolder<Long, Long> chances = chances(false, test, p2, scoreP1 + test, scoreP2);
                        p1Win += chances.getKey();
                        p2Win += chances.getValue();
                    }
                }
            }

            var t = new BiHolder<>(p1Win, p2Win);
            cache.put(data, t);
            return t;
        } else {
            Long p1Win = 0L;
            Long p2Win = 0L;
            for (int r1 = 1; r1 <= 3; r1++) {
                for (int r2 = 1; r2 <= 3; r2++) {
                    for (int r3 = 1; r3 <= 3; r3++) {
                        int total = r1 + r2 + r3;
                        long test = increase(p2, total);
                        BiHolder<Long, Long> chances = chances(true, p1, test, scoreP1, scoreP2 + test);
                        p1Win += chances.getKey();
                        p2Win += chances.getValue();
                    }
                }
            }

            var t = new BiHolder<>(p1Win, p2Win);
            cache.put(data, t);
            return t;
        }
    }

    public static long increase(long score, long with) {
        score += with;
        score %= 10;
        if (score == 0) score = 10;
        return score;
    }

    public record Data(boolean turn, long p1, long p2, long scoreP1, long scoreP2) {
    }
}