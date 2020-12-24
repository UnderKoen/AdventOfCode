package nl.underkoen.adventofcode.general.position;

public class PositionND extends AbstractPosition<PositionND> {

    public PositionND(int dimensions) {
        super(dimensions);
    }

    public PositionND(long... coords) {
        super(coords);
    }

    public PositionND(AbstractPosition<?> position) {
        super(position);
    }

    public PositionND(AbstractPosition<?> position, int dimensions) {
        super(position, dimensions);
    }

    @Override
    protected PositionND convert() {
        return this;
    }

    @Override
    public PositionND copy() {
        return new PositionND(this);
    }
}
