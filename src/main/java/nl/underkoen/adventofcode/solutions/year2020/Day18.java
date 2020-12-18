package nl.underkoen.adventofcode.solutions.year2020;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.List;

public class Day18 extends Solution {
    @Getter private final int day = 18;
    @Getter private final int year = 2020;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{24650385570008L, 158183007916215L};
    }

    @Override
    protected void run(List<String> input) {
        for (String s : input) {
            a += execute(s);
        }
    }

    private long execute(String line) {
        int deep = 0;
        StringBuilder inside = new StringBuilder();
        for (char c : line.toCharArray()) {
            if (c == ')') {
                if (--deep == 0) {
                    line = line.replace("(" + inside.toString() + ")", execute(inside.toString()) + "");
                    inside.setLength(0);
                }
            }
            if (deep >= 1) inside.append(c);
            if (c == '(') deep++;
        }

        String op = "";
        long num = 0;
        boolean changed = true;
        while (changed) {
            changed = false;
            String[] s = line.split(" ");
            for (int i = 0; i < s.length; i++) {
                if (i == 0) num = Long.parseLong(s[i]);
                else if (i % 2 == 1) op = s[i];
                else {
                    long num2 = Long.parseLong(s[i]);
                    if ("+".equals(op)) {
                        line = line.replace(num + " + " + num2, num + num2 + "");
                        changed = true;
                        break;
                    } else {
//                        System.out.println(s[i]);
                    }
                    num = num2;
                }
            }
        }

        num = 0;
        String[] s = line.split(" ");
        for (int i = 0; i < s.length; i++) {
            if (i == 0) num = Long.parseLong(s[i]);
            else if (i % 2 == 1) op = s[i];
            else {
                long num2 = Long.parseLong(s[i]);
                if ("*".equals(op)) {
                    num *= num2;
                } else {
//                    System.out.println(s[i]);
                }
            }
        }

        return num;
    }
}