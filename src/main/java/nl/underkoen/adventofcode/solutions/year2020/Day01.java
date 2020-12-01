package nl.underkoen.adventofcode.solutions.year2020;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;

import java.util.List;

public class Day01 extends Solution {
    @Getter private final int day = 1;
    @Getter private final int year = 2020;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{270144, 261342720};
    }

    @Override
    protected void run(List<String> input) {
        List<Long> nums = InputUtils.asNumberList(input);

        for (int i = 0; i < nums.size(); i++) {
            long num1 = nums.get(i);
            for (int j = i; j < nums.size(); j++) {
                long num2 = nums.get(j);
                if (num1 + num2 == 2020) {
                    a = num1 * num2;
                    if (b != 0) return;
                }

                for (int k = j; k < nums.size(); k++) {
                    long num3 = nums.get(k);
                    if (num1 + num2 + num3 == 2020) {
                        b = num1 * num2 * num3;
                        if (a != 0) return;
                    }
                }
            }
        }
    }
}