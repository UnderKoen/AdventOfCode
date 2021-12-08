package nl.underkoen.adventofcode.solutions.year2021;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.map.counter.LongMapCounter;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.NumberUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day08 extends Solution {
    @Getter private final int day = 8;
    @Getter private final int year = 2021;

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
        for (String s : input) {
            String[] parts = s.split(" \\| ");
            List<String> p1 = Arrays.asList(parts[0].split(" "));
            List<String> p2 = Arrays.asList(parts[1].split(" "));

            a += p2.stream().filter(s2 -> s2.length() == 2).count();
            a += p2.stream().filter(s2 -> s2.length() == 3).count();
            a += p2.stream().filter(s2 -> s2.length() == 4).count();
            a += p2.stream().filter(s2 -> s2.length() == 7).count();


            List<String> all = new ArrayList<>(p1);
            List<Character> num1 = new ArrayList<>(all.stream().filter(r -> r.length() == 2).findFirst().orElseThrow().chars().mapToObj(c -> (char) c).toList());
            List<Character> num7 = new ArrayList<>(all.stream().filter(r -> r.length() == 3).findFirst().orElseThrow().chars().mapToObj(c -> (char) c).toList());
            List<Character> num4 = new ArrayList<>(all.stream().filter(r -> r.length() == 4).findFirst().orElseThrow().chars().mapToObj(c -> (char) c).toList());
            List<Character> num8 = new ArrayList<>(all.stream().filter(r -> r.length() == 7).findFirst().orElseThrow().chars().mapToObj(c -> (char) c).toList());

            num7.removeAll(num1);
            num4.removeAll(num1);

            List<List<Character>> num_235 = all.stream().filter(r -> r.length() == 5).map(s3 -> s3.chars().mapToObj(c -> (char) c).toList()).toList();
            LongMapCounter<Character> vertical = new LongMapCounter<>();
            num_235.forEach(vertical::increaseAll);

            List<Character> seg_dg = new ArrayList<>();
            for (Character character : vertical.keySet()) {
                if (vertical.get(character) == 3) seg_dg.add(character);
            }

            char seg_a = num7.get(0);
            seg_dg.removeAll(List.of(seg_a));

            List<Character> nums_g = new ArrayList<>(seg_dg);
            nums_g.removeAll(num4);
            char seg_g = nums_g.get(0);

            seg_dg.removeAll(List.of(seg_g));
            char seg_d = seg_dg.get(0);

            num4.removeAll(List.of(seg_d));
            char seg_b = num4.get(0);

            List<Character> num5 = new ArrayList<>(num_235.stream().filter(l -> l.contains(seg_b) && l.contains(seg_d)).findFirst().orElseThrow());
            num5.removeAll(List.of(seg_a, seg_b, seg_d, seg_g));
            char seg_f = num5.get(0);

            num1.removeAll(List.of(seg_f));
            char seg_c = num1.get(0);

            num8.removeAll(List.of(seg_a, seg_b, seg_c, seg_d, seg_f, seg_g));

            char seg_e = num8.get(0);

            Set<Character> com_0 = Set.of(seg_a, seg_b, seg_c, seg_e, seg_f, seg_g);
            Set<Character> com_1 = Set.of(seg_c, seg_f);
            Set<Character> com_2 = Set.of(seg_a, seg_c, seg_d, seg_e, seg_g);
            Set<Character> com_3 = Set.of(seg_a, seg_c, seg_d, seg_f, seg_g);
            Set<Character> com_4 = Set.of(seg_b, seg_c, seg_d, seg_f);
            Set<Character> com_5 = Set.of(seg_a, seg_b, seg_d, seg_f, seg_g);
            Set<Character> com_6 = Set.of(seg_a, seg_b, seg_d, seg_e, seg_f, seg_g);
            Set<Character> com_7 = Set.of(seg_a, seg_c, seg_f);
            Set<Character> com_8 = Set.of(seg_a, seg_b, seg_c, seg_d, seg_e, seg_f, seg_g);
            Set<Character> com_9 = Set.of(seg_a, seg_b, seg_c, seg_d, seg_f, seg_g);

            List<Integer> output = new ArrayList<>();
            for (String s1 : p2) {
                Set<Character> characters = s1.chars().mapToObj(c -> (char) c).collect(Collectors.toSet());
                if (characters.equals(com_0)) {
                    output.add(0);
                } else if (characters.equals(com_1)) {
                    output.add(1);
                } else if (characters.equals(com_2)) {
                    output.add(2);
                } else if (characters.equals(com_3)) {
                    output.add(3);
                } else if (characters.equals(com_4)) {
                    output.add(4);
                } else if (characters.equals(com_5)) {
                    output.add(5);
                } else if (characters.equals(com_6)) {
                    output.add(6);
                } else if (characters.equals(com_7)) {
                    output.add(7);
                } else if (characters.equals(com_8)) {
                    output.add(8);
                } else if (characters.equals(com_9)) {
                    output.add(9);
                }
            }


            b += NumberUtils.toNumber(output.stream().mapToInt(i -> i).toArray());
        }
    }
}