package nl.underkoen.adventofcode.general.position;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Setter
public class PositionND {
    private long[] coords;

    public PositionND(int dimensions) {
        coords = new long[dimensions];
    }

    public long getN(int n) {
        return coords[n];
    }

    public void setN(int n, long val) {
        coords[n] = val;
    }

    public void addN(int n, long val) {
        coords[n] += val;
    }

    public PositionND add(long[] coords) {
        for (int i = 0; i < coords.length && i < this.coords.length; i++) {
            this.coords[i] += coords[i];
        }
        return this;
    }

    public PositionND add(PositionND positionND) {
        return add(positionND.coords);
    }

    public PositionND copyAdd(PositionND positionND) {
        return copy().add(positionND);
    }

    public void subN(int n, long val) {
        coords[n] -= val;
    }

    public PositionND sub(long[] coords) {
        for (int i = 0; i < coords.length && i < this.coords.length; i++) {
            this.coords[i] -= coords[i];
        }
        return this;
    }

    public PositionND sub(PositionND positionND) {
        return sub(positionND.coords);
    }

    public PositionND copySub(PositionND positionND) {
        return copy().add(positionND);
    }

    public void mulN(int n, long val) {
        coords[n] *= val;
    }

    public PositionND mul(long[] coords) {
        for (int i = 0; i < coords.length && i < this.coords.length; i++) {
            this.coords[i] *= coords[i];
        }
        return this;
    }

    public PositionND copyMul(PositionND positionND) {
        return mul(positionND.coords);
    }

    public PositionND mul(PositionND positionND) {
        return copy().mul(positionND);
    }

    public void divN(int n, long val) {
        coords[n] /= val;
    }

    public PositionND div(long[] coords) {
        for (int i = 0; i < coords.length && i < this.coords.length; i++) {
            this.coords[i] /= coords[i];
        }
        return this;
    }

    public PositionND div(PositionND positionND) {
        return div(positionND.coords);
    }

    public PositionND copyDiv(PositionND positionND) {
        return copy().div(positionND);
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
        return new PositionND(asArray());
    }

    public long[] asArray() {
        return Arrays.copyOf(coords, getDimensions());
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
}
