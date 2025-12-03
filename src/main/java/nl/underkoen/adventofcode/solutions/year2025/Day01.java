package nl.underkoen.adventofcode.solutions.year2025;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.stream.EStream;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.Objects;

public class Day01 extends Solution {
    @Getter private final int day = 1;
    @Getter private final int year = 2025;

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
        int i = 50;
        for (EStream<String> stream : input.asRegexGroupList("(.)(\\d+)").toList()) {
            var list = stream.toList();
            var direction = list.get(0);
            int amount = Integer.parseInt(list.get(1));


            if (Objects.equals(direction, "L")) {
                for (int j = 0; j < amount; j++) {
                    i--;
                    i += 100;
                    i %= 100;
                    if (i == 0) {
                        b++;
                    }
                }
            } else {
                for (int j = 0; j < amount; j++) {
                    i++;
                    i %= 100;
                    if (i == 0) {
                        b++;
                    }
                }
            }

            if (i == 0) {
                a++;
            }
        }

    }
}