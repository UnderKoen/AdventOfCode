package nl.underkoen.adventofcode.general.position;

import com.google.common.base.Objects;
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

    public void addW(long w) {
        setW(getW() + w);
    }

    public Position4D set(long x, long y, long z, long w) {
        set(x, y, z);
        setW(w);
        return this;
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

    public Position4D copyAdd(long x, long y, long z, long w) {
        return copy().add(x, y, z, w);
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

    @Override
    public Position set(Position position) {
        if (position instanceof Position4D) setW(((Position4D) position).getW());
        return super.set(position);
    }

    @Override
    public Position4D copy() {
        return new Position4D(this);
    }

    @Override
    public Position4D add(Position position) {
        if (position instanceof Position4D) return add(position);
        else return (Position4D) super.add(position);
    }

    @Override
    public Position4D add(long x, long y) {
        return (Position4D) super.add(x, y);
    }

    @Override
    public Position4D copyAdd(long x, long y) {
        return (Position4D) super.copyAdd(x, y);
    }

    @Override
    public Position4D add(long x, long y, long z) {
        return (Position4D) super.add(x, y, z);
    }

    @Override
    public Position4D copyAdd(long x, long y, long z) {
        return (Position4D) super.copyAdd(x, y, z);
    }

    @Override
    public Position4D copyAdd(Position position) {
        return (Position4D) super.copyAdd(position);
    }

    @Override
    public long distance(Position position) {
        long distance = super.distance(position);
        if (position instanceof Position4D) distance += Math.abs(getW() - ((Position4D) position).getW());
        else distance += Math.abs(getW());
        return distance;
    }

    @Override
    public long distanceOrigin() {
        return super.distanceOrigin() + Math.abs(getW());
    }

    @Override
    public Position4D min(Position position) {
        return new Position4D(super.min(position), Math.min(getW(), position instanceof Position4D ? ((Position4D) position).getW() : 0));
    }

    @Override
    public Position4D max(Position position) {
        return new Position4D(super.max(position), Math.max(getW(), position instanceof Position4D ? ((Position4D) position).getW() : 0));
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && getW() == (o instanceof Position4D ? ((Position4D) o).getW() : 0);
    }

    @Override
    public int hashCode() {
        if (getW() == 0) return hashCode();
        return Objects.hashCode(super.hashCode(), getW());
    }
}
