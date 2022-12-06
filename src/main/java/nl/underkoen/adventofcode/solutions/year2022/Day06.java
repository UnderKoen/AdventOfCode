package nl.underkoen.adventofcode.solutions.year2022;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;

public class Day06 extends Solution {
    @Getter private final int day = 6;
    @Getter private final int year = 2022;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{1892, 2313};
    }

    @Override
    protected void run(Input input) {
        Queue<Character> cs = new ArrayDeque<>();
        Queue<Character> csb = new ArrayDeque<>();
        long i = 0;
        for (char c : input.get(0).toCharArray()) {
            cs.add(c);
            csb.add(c);
            if (cs.size() == 5) cs.remove();
            if (csb.size() == 15) csb.remove();
            i++;
            if (a == 0 && new HashSet<>(cs).size() == 4) a = i;
            if (b == 0 && new HashSet<>(csb).size() == 14) b = i;
        }
    }
}