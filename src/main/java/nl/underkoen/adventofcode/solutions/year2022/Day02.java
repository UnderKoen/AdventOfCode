package nl.underkoen.adventofcode.solutions.year2022;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.*;

public class Day02 extends Solution {
    @Getter private final int day = 2;
    @Getter private final int year = 2022;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{};
    }

//    @Override
//    public String[] getCorrectOutputText() {
//        return new String[]{};
//    }

    @Override
    protected void run(Input input) {
        boolean[][] win = new boolean[][]{
                new boolean[]{false, true, false},
                new boolean[]{false, false, true},
                new boolean[]{true, false, false},
        };

        boolean[][] loss = new boolean[][]{
                new boolean[]{false, false, true},
                new boolean[]{true, false, false},
                new boolean[]{false, true, false},
        };

        for (String line : input) {
            char opp = line.charAt(0);
            int x = -Character.compare('A',opp);

            char you = line.charAt(2);
            int y = -Character.compare('X', you);

            a += y + 1;

            if (x == y) {
                a += 3;
            } else if (win[x][y]) {
                a += 6;
            }
        }

        for (String line : input) {
            char opp = line.charAt(0);
            int x = -Character.compare('A',opp);

            char you = line.charAt(2);
            int y = -Character.compare('X', you);


            if (y == 0) {
                if (loss[x][0]) b += 1;
                if (loss[x][1]) b += 2;
                if (loss[x][2]) b += 3;

            } else if (y == 1) {
                b += x + 1;
                b += 3;
            } else {
                b += 6;
                if (win[x][0]) b += 1;
                if (win[x][1]) b += 2;
                if (win[x][2]) b += 3;
            }
        }
    }
}