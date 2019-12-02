package nl.underkoen.adventofcode;


import java.util.List;

/**
 * Created by Under_Koen on 01/12/2019.
 */
public class Day1 {
    public static void main(String[] args) {
        List<String> input = Utils.getInput(1);
        int total = input.stream()
                .mapToInt(Integer::parseInt)
                .map(i -> i / 3 - 2)
                .sum();

        System.out.println("Result day1a");
        System.out.println(total);

        total = input.stream()
                .mapToInt(Integer::parseInt)
                .map(Day1::calculate)
                .sum();

        System.out.println("\nResult day1b:");
        System.out.println(total);
    }

    public static int calculate(int i) {
        int fuel = Math.max(i / 3 - 2, 0);
        return fuel + (fuel > 0 ? calculate(fuel) : 0);
    }
}
