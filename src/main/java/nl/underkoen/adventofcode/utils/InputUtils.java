package nl.underkoen.adventofcode.utils;

import lombok.experimental.UtilityClass;
import nl.underkoen.adventofcode.general.Position;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class InputUtils {
    public final String DEFAULT_SPLIT = ", ?";

    public List<Integer> asNumberList(List<String> input) {
        return asNumberList(input, DEFAULT_SPLIT);
    }

    public List<Integer> asNumberList(List<String> input, String split) {
        return input.stream()
                .flatMap(s -> Arrays.stream(s.split(split)))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public List<List<Integer>> asLineNumberList(List<String> input) {
        return asLineNumberList(input, DEFAULT_SPLIT);
    }

    public List<List<Integer>> asLineNumberList(List<String> input, String split) {
        return input.stream()
                .map(s -> Arrays.stream(s.split(split)))
                .map(s -> s.map(Integer::parseInt))
                .map(s -> s.collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    public List<Position> asPositionList(List<String> input) {
        return asPositionList(input, DEFAULT_SPLIT);

    }

    public List<Position> asPositionList(List<String> input, String split) {
        return input.stream()
                .map(s -> Arrays.stream(s.split(split)))
                .map(s -> s.map(Integer::parseInt))
                .map(s -> s.collect(Collectors.toList()))
                .map(s -> new Position(s.get(0), s.get(1)))
                .collect(Collectors.toList());
    }

    public <T> Stream<T> mapChar(List<String> input, BiFunction<Character, Position, T> map) {
        Position position = new Position(-1, -1);
        return input.stream()
                .map(String::chars)
                .map(s -> s.mapToObj(c -> (char) c))
                .flatMap(s -> {
                    position.setX(-1);
                    position.addY(1);
                    return s.map(c -> map.apply(c, position.add(1, 0).copy()));
                });
    }
}
