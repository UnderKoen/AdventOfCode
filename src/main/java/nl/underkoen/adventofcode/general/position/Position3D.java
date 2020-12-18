package nl.underkoen.adventofcode.general.position;

public class Position3D extends CastedPosition<Position3D> implements Dimensions.ThreeDimensions {
    public Position3D() {
        super(3);
    }

    public Position3D(long x, long y, long z) {
        super(x, y, z);
    }

    public Position3D(PositionND position) {
        super(position, 3);
    }

    @Override
    public Position3D copy() {
        return new Position3D(this);
    }
}
