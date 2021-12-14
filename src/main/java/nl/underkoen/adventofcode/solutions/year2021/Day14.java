package nl.underkoen.adventofcode.solutions.year2021;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.map.counter.LongMapCounter;
import nl.underkoen.adventofcode.general.stream.EStream;
import nl.underkoen.adventofcode.general.tuple.BiHolder;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.List;
import java.util.Map;

public class Day14 extends Solution {
    @Getter private final int day = 14;
    @Getter private final int year = 2021;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{3143, 4110215602456L};
    }

    @Override
    protected void run(Input input) {
        List<Input> inputs = input.asSubInputs().toList();

        String start = inputs.get(0).get(0);

        Map<String, String> map = inputs.get(1).asRegexGroupList("(.{2}) \\-> (.)")
                .map(s -> s.mapPairs(BiHolder::new).findFirst().orElseThrow())
                .collect(BiHolder.toMap());

        List<String> pairs = EStream.of(start).mapWithPrev((c1, c2) -> c1 + "" + c2).toList();
        LongMapCounter<String> counter = new LongMapCounter<>();
        counter.increaseAll(pairs);

        LongMapCounter<String> fresh = new LongMapCounter<>();
        for (int i = 0; i < 40; i++) {
            for (String s : map.keySet()) {
                if (!counter.containsKey(s)) continue;

                long v = counter.get(s);
                String s2 = map.get(s);

                fresh.increase(s.charAt(0) + "" + s2.charAt(0), v);
                fresh.increase(s2.charAt(0) + "" + s.charAt(1), v);
            }

            counter = fresh;
            fresh = new LongMapCounter<>();

            if (i == 9) a = count(counter, start);
        }

        b = count(counter, start);
    }

    public long count(LongMapCounter<String> counter, String start) {
        LongMapCounter<Character> charCounter = new LongMapCounter<>();

        counter.forEach((s, v) -> charCounter.increase(s.charAt(0), v));
        charCounter.increase(start.charAt(start.length() - 1));

        return charCounter.max() - charCounter.min();
    }
}