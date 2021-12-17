package nl.underkoen.adventofcode.general.position.area;

import nl.underkoen.adventofcode.general.position.AbstractPosition;
import nl.underkoen.adventofcode.utils.PositionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Area<T extends AbstractPosition<T>> {
    private final T p1;
    private final T p2;

    public Area(T p1, T p2) {
        this.p1 = p1.min(p2);
        this.p2 = p2.max(p1);
    }

    public Area(T origin, long width, long height) {
        this(origin, origin.copyAdd(width - 1, height - 1));
    }

    public Area(Collection<T> list) {
        this(PositionUtils.min(list), PositionUtils.max(list));
    }

    public List<T> between() {
        return PositionUtils.between(p1, p2);
    }

    public T min() {
        return PositionUtils.min(List.of(p1, p2));
    }

    public T max() {
        return PositionUtils.max(List.of(p1, p2));
    }

    public Set<T> neighbours(T position) {
        return position.getNeighbours()
                .stream()
                .filter(p -> p.inside(p1, p2))
                .collect(Collectors.toSet());
    }

    public Set<T> directNeighbours(T position) {
        return position.getDirectNeighbours()
                .stream()
                .filter(p -> p.inside(p1, p2))
                .collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return "Area{" +
                "p1=" + p1 +
                ", p2=" + p2 +
                '}';
    }
}
