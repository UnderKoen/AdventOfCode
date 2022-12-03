package nl.underkoen.adventofcode.utils;

import lombok.experimental.UtilityClass;
import nl.underkoen.adventofcode.general.sets.ESet;
import nl.underkoen.adventofcode.general.stream.EStream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public String combineMultilines(List<? extends Object> objects) {
        List<StringBuilder> lines = new ArrayList<>();
        for (Object object : objects) {
            String[] split = object.toString().split("\n");
            for (int i = 0; i < split.length; i++) {
                if (lines.size() <= i) lines.add(new StringBuilder());
                lines.get(i).append(split[i]);
            }
        }

        return String.join("\n", lines);
    }

    public EStream<Character> asStream(String s) {
        return EStream.of(s);
    }

    public List<Character> asList(String s) {
        return asStream(s).collect(Collectors.toList());
    }

    public ESet<Character> asSet(String s) {
        return ESet.of(asStream(s).collect(Collectors.toSet()));
    }
}
