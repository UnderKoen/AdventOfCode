package nl.underkoen.adventofcode.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtils {
    public String reverse(String str) {
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length / 2; i++) {
            char temp = chars[i];
            chars[i] = chars[chars.length - i - 1];
            chars[chars.length - i - 1] = temp;

        }
        return String.copyValueOf(chars);
    }

    public int[] getDigits(String line) {
        return line.chars()
                .mapToObj(c -> (char) c)
                .map(c -> Character.toString(c))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
