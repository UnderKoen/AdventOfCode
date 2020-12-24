package nl.underkoen.adventofcode.general.position;

public class Position3D extends AbstractPosition<Position3D> implements Dimensions.ThreeDimensions {
    public Position3D() {
        super(3);
    }

    public Position3D(long x, long y, long z) {
        super(x, y, z);
    }

    public Position3D(AbstractPosition<?> position) {
        super(position, 3);
    }

    @Override
    protected Position3D convert() {
        return this;
    }

    @Override
    public Position3D copy() {
        return new Position3D(this);
    }

    @Override
    public Position3D setDimensions(int dimensions) {
        return super.setDimensions(Math.max(3, dimensions));
    }
}
