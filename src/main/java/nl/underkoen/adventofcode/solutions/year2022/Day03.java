package nl.underkoen.adventofcode.solutions.year2022;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.sets.ESet;
import nl.underkoen.adventofcode.solutions.Solution;

public class Day03 extends Solution {
    @Getter private final int day = 3;
    @Getter private final int year = 2022;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{8252, 2828};
    }

    @Override
    protected void run(Input input) {
        ESet<Character> badge = ESet.of();
        int count = 0;
        
        for (String s : input) {
            ESet<Character> s1 = ESet.chars(s.substring(0, s.length() / 2));
            ESet<Character> s2 = ESet.chars(s.substring(s.length() / 2));

            a += calc(s1.intersection(s2).any().orElseThrow());

            ESet<Character> line = ESet.chars(s);

            if (count == 0) badge = line;
            else badge = badge.intersection(line);

            if (count == 2) {
                b += calc(badge.any().orElseThrow());
                count = 0;
            } else count++;
        }
    }

    public long calc(char c) {
        if (Character.isUpperCase(c)) {
            return Character.compare(c, 'A') + 27;
        } else {
            return Character.compare(c, 'a') + 1;
        }
    }
}