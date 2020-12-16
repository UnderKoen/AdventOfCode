package nl.underkoen.adventofcode.solutions.year2020;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.MapUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day16 extends Solution {
    @Getter private final int day = 16;
    @Getter private final int year = 2020;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{26053, 1515506256421L};
    }

//    @Override
//    public String[] getCorrectOutputText() {
//        return new String[]{};
//    }

    @Override
    protected void run(List<String> input) {
        List<List<Long>> consctraints = new ArrayList<>();

        int start = 0;
        String your = "";
        for (int i = 0; i < input.size(); i++) {
            String s = input.get(i);
            if (s.contains("or")) {
                List<Long> c = new ArrayList<>();
                s = s.split(": ")[1];
                String[] choices = s.split(" or ");
                for (String choice : choices) {
                    String[] limit = choice.split("-");
                    long lower = Long.parseLong(limit[0]);
                    long higher = Long.parseLong(limit[1]);
                    c.add(lower);
                    c.add(higher);
                }
                consctraints.add(c);
            }

            if (s.contains("your ticket")) {
                your = input.get(i + 1);
                start = i + 4;
                break;
            }
        }

        for (int i = start; i < input.size(); i++) {
            String s = input.get(i);
            for (String s1 : s.split(",")) {
                long t = Long.parseLong(s1);
                boolean valid = false;
                for (List<Long> c : consctraints) {
                    if (t >= c.get(0) && t <= c.get(1) || t >= c.get(2) && t <= c.get(3)) {
                        valid = true;
                        break;
                    }
                }
                if (!valid) {
                    input.remove(i--);
                    a += t;
                }
            }
        }

        Map<Integer, List<Integer>> valid = new HashMap<>();
        for (int i = 0; i < consctraints.size(); i++) {
            for (int j = 0; j < consctraints.size(); j++) {
                MapUtils.add(valid, i, j);
            }
        }

        for (int i = start; i < input.size(); i++) {
            String s = input.get(i);
            int z = 0;
            for (String s1 : s.split(",")) {
                long t = Long.parseLong(s1);
                int j = 0;
                for (List<Long> c : consctraints) {
                    if (!(t >= c.get(0) && t <= c.get(1) || t >= c.get(2) && t <= c.get(3))) {
                        int f = j;
                        valid.get(z).removeIf(p -> p == f);
                    }
                    j++;
                }
                z++;
            }
        }

        boolean changed = true;
        while (changed) {
            changed = false;
            for (int i = 0; i < consctraints.size(); i++) {
                List<Integer> z = valid.get(i);
                if (z.size() == 1) {
                    for (int j = 0; j < consctraints.size(); j++) {
                        if (j == i) continue;
                        if (valid.get(j).removeIf(p -> p.equals(z.get(0)))) changed = true;
                    }
                }
            }
        }

        valid = MapUtils.invert(valid);

        b = 1;
        String[] parts = your.split(",");
        for (int i = 0; i < 6; i++) {
            int j = valid.get(i).get(0);
            b *= Long.parseLong(parts[j]);
        }
    }
}