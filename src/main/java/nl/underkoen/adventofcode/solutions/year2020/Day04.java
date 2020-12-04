package nl.underkoen.adventofcode.solutions.year2020;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.List;
import java.util.regex.Pattern;

public class Day04 extends Solution {
    @Getter private final int day = 4;
    @Getter private final int year = 2020;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{254, 184};
    }

    @Override
    protected void run(List<String> input) {
        String s = String.join("\n", input);

        Pattern part1 = Pattern.compile("((byr|iyr|eyr|hgt|hcl|ecl|pid|cid):([^\\n ]+)( |\\n|$)){8}|((byr|iyr|eyr|hgt|hcl|ecl|pid):([^\\n ]+)( |\\n|$)){7}");
        Pattern part2 = Pattern.compile("(((byr:(19[2-9]\\d|200[0-2]))|(iyr:(201\\d|2020))|(eyr:(202\\d|2030))|(hgt:(((59|6\\d|7[0-6])in)|(1([5-8]\\d|9[0-3])cm)))|(hcl:#[0-9a-f]{6})|(ecl:(amb|blu|brn|gry|grn|hzl|oth))|(pid:(\\d{9}))|(cid:([^ \\n]*)))( |\\n|$)){8}|(((byr:(19[2-9]\\d|200[0-2]))|(iyr:(201\\d|2020))|(eyr:(202\\d|2030))|(hgt:(((59|6\\d|7[0-6])in)|(1([5-8]\\d|9[0-3])cm)))|(hcl:#[0-9a-f]{6})|(ecl:(amb|blu|brn|gry|grn|hzl|oth))|(pid:(\\d{9})))( |\\n|$)){7}");

        a = part1.matcher(s).results().count();
        b = part2.matcher(s).results().count();
    }
}