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
public abstract class AbstractPosition<T extends AbstractPosition<T>> implements Comparable<AbstractPosition<T>>, Dimensions {
    private long[] coords;

    public AbstractPosition(int dimensions) {
        coords = new long[dimensions];
    }

    public AbstractPosition(long... coords) {
        this.coords = coords;
    }

    public AbstractPosition(AbstractPosition<?> position) {
        this(position.asArray());
    }

    public AbstractPosition(AbstractPosition<?> position, int dimensions) {
        this(Arrays.copyOf(position.getCoords(), Math.max(position.getDimensions(), dimensions)));
    }

    protected abstract T convert();

    public long getN(int n) {
        return coords[n];
    }

    public T setN(int n, long val) {
        coords[n] = val;
        return convert();
    }

    public T set(long... coords) {
        for (int i = 0; i < coords.length && i < this.coords.length; i++) {
            setN(i, coords[i]);
        }
        return convert();
    }

    public T set(AbstractPosition<?> position) {
        return set(position.coords);
    }

    public T copySet(AbstractPosition<?> position) {
        return copy().set(position);
    }

    public T computeN(int n, LongUnaryOperator val) {
        coords[n] = val.applyAsLong(coords[n]);
        return convert();
    }

    public T compute(LongUnaryOperator... coords) {
        for (int i = 0; i < coords.length && i < this.coords.length; i++) {
            computeN(i, coords[i]);
        }
        return convert();
    }

    public T addN(int n, long val) {
        coords[n] += val;
        return convert();
    }

    public T add(long... coords) {
        for (int i = 0; i < coords.length && i < this.coords.length; i++) {
            addN(i, coords[i]);
        }
        return convert();
    }

    public T add(AbstractPosition<?> position) {
        return add(position.coords);
    }

    public T copyAdd(long... coords) {
        return copy().add(coords);
    }

    public T copyAdd(AbstractPosition<?> position) {
        return copy().add(position);
    }

    public T subN(int n, long val) {
        coords[n] -= val;
        return convert();
    }

    public T sub(long... coords) {
        for (int i = 0; i < coords.length && i < this.coords.length; i++) {
            subN(i, coords[i]);
        }
        return convert();
    }

    public T sub(AbstractPosition<?> position) {
        return sub(position.coords);
    }

    public T copySub(long... coords) {
        return copy().sub(coords);
    }

    public T copySub(AbstractPosition<?> position) {
        return copy().sub(position);
    }

    public T mulN(int n, long val) {
        coords[n] *= val;
        return convert();
    }

    public T mul(long... coords) {
        for (int i = 0; i < coords.length && i < this.coords.length; i++) {
            mulN(i, coords[i]);
        }
        return convert();
    }

    public T copyMul(long... coords) {
        return copy().mul(coords);
    }

    public T copyMul(AbstractPosition<?> position) {
        return copy().mul(position);
    }

    public T mul(AbstractPosition<?> position) {
        return copy().mul(position);
    }

    public T divN(int n, long val) {
        coords[n] /= val;
        return convert();
    }

    public T div(long... coords) {
        for (int i = 0; i < coords.length && i < this.coords.length; i++) {
            divN(i, coords[i]);
        }
        return convert();
    }

    public T div(AbstractPosition<?> position) {
        return div(position.coords);
    }

    public T copyDiv(long... coords) {
        return copy().div(coords);
    }

    public T copyDiv(AbstractPosition<?> position) {
        return copy().div(position);
    }

    public T copyWithDimension(int dimensions) {
        return copy().setDimensions(dimensions);
    }

    public int getDimensions() {
        return coords.length;
    }

    public T setDimensions(int dimensions) {
        this.coords = Arrays.copyOf(coords, dimensions);
        return convert();
    }

    public List<Long> asList() {
        return Arrays.stream(coords).boxed().collect(Collectors.toList());
    }

    public T min(AbstractPosition<?> position) {
        T r = copy();
        for (int i = 0; i < coords.length || i < position.coords.length; i++) {
            long l1 = i < coords.length ? coords[i] : 0;
            long l2 = i < position.coords.length ? position.coords[i] : 0;
            r.getCoords()[i] = Math.min(l1, l2);
        }
        return r;
    }

    public T max(AbstractPosition<?> position) {
        T r = copy();
        for (int i = 0; i < coords.length || i < position.coords.length; i++) {
            long l1 = i < coords.length ? coords[i] : 0;
            long l2 = i < position.coords.length ? position.coords[i] : 0;
            r.getCoords()[i] = Math.max(l1, l2);
        }
        return r;
    }

    public long distance(AbstractPosition<?> position) {
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

    public boolean inside(AbstractPosition<?> position1, AbstractPosition<?> position2) {
        AbstractPosition<?> min = position1.min(position2);
        AbstractPosition<?> max = position1.max(position2);
        for (int i = 0; i < coords.length; i++) {
            if (coords[i] < min.coords[i] || coords[i] > max.coords[i]) return false;
        }
        return true;
    }

    public abstract T copy();

    public long[] asArray() {
        return Arrays.copyOf(coords, getDimensions());
    }

    public Set<T> getNeighbours() {
        Set<T> positions = new HashSet<>();
        neighboursRecursion(positions, convert(), 0);
        positions.remove(convert());
        return positions;
    }

    private void neighboursRecursion(Set<T> positions, T current, int dimension) {
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

        if (!(o instanceof AbstractPosition)) return false;

        AbstractPosition<?> that = (AbstractPosition<?>) o;

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
    public int compareTo(AbstractPosition o) {
        return Arrays.compare(coords, o.coords);
    }
}
