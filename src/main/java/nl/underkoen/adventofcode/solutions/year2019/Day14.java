package nl.underkoen.adventofcode.solutions.year2019;

import lombok.Getter;
import nl.underkoen.adventofcode.general.tuple.BiHolder;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Under_Koen on 13/12/2019.
 */
public class Day14 extends Solution {
    @Getter private final int day = 14;
    @Getter private final int year = 2019;

    public static Map<String, Long> remaining = new HashMap<>();

    public static BiHolder<Long, Long> convert(Map<BiHolder<String, Long>, List<BiHolder<String, Long>>> map, String start, long amount) {
        BiHolder<String, Long> out = map.keySet().stream()
                .filter(e -> e.getKey().equalsIgnoreCase(start))
                .findFirst()
                .orElse(null);

        if (out == null) {
            return new BiHolder<>(amount, amount);
        }

        int times = (int) Math.ceil((double) amount / out.getValue());

        List<BiHolder<String, Long>> in = map.get(out);
        Map<String, Long> needed = new HashMap<>(Map.ofEntries(in.toArray(new Map.Entry[0])));
        needed.keySet().forEach(s -> needed.put(s, needed.get(s) * times));

        long result = 0;

        for (Map.Entry<String, Long> e : needed.entrySet()) {
            long r = remaining.getOrDefault(e.getKey(), 0L);
            BiHolder<Long, Long> i = convert(map, e.getKey(), e.getValue() - r);
            r = (i.getKey() + r) - e.getValue();
            remaining.put(e.getKey(), r);
            result += i.getValue();
        }

        return new BiHolder<>(times * out.getValue(), result);
    }

    @Override
    protected void run(List<String> input) {
        Map<BiHolder<String, Long>, List<BiHolder<String, Long>>> convert = new HashMap<>();
        for (String s : input) {
            String[] pieces = s.split(" => ");
            String in = pieces[0];
            String out = pieces[1];

            pieces = out.split(" ");
            Long outI = Long.parseLong(pieces[0]);
            BiHolder<String, Long> outN = new BiHolder<>(pieces[1], outI);
            List<BiHolder<String, Long>> inN = new ArrayList<>();

            pieces = in.split(", ");
            for (String piece : pieces) {
                String[] parts = piece.split(" ");
                Long inI = Long.parseLong(parts[0]);
                inN.add(new BiHolder<>(parts[1], inI));
            }

            convert.put(outN, inN);
        }

        a = convert(convert, "FUEL", 1).getValue();

        long low = 0;
        long high = 1000000000000L;
        while (low < high) {
            remaining.clear();
            b = (low + high + 1) / 2;
            long ore = convert(convert, "FUEL", b).getValue();
            if (ore <= 1000000000000L) {
                low = b;
            } else {
                high = b - 1;
            }
        }
    }
}
