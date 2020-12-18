package nl.underkoen.adventofcode.general.position;

/**
 * Created by Under_Koen on 11/12/2019.
 */
public class Position extends CastedPosition<Position> implements Dimensions.TwoDimensions {
    public Position() {
        super(2);
    }

    public Position(long x, long y) {
        super(x, y);
    }

    public Position(PositionND position) {
        super(position, 2);
    }

    @Override
    public Position copy() {
        return new Position(this);
    }
}
