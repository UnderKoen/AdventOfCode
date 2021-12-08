package nl.underkoen.adventofcode.utils;

import lombok.experimental.UtilityClass;
import nl.underkoen.adventofcode.general.position.Position;
import nl.underkoen.adventofcode.general.stream.EStream;
import nl.underkoen.adventofcode.general.tuple.BiHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@UtilityClass
public class InputUtils {
    public final String DEFAULT_SPLIT = "[, ] ?";

    private Long tryParse(String s) {
        try {
            return Long.parseLong(s);
        } catch (Exception e) {
            return null;
        }
    }

    public EStream<Long> asNumbers(List<String> input) {
        return asNumbers(input, DEFAULT_SPLIT);
    }

    public EStream<Long> asNumbers(List<String> input, String regex) {
        return EStream.of(input)
                .flatMap(s -> Arrays.stream(s.split(regex)))
                .map(InputUtils::tryParse)
                .filter(Objects::nonNull);
    }

    public EStream<EStream<Long>> asLineNumbers(List<String> input) {
        return asLineNumbers(input, DEFAULT_SPLIT);
    }

    public EStream<EStream<Long>> asLineNumbers(List<String> input, String regex) {
        return EStream.of(input)
                .map(s -> EStream.of(s.split(regex)))
                .map(s -> s.map(InputUtils::tryParse)
                        .filter(Objects::nonNull));
    }

    public EStream<EStream<String>> asSplitLine(List<String> input) {
        return asSplitLine(input, DEFAULT_SPLIT);
    }

    public EStream<EStream<String>> asSplitLine(List<String> input, String regex) {
        return EStream.of(input)
                .map(s -> EStream.of(s.split(regex)));
    }

    public EStream<String> asSplit(List<String> input, String regex) {
        return asSplitLine(input, regex).flatMap(s -> s);
    }

    public EStream<String> asSplit(List<String> input) {
        return asSplitLine(input).flatMap(s -> s);
    }

    public EStream<Position> asPositionList(List<String> input) {
        return asPositionList(input, DEFAULT_SPLIT);

    }

    public EStream<Position> asPositionList(List<String> input, String regex) {
        return EStream.of(input)
                .map(s -> Arrays.stream(s.split(regex)))
                .map(s -> s.map(InputUtils::tryParse)
                        .filter(Objects::nonNull))
                .map(s -> s.collect(Collectors.toList()))
                .map(s -> new Position(s.get(0), s.get(1)));
    }

    public <T> EStream<T> mapChar(List<String> input, BiFunction<Character, Position, T> map) {
        Position position = new Position(-1, -1);
        return EStream.of(input)
                .map(String::chars)
                .map(s -> s.mapToObj(c -> (char) c))
                .flatMap(s -> {
                    position.setX(-1);
                    position.addY(1);
                    return s.map(c -> map.apply(c, position.add(1, 0).copy()));
                });
    }

    public char[][] as2dArray(List<String> input) {
        return input.stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }

    public EStream<EStream<String>> asRegexGroupList(List<String> input, String regex) {
        Pattern pattern = Pattern.compile(regex);

        return EStream.of(input)
                .map(pattern::matcher)
                .map(Matcher::results)
                .map(s -> s.flatMap(
                        r -> IntStream
                                .range(0, r.groupCount())
                                .mapToObj(i -> r.group(i + 1)))
                ).map(EStream::of);
    }

    public EStream<EStream<Long>> asAllNumbers(List<String> input) {
        return asRegexGroupList(input, "(-?\\d+)")
                .map(s -> s.map(Long::parseLong));
    }

    public EStream<List<String>> asSubInputs(List<String> input) {
        Stream.Builder<List<String>> builder = Stream.builder();

        List<String> current = new ArrayList<>();
        builder.accept(current);
        for (String s : input) {
            if (s.isBlank()) {
                current = new ArrayList<>();
                builder.accept(current);
            } else {
                current.add(s);
            }
        }

        return EStream.of(builder.build())
                .filter(l -> !l.isEmpty());
    }

    public EStream<EStream<Character>> asCharacters(List<String> input) {
        return EStream.of(input)
                .map(String::chars)
                .map(s -> s.mapToObj(i -> (char) i))
                .map(EStream::of);
    }

    public <T> EStream<BiHolder<Integer, T>> asIndexedStream(List<T> input) {
        Stream.Builder<BiHolder<Integer, T>> builder = Stream.builder();
        for (int i = 0; i < input.size(); i++) {
            builder.accept(new BiHolder<>(i, input.get(i)));
        }
        return EStream.of(builder.build());
    }

    public <T> EStream<BiHolder<Integer, T>> asIndexedStream(T[] input) {
        Stream.Builder<BiHolder<Integer, T>> builder = Stream.builder();
        for (int i = 0; i < input.length; i++) {
            builder.accept(new BiHolder<>(i, input[i]));
        }
        return EStream.of(builder.build());
    }

    public String asString(List<String> input) {
        return String.join("\n", input);
    }
}
