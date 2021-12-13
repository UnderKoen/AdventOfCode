package nl.underkoen.adventofcode.solutions.year2021;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.position.Position;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.PositionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Day13 extends Solution {
    @Getter private final int day = 13;
    @Getter private final int year = 2021;

    @Override
    public String[] getCorrectOutputText() {
        return new String[]{"602", "CAFJHZCK"};
    }

    @Override
    protected void run(Input input) {
        List<Input> inputs = input.asSubInputs().toList();

        Set<Position> positions = inputs.get(0).asPositions().toSet();

        List<List<String>> lists = inputs.get(1).asRegexGroupList("fold along (x|y)=(\\d+)")
                .map(Stream::toList)
                .toList();

        for (List<String> list : lists) {
            long v = Long.parseLong(list.get(1));

            if (list.get(0).equals("x")) {
                positions.stream()
                        .sorted()
                        .filter(p -> p.getX() > v)
                        .forEach(p -> positions.add(new Position(v - (p.getX() - v), p.getY())));

                positions.removeIf(p -> p.getX() >= v);
            } else {
                positions.stream()
                        .sorted()
                        .filter(p -> p.getY() > v)
                        .forEach(p -> positions.add(new Position(p.getX(), v - (p.getY() - v))));

                positions.removeIf(p -> p.getY() >= v);
            }

            if (a == 0) a = positions.size();
        }

        textB = PositionUtils.asLettersString(positions);
    }
}