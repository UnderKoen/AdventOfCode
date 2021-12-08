package nl.underkoen.adventofcode.solutions.year2021;

import com.google.common.collect.Sets;
import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.stream.EStream;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day08 extends Solution {
    @Getter private final int day = 8;
    @Getter private final int year = 2021;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{342, 1068933};
    }

    @Override
    protected void run(Input input) {
        for (String line : input) {
            String[] parts = line.split(" \\| ");
            String[] signal = parts[0].split(" ");
            String[] output = parts[1].split(" ");

            Set<Integer> unique = Set.of(2, 3, 4, 7);
            a += EStream.of(output).filter(s -> unique.contains(s.length())).count();

            List<Set<Character>> all = EStream.of(signal)
                    .map(s -> s.chars()
                            .mapToObj(c -> (char) c)
                            .collect(Collectors.toSet())
                    ).toList();

            Set<Character> num1 = all.stream().filter(s -> s.size() == 2).findFirst().orElseThrow();
            Set<Character> num4 = all.stream().filter(s -> s.size() == 4).findFirst().orElseThrow();
            Set<Character> num7 = all.stream().filter(s -> s.size() == 3).findFirst().orElseThrow();
            Set<Character> num8 = all.stream().filter(s -> s.size() == 7).findFirst().orElseThrow();

            char seg_a = Sets.difference(num7, num1).iterator().next();

            Set<Character> seg_dg = EStream.of(all).filter(s -> s.size() == 5)
                    .flatMap(Collection::stream)
                    .duplicates(3)
                    .filter(c -> c != seg_a)
                    .collect(Collectors.toSet());

            char seg_d = Sets.intersection(seg_dg, num4).iterator().next();
            char seg_g = seg_dg.stream().filter(c -> c != seg_d).findFirst().orElseThrow();
            char seg_b = Sets.difference(num4, num1).stream().filter(c -> c != seg_d).findFirst().orElseThrow();

            Set<Character> num5 = all.stream()
                    .filter(s -> s.size() == 5)
                    .filter(s -> s.contains(seg_b) && s.contains(seg_d))
                    .findFirst()
                    .orElseThrow();

            char seg_f = Sets.difference(num5, Set.of(seg_a, seg_b, seg_d, seg_g)).iterator().next();
            char seg_c = Sets.difference(num1, Set.of(seg_f)).iterator().next();
            char seg_e = Sets.difference(num8, Set.of(seg_a, seg_b, seg_c, seg_d, seg_f, seg_g)).iterator().next();

            Set<Character> num0 = Sets.difference(num8, Set.of(seg_d));
            Set<Character> num2 = Sets.difference(num8, Set.of(seg_b, seg_f));
            Set<Character> num3 = Sets.difference(num8, Set.of(seg_b, seg_e));
            Set<Character> num6 = Sets.difference(num8, Set.of(seg_c));
            Set<Character> num9 = Sets.difference(num8, Set.of(seg_e));

            StringBuilder num = new StringBuilder();
            for (String part : output) {
                Set<Character> characters = part.chars().mapToObj(c -> (char) c).collect(Collectors.toSet());
                if (characters.equals(num0)) num.append(0);
                else if (characters.equals(num1)) num.append(1);
                else if (characters.equals(num2)) num.append(2);
                else if (characters.equals(num3)) num.append(3);
                else if (characters.equals(num4)) num.append(4);
                else if (characters.equals(num5)) num.append(5);
                else if (characters.equals(num6)) num.append(6);
                else if (characters.equals(num7)) num.append(7);
                else if (characters.equals(num8)) num.append(8);
                else if (characters.equals(num9)) num.append(9);
            }

            b += Long.parseLong(num.toString());
        }
    }
}