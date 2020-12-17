package nl.underkoen.adventofcode.general;

import com.google.common.base.Objects;

@SuppressWarnings("unchecked")
abstract class PositionExtension<T extends PositionExtension<T>> extends Position {
    protected Class<T> getPositionClass() {
        return (Class<T>) getClass();
    }

    protected abstract T add(T position);

    protected abstract T convert(Position position);

    protected abstract long getExtension();

    protected abstract T setExtension(long extension);

    protected boolean instanceOf(Object obj) {
        return getPositionClass().isAssignableFrom(obj.getClass());
    }

    @Override
    public T add(Position position) {
        if (instanceOf(position)) return add(position);
        else return (T) super.add(position);
    }

    @Override
    public T add(long x, long y) {
        return (T) super.add(x, y);
    }

    @Override
    public T copyAdd(long x, long y) {
        return (T) super.copyAdd(x, y);
    }

    @Override
    public T copyAdd(Position position) {
        return (T) super.copyAdd(position);
    }

    @Override
    public long distance(Position position) {
        long distance = super.distance(position);
        if (instanceOf(position)) distance += Math.abs(getExtension() - ((T) position).getExtension());
        else distance += Math.abs(getExtension());
        return distance;
    }

    @Override
    public long distanceOrigin() {
        return super.distanceOrigin() + Math.abs(getExtension());
    }

    @Override
    public T min(Position position) {
        return convert(super.min(position))
                .setExtension(Math.min(getExtension(), instanceOf(position) ? ((T) position).getExtension() : 0));
    }

    @Override
    public T max(Position position) {
        return convert(super.min(position))
                .setExtension(Math.max(getExtension(), instanceOf(position) ? ((T) position).getExtension() : 0));
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && getExtension() == (instanceOf(o) ? ((T) o).getExtension() : 0);
    }

    @Override
    public int hashCode() {
        if (getExtension() == 0) return hashCode();
        return Objects.hashCode(super.hashCode(), getExtension());
    }

    @Override
    public String toString() {
        String s = super.toString();
        s = s.substring(0, s.length() - 1);
        return String.format("%s, %s]", s, getExtension());
    }
}
