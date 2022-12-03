package nl.underkoen.adventofcode.solutions.year2022;

import com.google.common.collect.Sets;
import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.HashSet;
import java.util.Set;

public class Day03 extends Solution {
    @Getter private final int day = 3;
    @Getter private final int year = 2022;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{8252, 2828};
    }

    @Override
    protected void run(Input input) {
        Set<Character> characters = new HashSet<>();
        int count = 0;

        for (String s : input) {
            String s1 = s.substring(0, s.length() / 2);
            String s2 = s.substring(s.length() / 2);

            z:
            for (char c1 : s1.toCharArray()) {
                for (char c2 : s2.toCharArray()) {
                    if (c1 == c2) {
                        if (Character.isUpperCase(c1)) {
                            a += Character.compare(c1, 'A') + 27;
                        } else {
                            a += Character.compare(c1, 'a') + 1;
                        }
                        break z;
                    }
                }
            }

            Set<Character> t = new HashSet<>();

            for (char c : s.toCharArray()) {
                t.add(c);
            }


            if (count == 0) characters = t;
            else characters = Sets.intersection(characters, t);

            if (count == 2) {
                char c = characters.stream().findAny().orElseThrow();
                if (Character.isUpperCase(c)) {
                    b += Character.compare(c, 'A') + 27;
                } else {
                    b += Character.compare(c, 'a') + 1;
                }
                count = 0;
            } else count++;
        }
    }
}