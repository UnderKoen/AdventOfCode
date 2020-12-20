package nl.underkoen.adventofcode.general.position;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.LongUnaryOperator;
import java.util.stream.Collectors;

@Getter
@Setter
public class PositionND implements Comparable<PositionND> {
    private long[] coords;

    public PositionND(int dimensions) {
        coords = new long[dimensions];
    }

    public PositionND(long... coords) {
        this.coords = coords;
    }

    public PositionND(PositionND positionND) {
        this(positionND.asArray());
    }

    public PositionND(PositionND positionND, int dimensions) {
        this(Arrays.copyOf(positionND.getCoords(), dimensions));
    }

    public long getN(int n) {
        return coords[n];
    }

    public PositionND setN(int n, long val) {
        coords[n] = val;
        return this;
    }

    public PositionND set(long... coords) {
        for (int i = 0; i < coords.length && i < this.coords.length; i++) {
            setN(i, coords[i]);
        }
        return this;
    }

    public PositionND set(PositionND positionND) {
        return set(positionND.coords);
    }

    public PositionND copySet(PositionND positionND) {
        return copy().set(positionND);
    }

    public PositionND computeN(int n, LongUnaryOperator val) {
        coords[n] = val.applyAsLong(coords[n]);
        return this;
    }

    public PositionND compute(LongUnaryOperator... coords) {
        for (int i = 0; i < coords.length && i < this.coords.length; i++) {
            computeN(i, coords[i]);
        }
        return this;
    }

    public PositionND addN(int n, long val) {
        coords[n] += val;
        return this;
    }

    public PositionND add(long... coords) {
        for (int i = 0; i < coords.length && i < this.coords.length; i++) {
            addN(i, coords[i]);
        }
        return this;
    }

    public PositionND add(PositionND positionND) {
        return add(positionND.coords);
    }

    public PositionND copyAdd(long... coords) {
        return copy().add(coords);
    }

    public PositionND copyAdd(PositionND positionND) {
        return copy().add(positionND);
    }

    public PositionND subN(int n, long val) {
        coords[n] -= val;
        return this;
    }

    public PositionND sub(long... coords) {
        for (int i = 0; i < coords.length && i < this.coords.length; i++) {
            subN(i, coords[i]);
        }
        return this;
    }

    public PositionND sub(PositionND positionND) {
        return sub(positionND.coords);
    }

    public PositionND copySub(long... coords) {
        return copy().sub(coords);
    }

    public PositionND copySub(PositionND positionND) {
        return copy().sub(positionND);
    }

    public PositionND mulN(int n, long val) {
        coords[n] *= val;
        return this;
    }

    public PositionND mul(long... coords) {
        for (int i = 0; i < coords.length && i < this.coords.length; i++) {
            mulN(i, coords[i]);
        }
        return this;
    }

    public PositionND copyMul(long... coords) {
        return copy().mul(coords);
    }

    public PositionND copyMul(PositionND positionND) {
        return copy().mul(positionND);
    }

    public PositionND mul(PositionND positionND) {
        return copy().mul(positionND);
    }

    public PositionND divN(int n, long val) {
        coords[n] /= val;
        return this;
    }

    public PositionND div(long... coords) {
        for (int i = 0; i < coords.length && i < this.coords.length; i++) {
            divN(i, coords[i]);
        }
        return this;
    }

    public PositionND div(PositionND positionND) {
        return div(positionND.coords);
    }

    public PositionND copyDiv(long... coords) {
        return copy().div(coords);
    }

    public PositionND copyDiv(PositionND positionND) {
        return copy().div(positionND);
    }

    public PositionND copyWithDimension(int dimensions) {
        return new PositionND(this, dimensions);
    }

    public int getDimensions() {
        return coords.length;
    }

    public List<Long> asList() {
        return Arrays.stream(coords).boxed().collect(Collectors.toList());
    }

    public PositionND min(PositionND position) {
        PositionND r = copy();
        for (int i = 0; i < coords.length || i < position.coords.length; i++) {
            long l1 = i < coords.length ? coords[i] : 0;
            long l2 = i < position.coords.length ? position.coords[i] : 0;
            r.coords[i] = Math.min(l1, l2);
        }
        return r;
    }

    public PositionND max(PositionND position) {
        PositionND r = copy();
        for (int i = 0; i < coords.length || i < position.coords.length; i++) {
            long l1 = i < coords.length ? coords[i] : 0;
            long l2 = i < position.coords.length ? position.coords[i] : 0;
            r.coords[i] = Math.max(l1, l2);
        }
        return r;
    }

    public long distance(PositionND position) {
        long r = 0;
        for (int i = 0; i < coords.length || i < position.coords.length; i++) {
            long l1 = i < coords.length ? coords[i] : 0;
            long l2 = i < position.coords.length ? position.coords[i] : 0;
            r += (Math.abs(l1 - l2));
        }
        return r;
    }

    public long distanceOrigin() {
        long r = 0;
        for (long coord : coords) {
            r += Math.abs(coord);
        }
        return r;
    }

    public boolean inside(PositionND position1, PositionND position2) {
        PositionND min = position1.min(position2);
        PositionND max = position1.max(position2);
        for (int i = 0; i < coords.length; i++) {
            if (coords[i] < min.coords[i] || coords[i] > max.coords[i]) return false;
        }
        return true;
    }

    public PositionND copy() {
        return new PositionND(this);
    }

    public long[] asArray() {
        return Arrays.copyOf(coords, getDimensions());
    }

    public Set<PositionND> getNeighbours() {
        Set<PositionND> positions = new HashSet<>();
        neighboursRecursion(positions, this, 0);
        positions.remove(this);
        return positions;
    }

    private void neighboursRecursion(Set<PositionND> positions, PositionND current, int dimension) {
        if (dimension >= current.getDimensions()) {
            positions.add(current);
            return;
        }

        for (int i = -1; i <= 1; i++) {
            neighboursRecursion(positions, current.copy().addN(dimension, i), dimension + 1);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof PositionND)) return false;

        PositionND that = (PositionND) o;

        return Arrays.equals(coords, that.coords);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(coords);
    }

    @Override
    public String toString() {
        return Arrays.toString(coords);
    }

    @Override
    public int compareTo(PositionND o) {
        return Arrays.compare(coords, o.coords);
    }
}
