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
        while(scanner.hasNextLine()) {
            result.add(scanner.nextLine());
        }

        return result;
    }
}
