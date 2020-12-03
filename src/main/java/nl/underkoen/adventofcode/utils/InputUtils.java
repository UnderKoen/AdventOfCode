package nl.underkoen.adventofcode.utils;

import lombok.experimental.UtilityClass;
import nl.underkoen.adventofcode.general.Position;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@UtilityClass
public class InputUtils {
    public final String DEFAULT_SPLIT = "[, ] ?";

    public Stream<Long> asNumberList(List<String> input) {
        return asNumberList(input, DEFAULT_SPLIT);
    }

    public Stream<Long> asNumberList(List<String> input, String split) {
        return input.stream()
                .flatMap(s -> Arrays.stream(s.split(split)))
                .map(Long::parseLong);
    }

    public Stream<Stream<Long>> asLineNumberList(List<String> input) {
        return asLineNumberList(input, DEFAULT_SPLIT);
    }

    public Stream<Stream<Long>> asLineNumberList(List<String> input, String split) {
        return input.stream()
                .map(s -> Arrays.stream(s.split(split)))
                .map(s -> s.map(Long::parseLong));
    }

    public Stream<Position> asPositionList(List<String> input) {
        return asPositionList(input, DEFAULT_SPLIT);

    }

    public Stream<Position> asPositionList(List<String> input, String split) {
        return input.stream()
                .map(s -> Arrays.stream(s.split(split)))
                .map(s -> s.map(Long::parseLong))
                .map(s -> s.collect(Collectors.toList()))
                .map(s -> new Position(s.get(0), s.get(1)));
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

    public char[][] as2dArray (List<String> input) {
        return input.stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }

    public Stream<Stream<String>> asRegexGroupList(List<String> input, String regex) {
        Pattern pattern = Pattern.compile(regex);

        return input.stream()
                .map(pattern::matcher)
                .map(Matcher::results)
                .map(s -> s.flatMap(r -> IntStream
                        .range(0, r.groupCount())
                        .mapToObj(i -> r.group(i + 1))));
    }

    public Stream<Stream<Long>> asAllNumbers(List<String> input) {
        return asRegexGroupList(input, "(-?\\d+)")
                .map(s -> s.map(Long::parseLong));
    }
}
