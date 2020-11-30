package nl.underkoen.adventofcode.solutions.year2018;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day05 extends Solution {
    @Getter private final int day = 5;
    @Getter private final int year = 2018;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{11590, 4504};
    }

    @Override
    protected void run(List<String> input) {
        String polymer = input.get(0);

        polymer = react(polymer);
        a = polymer.length();

        List<String> shortened = new ArrayList<>();
        for (char c = 'a'; c <= 'z'; c++) {
            String s = polymer.replaceAll("[" + c + Character.toUpperCase(c) + "]", "");
            shortened.add(react(s));
        }

        b = shortened.stream().min(Comparator.comparingInt(String::length)).orElseThrow().length();
    }

    public String react(String polymer) {
        boolean changed = true;
        while (changed) {
            changed = false;
            for (char c = 'a'; c <= 'z'; c++) {
                String r = polymer.replaceAll(c  + "" + Character.toUpperCase(c), "");
                r = r.replaceAll(Character.toUpperCase(c) + "" + c, "");
                if (!polymer.equals(r)) changed = true;
                polymer = r;
            }
        }
        return polymer;
    }
}