package nl.underkoen.adventofcode.general.position;

/**
 * Created by Under_Koen on 11/12/2019.
 */
public class Position extends AbstractPosition<Position> implements Dimensions.TwoDimensions {
    public Position() {
        super(2);
    }

    public Position(long x, long y) {
        super(x, y);
    }

    public Position(AbstractPosition<?> position) {
        super(position, 2);
    }

    @Override
    protected Position convert() {
        return this;
    }

    @Override
    public Position copy() {
        return new Position(this);
    }

    @Override
    public Position setDimensions(int dimensions) {
        return super.setDimensions(Math.max(2, dimensions));
    }
}
