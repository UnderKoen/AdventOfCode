package nl.underkoen.adventofcode.solutions.year2021;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;

import java.util.List;
import java.util.stream.Stream;

public class Day02 extends Solution {
    @Getter private final int day = 2;
    @Getter private final int year = 2021;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{2147104, 2044620088};
    }

    @Override
    protected void run(Input input) {
        long horizontal = 0;
        long depth = 0;
        long aim = 0;

        List<List<String>> lists = InputUtils.asSplitLine(input)
                .map(Stream::toList)
                .toList();

        for (List<String> list : lists) {
            int x = Integer.parseInt(list.get(1));

            switch (list.get(0)) {
                case "forward" -> {
                    horizontal += x;
                    depth += aim * x;
                }
                case "up" -> aim -= x;
                case "down" -> aim += x;
            }
        }

        a = horizontal * aim;
        b = depth * horizontal;
    }
}