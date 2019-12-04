package nl.underkoen.adventofcode;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Under_Koen on 03/12/2019.
 */
public class Day4 {
    public static void main(String[] args) {
        List<String> input = Utils.getInput(4);
        String[] range = input.get(0).split("-");
        int min = Integer.parseInt(range[0]);
        int max = Integer.parseInt(range[1]);

        int count = 0;

        for (int i = min; i < max; i++) {
            if (check(i)) count++;
        }

        System.out.println(count);
    }

    public static boolean check(int i) {
        String j = Integer.toString(i);
        int s = -1;
        int[] doubles = new int[10];
        Arrays.fill(doubles, 0);
        for (int k = 0; k < j.length(); k++) {
            int n = Integer.parseInt(j.charAt(k) + "");
            if (s == n) {
                doubles[n]++;
                continue;
            } else if (n > s) {
                s = n;
                continue;
            }
            return false;
        }
        return Arrays.stream(doubles).anyMatch(p -> p == 1);
    }
}

