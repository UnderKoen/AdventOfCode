package nl.underkoen.adventofcode.general;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.function.LongFunction;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Position4D extends Position3D {
    private long w = 0;

    public Position4D(long x, long y, long z, long w) {
        super(x, y, z);
        this.w = w;
    }

    public Position4D(Position4D position) {
        super(position);
        this.w = position.getW();
    }

    public Position4D(Position3D position, long w) {
        super(position);
        this.w = w;
    }

    public Position4D(Position3D position) {
        this(position, 0);
    }

    public Position4D(Position position, long z, long w) {
        super(position, z);
        this.w = w;
    }

    public Position4D(Position position) {
        this(position, 0, 0);
    }

    public Position4D addW(long w) {
        setW(getW() + w);
        return this;
    }

    public Position4D set(long x, long y, long z, long w) {
        set(x, y, z);
        setW(w);
        return this;
    }

    public Position4D set(Position4D position) {
        return set(position.getX(), position.getY(), position.getZ(), position.getW());
    }

    public Position4D compute(LongFunction<Long> computeX, LongFunction<Long> computeY, LongFunction<Long> computeZ, LongFunction<Long> computeW) {
        super.compute(computeX, computeY, computeZ);
        setW(computeW.apply(getW()));
        return this;
    }

    public Position4D add(long x, long y, long z, long w) {
        super.add(x, y, z);
        addW(w);
        return this;
    }

    public Position4D add(Position4D position) {
        super.add(position);
        return addW(position.getW());
    }

    @Override
    public Position4D copy() {
        return new Position4D(this);
    }

    public Position4D copyAdd(long x, long y, long z, long w) {
        Position4D pos = copy();
        pos.add(x, y, z, w);
        return pos;
    }

    public Position4D copyAdd(Position4D position) {
        Position4D pos = copy();
        pos.add(position);
        return pos;
    }

    public Position4D min(Position4D position) {
        return new Position4D(super.min(position), Math.min(getW(), position.getW()));
    }

    public Position4D max(Position4D position) {
        return new Position4D(super.max(position), Math.max(getW(), position.getW()));
    }

    public long distance(Position4D position) {
        return super.distance(position) + Math.abs(getW() - position.getW());
    }

    @Override
    public long distanceOrigin() {
        return super.distanceOrigin() + Math.abs(getW());
    }

    public long[] asArray() {
        return new long[]{getX(), getY(), getZ(), getW()};
    }

    public boolean inside(long xMin, long xMax, long yMin, long yMax, long zMin, long zMax, long wMin, long wMax) {
        return super.inside(xMin, xMax, yMin, yMax, zMin, zMax) && getW() >= wMin && getW() < wMax;
    }

    public boolean inside(Position4D p1, Position4D p2) {
        Position4D min = p1.min(p2);
        Position4D max = p1.max(p2);
        return inside(min.getX(), min.getY(), max.getX(), max.getY(), min.getZ(), max.getZ(), min.getW(), max.getW());
    }

    @Override
    public String toString() {
        return String.format("[%s, %s, %s, %s]", getX(), getY(), getZ(), getW());
    }

    public static Position4D convert(Position position) {
        if (position instanceof Position4D) return (Position4D) position.copy();
        if (position instanceof Position3D) return new Position4D((Position3D) position);
        return new Position4D(position);
    }

    @Override
    public Position4D min(Position position) {
        return min(convert(position));
    }

    @Override
    public Position4D max(Position position) {
        return max(convert(position));
    }
}
