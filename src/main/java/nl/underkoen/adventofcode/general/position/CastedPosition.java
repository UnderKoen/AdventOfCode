package nl.underkoen.adventofcode.general.position;

import java.util.Set;
import java.util.function.LongUnaryOperator;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public abstract class CastedPosition<T extends CastedPosition<T>> extends PositionND {
    public CastedPosition(int dimensions) {
        super(dimensions);
    }

    public CastedPosition(long... coords) {
        super(coords);
    }

    public CastedPosition(PositionND positionND) {
        super(positionND);
    }

    public CastedPosition(PositionND positionND, int dimensions) {
        super(positionND, Math.max(dimensions, positionND.getDimensions()));
    }

    @Override
    public T set(long... coords) {
        return (T) super.set(coords);
    }

    @Override
    public T set(PositionND positionND) {
        return (T) super.set(positionND);
    }

    @Override
    public T copySet(PositionND positionND) {
        return (T) super.copySet(positionND);
    }

    @Override
    public T compute(LongUnaryOperator... coords) {
        return (T) super.compute(coords);
    }

    @Override
    public T add(long... coords) {
        return (T) super.add(coords);
    }

    @Override
    public T add(PositionND positionND) {
        return (T) super.add(positionND);
    }

    @Override
    public T copyAdd(long... coords) {
        return (T) super.copyAdd(coords);
    }

    @Override
    public T copyAdd(PositionND positionND) {
        return (T) super.copyAdd(positionND);
    }

    @Override
    public T sub(long... coords) {
        return (T) super.sub(coords);
    }

    @Override
    public T sub(PositionND positionND) {
        return (T) super.sub(positionND);
    }

    @Override
    public T copySub(long... coords) {
        return (T) super.copySub(coords);
    }

    @Override
    public T copySub(PositionND positionND) {
        return (T) super.copySub(positionND);
    }

    @Override
    public T mul(long... coords) {
        return (T) super.mul(coords);
    }

    @Override
    public T copyMul(long... coords) {
        return (T) super.copyMul(coords);
    }

    @Override
    public T copyMul(PositionND positionND) {
        return (T) super.copyMul(positionND);
    }

    @Override
    public T mul(PositionND positionND) {
        return (T) super.mul(positionND);
    }

    @Override
    public T div(long... coords) {
        return (T) super.div(coords);
    }

    @Override
    public T div(PositionND positionND) {
        return (T) super.div(positionND);
    }

    @Override
    public T copyDiv(long... coords) {
        return (T) super.copyDiv(coords);
    }

    @Override
    public T copyDiv(PositionND positionND) {
        return (T) super.copyDiv(positionND);
    }

    @Override
    public T min(PositionND position) {
        return (T) super.min(position);
    }

    @Override
    public T max(PositionND position) {
        return (T) super.max(position);
    }

    @Override
    public T computeN(int n, LongUnaryOperator val) {
        return (T) super.computeN(n, val);
    }

    @Override
    public T addN(int n, long val) {
        return (T) super.addN(n, val);
    }

    @Override
    public T subN(int n, long val) {
        return (T) super.subN(n, val);
    }

    @Override
    public T mulN(int n, long val) {
        return (T) super.mulN(n, val);
    }

    @Override
    public T divN(int n, long val) {
        return (T) super.divN(n, val);
    }

    @Override
    public T setN(int n, long val) {
        return (T) super.setN(n, val);
    }

    public Set<T> getNeighboursCasted() {
        return super.getNeighbours().stream()
                .map(p -> (T) p)
                .collect(Collectors.toSet());
    }

    @Override
    public abstract T copy();
}
