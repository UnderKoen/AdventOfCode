package nl.underkoen.adventofcode.solutions.year2023;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.regex.Pattern;

public class Day01 extends Solution {
    @Getter private final int day = 1;
    @Getter private final int year = 2023;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{};
    }

//    @Override
//    public String[] getCorrectOutputText() {
//        return new String[]{};
//    }

    @Override
    protected void run(Input input) {
        for (String line : input) {
            int first = -1;
            int last = -1;
            for (char c : line.toCharArray()) {
                if (Character.isDigit(c)) {
                    if (first == -1) first = c - '0';

                    last = c - '0';
                }
            }

            a += first * 10 + last;
        }

        for (String line : input) {
            var regex = "^.*?(one|two|three|four|five|six|seven|eight|nine|\\d).*(one|two|three|four|five|six|seven|eight|nine|\\d).*?$";

            var matcher = Pattern.compile(regex).matcher(line);
            if (!matcher.find()) {
                //one digit
                regex = "(one|two|three|four|five|six|seven|eight|nine|\\d)";
                matcher = Pattern.compile(regex).matcher(line);

                if (!matcher.find()) continue;

                var digit = convert(matcher.group(1));

                b += digit + digit * 10;
            } else {
                var first = convert(matcher.group(1));
                var last = convert(matcher.group(2));

                b += first * 10 + last;
            }
        }
    }

    static int convert(String str) {
        switch (str) {
            case "one":
                return 1;
            case "two":
                return 2;
            case "three":
                return 3;
            case "four":
                return 4;
            case "five":
                return 5;
            case "six":
                return 6;
            case "seven":
                return 7;
            case "eight":
                return 8;
            case "nine":
                return 9;
            default:
                return Integer.parseInt(str);
        }
    }
}