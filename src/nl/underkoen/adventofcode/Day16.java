package nl.underkoen.adventofcode;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by Under_Koen on 15/12/2019.
 */
public class Day16 extends AdventOfCode {
    public static String calcPart(String line, int start, int l) {
        line = line.substring(start);
        int[] nums = line.chars()
                .mapToObj(c -> (char) c)
                .map(c -> Character.toString(c))
                .mapToInt(Integer::parseInt)
                .toArray();

        int length = nums.length;
        for (int i = 0; i < 100; i++) {
            long s = IntStream.of(nums).sum();
            for (int j = 0; j < length; j++) {
                long t = s;
                s -= nums[j];
                nums[j] = (int) Math.abs(t % 10);
            }
        }

        StringBuilder out = new StringBuilder();
        for (int i = 0; i < l; i++) {
            out.append(nums[i]);
        }

        return out.toString();
    }

    public static String calc(String line) {
        int[] nums = line.chars()
                .mapToObj(c -> (char) c)
                .map(c -> Character.toString(c))
                .mapToInt(Integer::parseInt)
                .toArray();

        int length = nums.length;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < length; j++) {
                long sum = 0;
                for (int k = j; k < length; k++) {
                    sum += nums[k] * getPhase(j, k);
                }
                nums[j] = (int) Math.abs(sum % 10);
            }
        }

        StringBuilder out = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            out.append(nums[i]);
        }

        return out.toString();
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
        a = Long.parseLong(calc(line));

        line = line.repeat(10000);

        int offset = Integer.parseInt(line.substring(0, 7));
        b = Long.parseLong(calcPart(line, offset, 8));
    }
}
