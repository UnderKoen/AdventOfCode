package nl.underkoen.adventofcode.solutions.year2019;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.NumberUtils;
import nl.underkoen.adventofcode.utils.StringUtils;

/**
 * Created by Under_Koen on 15/12/2019.
 */
public class Day16 extends Solution {
    @Getter private final int day = 16;
    @Getter private final int year = 2019;

    public static Long calcPart(String line, int start) {
        int[] nums = StringUtils.getDigits(line.substring(start));

        int length = nums.length;
        for (int i = 0; i < 100; i++) {
            int s = 0;
            for (int j = length - 1; j >= 0; j--) {
                s += nums[j];
                nums[j] = s % 10;
            }
        }

        return NumberUtils.toNumber(nums, 8);
    }

    public static Long calc(String line) {
        int[] nums = StringUtils.getDigits(line);

        int length = nums.length;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < length; j++) {
                long sum = 0;
                for (int k = j; k < length; k++) sum += nums[k] * getPhase(j, k);
                nums[j] = (int) Math.abs(sum % 10);
            }
        }

        return NumberUtils.toNumber(nums, 8);
    }

    public static int getPhase(int phase, int pos) {
        return (2 - ++pos / ++phase % 4) % 2;
    }

    @Override
    public long[] getCorrectOutput() {
        return new long[]{10332447, 14288025};
    }

    @Override
    protected void run(Input input) {
        String line = input.get(0);
        a = calc(line);

        line = line.repeat(10000);
        int offset = Integer.parseInt(line.substring(0, 7));

        b = calcPart(line, offset);
    }
}
