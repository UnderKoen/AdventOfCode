package nl.underkoen.adventofcode;

import java.util.List;

/**
 * Created by Under_Koen on 15/12/2019.
 */
public class Day16 extends AdventOfCode {
    public static Long calcPart(String line, int start) {
        int[] nums = Utils.getDigits(line.substring(start));

        int length = nums.length;
        for (int i = 0; i < 100; i++) {
            int s = 0;
            for (int j = length - 1; j >= 0; j--) {
                s += nums[j];
                nums[j] = s % 10;
            }
        }

        return Utils.toNumber(nums, 8);
    }

    public static Long calc(String line) {
        int[] nums = Utils.getDigits(line);

        int length = nums.length;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < length; j++) {
                long sum = 0;
                for (int k = j; k < length; k++) sum += nums[k] * getPhase(j, k);
                nums[j] = (int) Math.abs(sum % 10);
            }
        }

        return Utils.toNumber(nums, 8);
    }

    public static int getPhase(int phase, int pos) {
        return (2 - ++pos / ++phase % 4) % 2;
    }

    @Override
    int getDay() {
        return 16;
    }

    @Override
    public long[] getCorrectOutput() {
        return new long[]{10332447, 14288025};
    }

    @Override
    void run(List<String> input) {
        String line = input.get(0);
        a = calc(line);

        line = line.repeat(10000);
        int offset = Integer.parseInt(line.substring(0, 7));

        b = calcPart(line, offset);
    }
}
