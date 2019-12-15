package nl.underkoen.adventofcode.general;

/**
 * Created by Under_Koen on 11/12/2019.
 */
public class Position {
    private long x;
    private long y;

    public Position() {
        this(0, 0);
    }

    public Position(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public long getX() {
        return x;
    }

    public long setX(long x) {
        long old = this.x;
        this.x = x;
        return old;
    }

    public long addX(long x) {
        return setX(getX() + x);
    }

    public long getY() {
        return y;
    }

    public long setY(long y) {
        long old = this.y;
        this.y = y;
        return old;
    }

    public long addY(long y) {
        return setY(getY() + y);
    }

    public void set(long x, long y) {
        setX(x);
        setY(y);
    }

    public void set(Position position) {
        set(position.getX(), position.getY());
    }

    public void add(long x, long y) {
        addX(x);
        addY(y);
    }

    public void add(Position position) {
        add(position.getX(), position.getY());
    }

    public Position copy() {
        return new Position(x, y);
    }

    public Position copyAdd(long x, long y) {
        Position pos = copy();
        pos.add(x, y);
        return pos;
    }

    public Position copyAdd(Position position) {
        Position pos = copy();
        pos.add(position);
        return pos;
    }

    public long distance(Position position) {
        return Math.abs(getX() - position.getX()) + Math.abs(getY() - position.getY());
    }

    public long distanceOrigin() {
        return Math.abs(getX()) + Math.abs(getY());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;

        Position position = (Position) o;

        if (getX() != position.getX()) return false;
        return getY() == position.getY();
    }

    @Override
    public int hashCode() {
        int result = (int) (getX() ^ (getX() >>> 32));
        result = 31 * result + (int) (getY() ^ (getY() >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return String.format("[%s, %s]", x, y);
    }
}
