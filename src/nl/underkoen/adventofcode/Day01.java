package nl.underkoen.adventofcode;


import java.util.List;

/**
 * Created by Under_Koen on 01/12/2019.
 */
public class Day01 extends AdventOfCode {
    public static int calculate(int i) {
        int fuel = Math.max(i / 3 - 2, 0);
        return fuel + (fuel > 0 ? calculate(fuel) : 0);
    }

    @Override
    int getDay() {
        return 1;
    }

    @Override
    public long[] getCorrectOutput() {
        return new long[]{3471229, 5203967};
    }

    @Override
    void run(List<String> input) {
        a = input.stream()
                .mapToInt(Integer::parseInt)
                .map(i -> i / 3 - 2)
                .sum();

        b = input.stream()
                .mapToInt(Integer::parseInt)
                .map(Day01::calculate)
                .sum();
    }
}
