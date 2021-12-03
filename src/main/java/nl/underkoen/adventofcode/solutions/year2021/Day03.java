package nl.underkoen.adventofcode.solutions.year2021;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.ArrayList;
import java.util.List;

public class Day03 extends Solution {
    @Getter private final int day = 3;
    @Getter private final int year = 2021;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{4006064, 5941884};
    }

    private static Long count(List<String> list, int index) {
        long count = 0;
        for (String s : list) if (s.charAt(index) == '1') count++;
        return count;
    }

    private static Long reduce(List<String> list, boolean invert) {
        for (int i = 0; list.size() > 1; i++) {
            long ones = count(list, i);
            long zeros = list.size() - ones;

            char c;
            if (!invert) c = ones >= zeros ? '1' : '0';
            else c = ones >= zeros ? '0' : '1';


            int finalI = i;
            list.removeIf(line -> line.charAt(finalI) == c);
        }

        return Long.parseLong(list.get(0), 2);
    }

    @Override
    protected void run(Input input) {
        int length = input.get(0).length();
        long gamma = 0, episode = 0;

        for (int j = 0; j < length; j++) {
            long ones = count(input, j);
            long zero = input.size() - ones;

            episode <<= 1;
            gamma <<= 1;
            if (ones > zero) gamma += 1;
            else episode += 1;
        }

        a = gamma * episode;

        //o2
        b = reduce(new ArrayList<>(input), false);

        //co2
        b *= reduce(new ArrayList<>(input), true);

    }
}