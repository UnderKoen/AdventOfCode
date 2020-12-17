package nl.underkoen.adventofcode.general.position;

import lombok.*;

import java.util.Collection;
import java.util.List;
import java.util.function.LongFunction;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * Created by Under_Koen on 11/12/2019.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@With
public class Position {
    public static Position min(Collection<? extends Position> positions) {
        return positions.stream()
                .map(p -> (Position) p)
                .reduce(Position::min)
                .orElseThrow();
    }

    public static Position max(Collection<? extends Position> positions) {
        return positions.stream()
                .map(p -> (Position) p)
                .reduce(Position::max)
                .orElseThrow();
    }

    public static List<Position> between(Position p1, Position p2) {
        Position min = p1.min(p2);
        Position max = p1.max(p2);

        return LongStream.range(min.getX(), max.getX() + 1)
                .boxed()
                .flatMap(x -> LongStream
                        .range(min.getY(), max.getY() + 1)
                        .boxed()
                        .map(y -> new Position(x, y)))
                .collect(Collectors.toList());
    }

    public static List<Position> rectangle(Position origin, long width, long height) {
        return between(origin, origin.copyAdd(width - 1, height - 1));
    }

    private long x = 0;
    private long y = 0;

    public Position(Position position) {
        this(position.getX(), position.getY());
    }

    public void addX(long x) {
        setX(getX() + x);
    }

    public void addY(long y) {
        setY(getY() + y);
    }

    public Position set(long x, long y) {
        setX(x);
        setY(y);
        return this;
    }

    public Position set(Position position) {
        return set(position.getX(), position.getY());
    }

    public Position compute(LongFunction<Long> computeX, LongFunction<Long> computeY) {
        return set(computeX.apply(getX()), computeY.apply(getY()));
    }

    public Position add(long x, long y) {
        addX(x);
        addY(y);
        return this;
    }

    public Position add(Position position) {
        return add(position.getX(), position.getY());
    }

    public Position copy() {
        return new Position(this);
    }

    public Position copyAdd(long x, long y) {
        return copy().add(x, y);
    }

    public Position copyAdd(Position position) {
        return copy().add(position);
    }

    public Position min(Position position) {
        return new Position(Math.min(getX(), position.getX()), Math.min(getY(), position.getY()));
    }

    public Position max(Position position) {
        return new Position(Math.max(getX(), position.getX()), Math.max(getY(), position.getY()));
    }

    public long distance(Position position) {
        return Math.abs(getX() - position.getX()) + Math.abs(getY() - position.getY());
    }

    public long distanceOrigin() {
        return Math.abs(getX()) + Math.abs(getY());
    }

    public double atan2(Position position) {
        return Math.atan2(getY() - position.getY(), getX() - position.getX());
    }

    public long[] asArray() {
        return new long[]{getX(), getY()};
    }

    public boolean inside(long xMin, long xMax, long yMin, long yMax) {
        return getX() >= xMin && getX() < xMax && getY() >= yMin && getY() < yMax;
    }

    public boolean inside(Position p1, Position p2) {
        Position min = p1.min(p2);
        Position max = p1.max(p2);
        return inside(min.getX(), min.getY(), max.getX(), max.getY());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position position = (Position) o;
        return getX() == position.getX() &&
                getY() == position.getY();
    }

    @Override
    public int hashCode() {
        int result = (int) (getX() ^ (getX() >>> 32));
        result = 31 * result + (int) (getY() ^ (getY() >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return String.format("[%s, %s]", getX(), getY());
    }
}
