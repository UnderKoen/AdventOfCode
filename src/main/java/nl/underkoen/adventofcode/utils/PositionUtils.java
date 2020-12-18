package nl.underkoen.adventofcode.utils;

import nl.underkoen.adventofcode.general.position.CastedPosition;
import nl.underkoen.adventofcode.general.position.PositionND;

import java.util.*;
import java.util.stream.Collectors;

public class PositionUtils {
    public static PositionND min(Collection<? extends PositionND> positions) {
        return positions.stream()
                .map(p -> (PositionND) p)
                .reduce(PositionND::min)
                .orElseThrow();
    }

    public static <T extends CastedPosition<T>> T minCasted(Collection<T> positions) {
        return positions.stream()
                .reduce(CastedPosition::min)
                .orElseThrow();
    }

    public static PositionND max(Collection<? extends PositionND> positions) {
        return positions.stream()
                .map(p -> (PositionND) p)
                .reduce(PositionND::max)
                .orElseThrow();
    }

    public static <T extends CastedPosition<T>> T maxCasted(Collection<T> positions) {
        return positions.stream()
                .reduce(CastedPosition::max)
                .orElseThrow();
    }

    public static List<PositionND> between(PositionND p1, PositionND p2) {
        PositionND min = p1.min(p2);
        PositionND max = p1.max(p2);

        List<PositionND> r = new ArrayList<>();
        addDimension(min, max, min.copy(), 0, r);
        return r;
    }

    @SuppressWarnings("unchecked")
    public static <T extends CastedPosition<T>> List<T> between(T p1, T p2) {
        return between((PositionND) p1, p2).stream()
                .map(p -> (T) p)
                .collect(Collectors.toList());
    }

    private static void addDimension(PositionND min, PositionND max, PositionND current, int dimension, List<PositionND> positions) {
        if (dimension >= min.getDimensions()) {
            positions.add(current);
            return;
        }

        for (long n = min.getN(dimension); n <= max.getN(dimension); n++) {
            PositionND pos = current.copy();
            pos.setN(dimension, n);
            addDimension(min, max, pos, dimension + 1, positions);
        }
    }

    public static List<PositionND> rectangle(PositionND origin, long width, long height) {
        return between(origin, origin.copyAdd(width - 1, height - 1));
    }

    public static <T extends CastedPosition<T>> List<T> rectangle(T origin, long width, long height) {
        return between(origin, origin.copyAdd(width - 1, height - 1));
    }

    public static Map<PositionND, Long> countNeighbours(Iterable<PositionND> positions) {
        Map<PositionND, Long> count = new HashMap<>();
        for (PositionND position : positions) {
            MapUtils.increaseAll(count, position.getNeighbours(), 0L);
        }
        return count;
    }
}
