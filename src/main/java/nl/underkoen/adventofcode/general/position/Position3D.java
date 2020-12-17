package nl.underkoen.adventofcode.general.position;

import com.google.common.base.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.function.LongFunction;

@NoArgsConstructor
@Getter
@Setter
public class Position3D extends Position {
    private long z = 0;

    public Position3D(long x, long y, long z) {
        super(x, y);
        this.z = z;
    }

    public Position3D(Position position, long z) {
        super(position);
        this.z = z;
    }

    public Position3D(Position position) {
        this(position, 0);
    }

    public Position3D(Position3D position) {
        super(position);
        this.z = position.getZ();
    }

    public void addZ(long z) {
        setZ(getZ() + z);
    }

    public Position3D set(long x, long y, long z) {
        super.set(x, y);
        setZ(z);
        return this;
    }

    public Position3D compute(LongFunction<Long> computeX, LongFunction<Long> computeY, LongFunction<Long> computeZ) {
        super.compute(computeX, computeY);
        setZ(computeZ.apply(getZ()));
        return this;
    }

    public Position3D add(long x, long y, long z) {
        super.add(x, y);
        addZ(z);
        return this;
    }

    public Position3D copyAdd(long x, long y, long z) {
        return copy().add(x, y, z);
    }

    public long[] asArray() {
        return new long[]{getX(), getY(), getZ()};
    }

    public boolean inside(long xMin, long xMax, long yMin, long yMax, long zMin, long zMax) {
        return super.inside(xMin, xMax, yMin, yMax) && getZ() >= zMin && getZ() < zMax;
    }

    public boolean inside(Position3D p1, Position3D p2) {
        Position3D min = p1.min(p2);
        Position3D max = p1.max(p2);
        return inside(min.getX(), min.getY(), max.getX(), max.getY(), min.getZ(), max.getZ());
    }

    @Override
    public String toString() {
        return String.format("[%s, %s, %s]", getX(), getY(), getZ());
    }

    @Override
    public Position set(Position position) {
        if (position instanceof Position3D) setZ(((Position3D) position).getZ());
        return super.set(position);
    }

    @Override
    public Position3D copy() {
        return new Position3D(this);
    }

    @Override
    public Position3D add(Position position) {
        if (position instanceof Position3D) return add(position);
        else return (Position3D) super.add(position);
    }

    @Override
    public Position3D add(long x, long y) {
        return (Position3D) super.add(x, y);
    }

    @Override
    public Position3D copyAdd(long x, long y) {
        return (Position3D) super.copyAdd(x, y);
    }

    @Override
    public Position3D copyAdd(Position position) {
        return (Position3D) super.copyAdd(position);
    }

    @Override
    public long distance(Position position) {
        long distance = super.distance(position);
        if (position instanceof Position3D) distance += Math.abs(getZ() - ((Position3D) position).getZ());
        else distance += Math.abs(getZ());
        return distance;
    }

    @Override
    public long distanceOrigin() {
        return super.distanceOrigin() + Math.abs(getZ());
    }

    @Override
    public Position3D min(Position position) {
        return new Position3D(super.min(position), Math.min(getZ(), position instanceof Position3D ? ((Position3D) position).getZ() : 0));
    }

    @Override
    public Position3D max(Position position) {
        return new Position3D(super.max(position), Math.max(getZ(), position instanceof Position3D ? ((Position3D) position).getZ() : 0));
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && getZ() == (o instanceof Position3D ? ((Position3D) o).getZ() : 0);
    }

    @Override
    public int hashCode() {
        if (getZ() == 0) return hashCode();
        return Objects.hashCode(super.hashCode(), getZ());
    }
}
