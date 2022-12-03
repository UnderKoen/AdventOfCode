package nl.underkoen.adventofcode.general.position.multi;

import lombok.Getter;
import nl.underkoen.adventofcode.general.position.AbstractPosition;
import nl.underkoen.adventofcode.general.stream.EStream;
import nl.underkoen.adventofcode.utils.NumberUtils;

import java.util.AbstractList;

public class Line<T extends AbstractPosition<T>> extends AbstractList<T> {
    @Getter private final T p1;
    @Getter private final T p2;
    private final T offset;

    public Line(T p1, T p2) {
        this.p1 = p1.copy();
        this.p2 = p2.copy();
        this.offset = calcOffset();
    }

    public boolean isPoint() {
        return p1.equals(p2);
    }

    public boolean isHorizontal() {
        if (isPoint()) return false;
        return 1 == p1.copySub(p2)
                .stream()
                .filter(l -> l != 0)
                .count();
    }

    public boolean isDiagonal() {
        if (isPoint()) return false;
        return !isHorizontal();
    }

    public long length() {
        return p1.distance(p2);
    }

    private T calcOffset() {
        T offset = p2.copySub(p1);
        if (!isPoint()) {
            if (isHorizontal()) {
                for (int n = 0; n < offset.getDimensions(); n++) {
                    long i = offset.getN(n);
                    if (i == 0) continue;
                    offset.setN(n, i > 0 ? 1 : -1);
                    break;
                }
            } else {
                long gcd = Math.abs(NumberUtils.gcd(offset.asArray()));
                offset.divAll(gcd);
            }
        }
        return offset;
    }

    public T getOffset() {
        return offset.copy();
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size()) throw new IndexOutOfBoundsException();
        if (index == 0) return p1;
        if (index == size() - 1) return p2;
        return getOffset().mulAll(index).add(p1);
    }

    private Integer size;

    @Override
    public int size() {
        if (size != null) return size;
        if (p1 == p2) return size = 1;
        return size = 1 + (int) p2.copySub(p1)
                .div(getOffset().computeAll(l -> l == 0 ? 1 : l))
                .stream()
                .filter(l -> l != 0)
                .map(Math::abs)
                .findAny()
                .orElse(0);
    }

    @Override
    public String toString() {
        return String.format("Line(%s -> %s)", p1, p2);
    }

    @Override
    public EStream<T> stream() {
        return EStream.of(super.stream());
    }
}
