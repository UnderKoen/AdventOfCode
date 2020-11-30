package nl.underkoen.adventofcode.solutions.year2018;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.List;

public class Day02 extends Solution {
    @Getter private final int day = 2;
    @Getter private final int year = 2018;

    @Override
    public String[] getCorrectOutputText() {
        return new String[]{"5880", "tiwcdpbseqhxryfmgkvjujvza"};
    }

    @Override
    protected void run(List<String> input) {
        int two = 0;
        int three = 0;

        for (String s : input) {
            boolean c2 = false;
            boolean c3 = false;
            for (char c : s.toCharArray()) {
                long count = s.chars().filter(i -> i == c).count();
                if (count == 2) c2 = true;
                if (count == 3) c3 = true;
            }
            if (c2) two++;
            if (c3) three++;
        }

        a = two * three;

        for (int i = 0; i < input.size(); i++) {
            String s = input.get(i);
            compareLoop:
            for (int j = i + 1; j < input.size(); j++) {
                String compare = input.get(j);
                int incorrect = 0;
                for (int c = 0; c < s.length(); c++) {
                    if (s.charAt(c) != compare.charAt(c)) {
                        incorrect++;
                        textB = new StringBuilder(s).deleteCharAt(c).toString();
                    }
                    if (incorrect > 1) continue compareLoop;
                }
                if (incorrect == 1) return;
            }
        }
    }
}