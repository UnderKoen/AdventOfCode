package nl.underkoen.adventofcode.utils;

import lombok.experimental.UtilityClass;
import nl.underkoen.adventofcode.general.position.AbstractPosition;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@UtilityClass
public class ConwayUtils {
    public <T extends AbstractPosition<T>> Set<T> calculate(Set<T> positions, int rounds, Rule<T> rule) {
        return calculate(positions, rounds, rule, AbstractPosition::getNeighbours);
    }

    public <T extends AbstractPosition<T>> Set<T> calculate(Set<T> positions, int rounds, Rule<T> rule, Function<T, Set<T>> neighbours) {
        for (int i = 0; i < rounds; i++) positions = round(positions, rule, neighbours);
        return positions;
    }

    public <T extends AbstractPosition<T>> Set<T> round(Set<T> positions, Rule<T> rule) {
        return round(positions, rule, AbstractPosition::getNeighbours);
    }

    public <T extends AbstractPosition<T>> Set<T> round(Set<T> positions, Rule<T> rule, Function<T, Set<T>> neighbours) {
        Map<T, Long> amount = new HashMap<>();
        for (T position : positions) {
            neighbours.apply(position).forEach(p -> MapUtils.increaseLong(amount, p));
        }

        return amount.entrySet().stream()
                .filter(e -> rule.keep(e.getValue(), positions.contains(e.getKey()), e.getKey()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    @FunctionalInterface
    public interface Rule<T extends AbstractPosition<T>> {
        boolean keep(long neighbours, boolean state, T position);
    }
}
