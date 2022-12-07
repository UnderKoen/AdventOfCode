package nl.underkoen.adventofcode.solutions.year2022;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.map.collection.HashMapSet;
import nl.underkoen.adventofcode.general.map.collection.MapSet;
import nl.underkoen.adventofcode.general.sets.ESet;
import nl.underkoen.adventofcode.general.tuple.BiHolder;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.*;

public class Day07 extends Solution {
    @Getter private final int day = 7;
    @Getter private final int year = 2022;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{2104783, 5883165};
    }

    @Override
    protected void run(Input input) {
        MapSet<String, BiHolder<String, Long>> tree = new HashMapSet<>();
        Stack<String> cwd = new Stack<>();
        for (String s : input) {
            String[] split = s.split(" ");
            if (s.charAt(0) == '$') {
                if (split[1].equalsIgnoreCase("cd")) {
                    if (split[2].equalsIgnoreCase("..")) cwd.pop();
                    else cwd.push(split[2]);
                }
            } else {
                if (split[0].equalsIgnoreCase("dir")) {
                    tree.add(cwd.toString(), new BiHolder<>(split[1], -1L));
                } else {
                    tree.add(cwd.toString(), new BiHolder<>(split[1], Long.parseLong(split[0])));
                }
            }
        }

        Map<String, Long> values = new HashMap<>();
        while (values.size() != tree.size()) {
            for (Map.Entry<String, ESet<BiHolder<String, Long>>> entry : tree.entrySet()) {
                List<BiHolder<String, Long>> list = entry.getValue().stream().map(b -> b.getValue() == -1 ? b.mapValue((k, v) -> {
                    List<String> cwd2 = new ArrayList<>(Arrays.asList(entry.getKey().replaceAll("[\\[\\]]", "").split(", ")));
                    cwd2.add(k);

                    if (values.containsKey(cwd2.toString())) return values.get(cwd2.toString());
                    return v;
                }) : b).toList();
                if (list.stream().allMatch(b -> b.getValue() != -1)) {
                    values.put(entry.getKey(), list.stream().mapToLong(BiHolder::getValue).sum());
                }
            }
        }

        a = values.values().stream().mapToLong(l -> l).filter(l -> l <= 100000).sum();

        b = values.values().stream().mapToLong(l -> l).filter(l -> l >= (30000000 - (70000000 - values.get("[/]")))).min().orElseThrow();
    }
}