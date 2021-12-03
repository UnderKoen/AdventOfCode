package nl.underkoen.adventofcode.solutions.year2020;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.MapUtils;

import java.util.*;

public class Day07 extends Solution {
    @Getter private final int day = 7;
    @Getter private final int year = 2020;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{296, 9339};
    }

    @Override
    protected void run(Input input) {
        Map<String, List<Bag>> map = new HashMap<>();
        for (String s : input) {
            String[] bags = s.split(" bags?,? ?\\.?(?:contain )?");
            for (int i = 1; i < bags.length; i++) {
                Bag bag = Bag.parse(bags[i]);
                if (bag != null) MapUtils.add(map, bags[0], bag);
            }
        }

        Map<Bag, List<String>> inverted = MapUtils.invert(map);

        a = doesContain(inverted, "shiny gold", new HashSet<>()).size();
        b = inside(map, "shiny gold") - 1;
    }

    Set<String> doesContain(Map<Bag, List<String>> map, String which, Set<String> start) {
        Bag toCheck = new Bag(which, 1);
        for (String s : map.getOrDefault(toCheck, new ArrayList<>())) {
            if (start.add(s)) doesContain(map, s, start);
        }
        return start;
    }

    long inside(Map<String, List<Bag>> map, String which) {
        return 1 + map.getOrDefault(which, new ArrayList<>()).stream()
                .mapToLong(bag -> bag.getTimes() * inside(map, bag.getBag()))
                .sum();
    }

    @Value
    public static class Bag {
        String bag;
        @EqualsAndHashCode.Exclude
        int times;

        public static Bag parse(String text) {
            if (text.equals("no other")) return null;
            String[] parts = text.split(" ", 2);
            return new Bag(parts[1], Integer.parseInt(parts[0]));
        }
    }
}