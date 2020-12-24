package nl.underkoen.adventofcode.utils;

import nl.underkoen.adventofcode.general.position.AbstractPosition;
import nl.underkoen.adventofcode.general.position.Position;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class PositionUtils {
    public static <T extends AbstractPosition<T>> T min(Collection<T> positions) {
        return positions.stream()
                .reduce(AbstractPosition::min)
                .orElseThrow();
    }

    public static <T extends AbstractPosition<T>> T max(Collection<T> positions) {
        return positions.stream()
                .reduce(AbstractPosition::max)
                .orElseThrow();
    }

    public static <T extends AbstractPosition<T>> List<T> between(T p1, T p2) {
        T min = p1.min(p2);
        T max = p1.max(p2);

        List<T> r = new ArrayList<>();
        addDimension(min, max, min.copy(), 0, r);
        return r;
    }

    private static <T extends AbstractPosition<T>> void addDimension(T min, T max, T current, int dimension, List<T> positions) {
        if (dimension >= min.getDimensions()) {
            positions.add(current);
            return;
        }

        for (long n = min.getN(dimension); n <= max.getN(dimension); n++) {
            T pos = current.copy();
            pos.setN(dimension, n);
            addDimension(min, max, pos, dimension + 1, positions);
        }
    }

    public static <T extends AbstractPosition<T>> List<T> rectangle(T origin, long width, long height) {
        return between(origin, origin.copyAdd(width - 1, height - 1));
    }

    public static <T extends AbstractPosition<T>> Map<T, Long> countNeighbours(Iterable<T> positions) {
        return countNeighbours(positions, p -> true);
    }

    public static <T extends AbstractPosition<T>> Map<T, Long> countNeighbours(Iterable<T> positions, Predicate<T> filter) {
        Map<T, Long> amount = new HashMap<>();
        for (T position : positions) {
            position.getNeighbours().stream()
                    .filter(filter)
                    .forEach(p -> MapUtils.increaseLong(amount, p));
        }
        return amount;
    }

    public static void print(Collection<Position> all) {
        print(all, (p, b) -> b ? "#" : ".");
    }

    public static void print(Collection<Position> all, BiFunction<Position, Boolean, String> convert) {
        Position min = min(all);
        Position max = max(all);

        for (long y = min.getY(); y <= max.getY(); y++) {
            for (long x = min.getX(); x <= max.getX(); x++) {
                Position pos = new Position(x, y);
                System.out.print(convert.apply(pos, all.contains(pos)));
            }
            System.out.println();
        }
    }
}
