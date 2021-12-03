package nl.underkoen.adventofcode.solutions.year2020;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.NumberUtils;

public class Day13 extends Solution {
    @Getter private final int day = 13;
    @Getter private final int year = 2020;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{6559, 626670513163231L};
    }

    @Override
    protected void run(Input input) {
        long initial = Long.parseLong(input.get(0));
        String[] parts = input.get(1).split(",");

        long min = -1;
        long product = 1;
        long[] ids = new long[parts.length];
        for (int i = 0; i < parts.length; i++) {
            String s = parts[i];
            if (s.equals("x")) continue;
            long id = Long.parseLong(s);
            product *= id;
            ids[i] = id;
            long diff = id - (initial % id);
            if (min == -1 || diff < min) {
                min = diff;
                a = id;
            }
        }

        a *= min;

        long p, sm = 0;
        for (int i = 0; i < ids.length; i++) {
            if (ids[i] == 0) continue;
            p = product / ids[i];
            sm += (ids[i] - i) * NumberUtils.mulInv(p, ids[i]) * p;
        }
        b = sm % product;
    }
}