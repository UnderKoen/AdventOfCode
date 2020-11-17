package nl.underkoen.adventofcode.solutions.year2019;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Under_Koen on 07/12/2019.
 */
public class Day08 extends Solution {
    @Getter private final int day = 8;
    @Getter private final int year = 2019;

    public static long countChar(char which, List<Character> characters) {
        return characters.stream().filter(c -> c == which).count();
    }

    @Override
    public long[] getCorrectOutput() {
        System.out.println("Should out put FAHEF in big letters");
        return new long[]{1330};
    }

    @Override
    protected void run(List<String> input) {
        String line = input.get(0);
        List<List<Character>> layers = new ArrayList<>();

        for (int l = 0; l < line.length() / 150; l++) {
            layers.add(
                    line.substring(l * 150, (l + 1) * 150).chars()
                            .mapToObj(c -> (char) c)
                            .collect(Collectors.toList())
            );
        }

        a = layers.stream()
                .sorted(Comparator.comparingLong((l) -> countChar('0', l)))
                .map(l -> countChar('1', l) * countChar('2', l))
                .findFirst()
                .orElse(0L);

        List<Character> imageLayer = layers.stream()
                .reduce((l1, l2) -> {
                    for (int j = 0; j < l2.size(); j++) {
                        if (l1.get(j) == '2') l1.set(j, l2.get(j));
                    }
                    return l1;
                })
                .orElseThrow();

        StringBuilder image = new StringBuilder();

        for (int j = 0; j < imageLayer.size(); j++) {
            char c = imageLayer.get(j);
            if (c == '0') image.append("  ");
            else image.append("##");
            if (j % 25 == 24) image.append('\n');
        }

        //Should print FAHEF
        System.out.println(image);
    }
}
