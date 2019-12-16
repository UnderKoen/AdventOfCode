package nl.underkoen.adventofcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * Created by Under_Koen on 01/12/2019.
 */
public class Utils {
    public static List<String> getInput(int day) {
        Scanner scanner = new Scanner(Objects.requireNonNull(Utils.class.getClassLoader().getResourceAsStream("day" + day + ".txt")));

        List<String> result = new ArrayList<>();
        while (scanner.hasNextLine()) {
            result.add(scanner.nextLine());
        }

        return result;
    }

    public static String reverse(String str) {
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length / 2; i++) {
            char temp = chars[i];
            chars[i] = chars[chars.length - i - 1];
            chars[chars.length - i - 1] = temp;

        }
        return String.copyValueOf(chars);
    }

    public static int[] getDigits(String line) {
        return line.chars()
                .mapToObj(c -> (char) c)
                .map(c -> Character.toString(c))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    public static long toNumber(int[] digits) {
        return toNumber(digits, digits.length);
    }

    public static long toNumber(int[] digits, int max) {
        long r = 0;
        int until = Math.min(digits.length, max) - 1;
        for (int i = 0; i <= until; i++) {
            r += Math.pow(10, until - i) * digits[i];
        }
        return r;
    }
}
