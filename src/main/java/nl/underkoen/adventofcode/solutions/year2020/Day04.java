package nl.underkoen.adventofcode.solutions.year2020;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day04 extends Solution {
    @Getter private final int day = 4;
    @Getter private final int year = 2020;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{254, 184};
    }

    @Override
    protected void run(List<String> input) {
        StringBuilder s2 = new StringBuilder();
        for (String line : input) {
            if (line.isBlank()) {
                check(s2.toString());
                s2.setLength(0);
            } else {
                s2.append(line).append(" ");
            }
        }
    }

    public static Set<String> validEyeColors = Set.of("amb", "blu", "brn", "grn", "gry", "hzl", "oth");

    public void check(String line) {
        line = line.trim();
        Map<String, String> map = Arrays.stream(line.split(" "))
                .map(field -> field.split(":"))
                .collect(Collectors.toMap(part -> part[0], part -> part[1]));
        map.remove("cid");

        if (map.size() == 7) {
            a++;

            if (checkRange(map.get("byr"), 1920, 2002)) return;
            if (checkRange(map.get("iyr"), 2010, 2020)) return;
            if (checkRange(map.get("eyr"), 2020, 2030)) return;
            if (checkRegex(map.get("hgt"), "^((59|6\\d|7[0-6])in)|(1([5-8]\\d|9[0-3])cm)$")) return;
            if (checkRegex(map.get("hcl"), "^#[0-9a-f]{6}$")) return;
            if (!validEyeColors.contains(map.get("ecl"))) return;
            if (checkRegex(map.get("pid"), "^\\d{9}$")) return;

            b++;
        }
    }

    public boolean checkRange(String value, int lower, int higher) {
        int v = Integer.parseInt(value);
        return v < lower || v > higher;
    }

    public boolean checkRegex(String value, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return !pattern.matcher(value).find();
    }
}