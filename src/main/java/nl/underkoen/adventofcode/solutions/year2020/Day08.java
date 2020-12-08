package nl.underkoen.adventofcode.solutions.year2020;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day08 extends Solution {
    @Getter private final int day = 8;
    @Getter private final int year = 2020;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{};
    }

//    @Override
//    public String[] getCorrectOutputText() {
//        return new String[]{};
//    }

    @Override
    protected void run(List<String> input) {
        a = test(input);

        for (int i = 0; i < input.size(); i++) {
            List<String> l = new ArrayList<>(input);
            String s = l.get(i);
            if (l.get(i).contains("nop")) s = l.get(i).replace("nop", "jmp");
            if (l.get(i).contains("jmp")) s = l.get(i).replace("jmp", "nop");
            l.set(i, s);
            b = test(l);
            if (!normal) return;
        }
    }

    public static boolean normal = false;

    public int test(List<String> input) {
        normal = false;
        Set<Integer> done = new HashSet<>();

        int j = 0;
        for (int i = 0; i < input.size(); i++) {

            String s = input.get(i);
            if (!done.add(i)) {
                normal = true;
                break;
            };
            int z = Integer.parseInt(s.split(" ")[1]);
            switch (s.split(" ")[0]) {
                case "acc":
                    j += z;
                    break;
                case "jmp":
                    i += z - 1;
                    break;
                case "nop":
                    break;
            }
            i %= input.size();
        }

        return j;
    }
}