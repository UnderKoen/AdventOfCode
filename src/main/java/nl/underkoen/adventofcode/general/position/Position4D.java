package nl.underkoen.adventofcode.general.position;

public class Position4D extends AbstractPosition<Position4D> implements Dimensions.FourDimensions {
    public Position4D() {
        super(3);
    }

    public Position4D(long x, long y, long z, long w) {
        super(x, y, z, w);
    }

    public Position4D(AbstractPosition<?> position) {
        super(position, 4);
    }

    @Override
    protected Position4D convert() {
        return this;
    }

    @Override
    public Position4D copy() {
        return new Position4D(this);
    }

    @Override
    public Position4D setDimensions(int dimensions) {
        return super.setDimensions(Math.max(4, dimensions));
    }
}
