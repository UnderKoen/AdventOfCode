package nl.underkoen.adventofcode.general.position.area;

import nl.underkoen.adventofcode.general.map.collection.HashMapList;
import nl.underkoen.adventofcode.general.map.collection.MapList;
import nl.underkoen.adventofcode.general.position.AbstractPosition;
import nl.underkoen.adventofcode.utils.PositionUtils;

import java.util.*;
import java.util.stream.Collectors;

public class Area<T extends AbstractPosition<T>> {
    private final T min;
    private final T max;

    public Area(T p1, T p2) {
        this.min = p1.min(p2);
        this.max = p2.max(p1);
    }

    public Area(T origin, long width, long height) {
        this(origin, origin.copyAdd(width - 1, height - 1));
    }

    public Area(Collection<T> list) {
        this(PositionUtils.min(list), PositionUtils.max(list));
    }

    public List<T> between() {
        return PositionUtils.between(min, max);
    }

    public boolean within(Area<T> area) {
        return min.inside(area) && max.inside(area);
    }

    public T min() {
        return min;
    }

    public T max() {
        return max;
    }

    public Set<T> neighbours(T position) {
        return position.getNeighbours()
                .stream()
                .filter(p -> p.inside(min, max))
                .collect(Collectors.toSet());
    }

    public Set<T> directNeighbours(T position) {
        return position.getDirectNeighbours()
                .stream()
                .filter(p -> p.inside(min, max))
                .collect(Collectors.toSet());
    }

    public long size() {
        long[] p1a = min.asArray();
        long[] p2a = max.asArray();
        long r = 1;
        for (int i = 0; i < Math.min(p1a.length, p2a.length); i++) {
            r *= p2a[i] - p1a[i] + 1;
        }
        return r;
    }

    public Set<T> corners() {
        if (min.equals(max)) return Set.of(min);

        Set<T> corners = new LinkedHashSet<>();
        long[] p1a = min.asArray();
        long[] p2a = max.asArray();

        cornersRecursive(min, Math.min(p1a.length, p2a.length), corners);
        return corners;
    }

    private void cornersRecursive(T position, int n, Set<T> positions) {
        if (n == 0) {
            positions.add(position);
            return;
        }

        cornersRecursive(position, n - 1, positions);
        cornersRecursive(position.copy().computeN(n - 1, l -> max.getN(n - 1)), n - 1, positions);
    }

    public Area<T> overlap(Area<T> other) {
        if (this.within(other)) return this;
        if (other.within(this)) return other;

        T rp1 = min.copy();
        T rp2 = min.copy();

        T oMin = other.min();
        T oMax = other.max();

        int dims = Math.max(min.getDimensions(), this.max.getDimensions());
        for (int n = 0; n < dims; n++) {
            rp1.setN(n, Math.max(min.getN(n), oMin.getN(n)));
            rp2.setN(n, Math.min(this.max.getN(n), oMax.getN(n)));

            if (rp1.getN(n) > rp2.getN(n)) return null;
        }

        return new Area<>(rp1, rp2);
    }

    public boolean hasOverlap(Area<T> other) {
        int dims = Math.max(min.getDimensions(), this.max.getDimensions());
        for (int n = 0; n < dims; n++) {
            if (min.getN(n) > other.max.getN(n)) return false;
            if (max.getN(n) < other.min.getN(n)) return false;
        }
        return true;
    }

    public Set<Area<T>> without(Area<T> other) {
        if (within(other)) return Set.of();
        if (!hasOverlap(other)) return Set.of(this);

        int dims = Math.max(min.getDimensions(), max.getDimensions());

        MapList<Integer, Long> coords = new HashMapList<>();
        for (int n = 0; n < dims; n++) {
            long c1 = min.getN(n);
            long c2 = other.min.getN(n);
            long c3 = other.max.getN(n) + 1;
            long c4 = max.getN(n);

            coords.add(n, c1);
            if (c1 < c2 && c2 <= c4) coords.add(n, c2);
            if (c1 < c3 && c3 <= c4) coords.add(n, c3);
            coords.add(n, c4 + 1);
        }

        Set<Area<T>> areas = new HashSet<>();
        subCubes(min, max, coords, dims, areas);
        areas.removeIf(a -> a.within(other));

        return areas;
    }

    private void subCubes(T p1, T p2, MapList<Integer, Long> coords, int dimension, Set<Area<T>> areas) {
        if (dimension == 0) {
            areas.add(new Area<>(p1, p2));
            return;
        }

        List<Long> list = coords.get(dimension - 1);
        int last = list.size() - 1;
        for (int i = 0; i < last; i++) {
            T p3 = p1.copy();
            T p4 = p2.copy();
            p3.setN(dimension - 1, list.get(i));
            p4.setN(dimension - 1, list.get(i + 1) - 1);
            subCubes(p3, p4, coords, dimension - 1, areas);
        }
    }

    public Area<T> expanded(T with) {
        return new Area<>(min().sub(with), max().add(with));
    }

    @Override
    public String toString() {
        return "Area{" +
                "p1=" + min +
                ", p2=" + max +
                '}';
    }
}
