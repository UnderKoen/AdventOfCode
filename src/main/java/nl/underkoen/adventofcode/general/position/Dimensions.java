package nl.underkoen.adventofcode.general.position;

public class Dimensions {
    public interface TwoDimensions {
        long[] getCoords();

        default long getX() {
            return getCoords()[0];
        }

        default long getY() {
            return getCoords()[1];
        }

        default void setX(long x) {
            getCoords()[0] = x;
        }

        default void setY(long y) {
            getCoords()[1] = y;
        }

        default void addX(long x) {
            setX(getX() + x);
        }

        default void addY(long y) {
            setY(getY() + y);
        }

        default double atan2(Position position) {
            return Math.atan2(getY() - position.getY(), getX() - position.getX());
        }
    }

    public interface ThreeDimensions extends TwoDimensions {
        default long getZ() {
            return getCoords()[2];
        }

        default void setZ(long z) {
            getCoords()[2] = z;
        }

        default void addZ(long z) {
            setZ(getZ() + z);
        }
    }

    public interface FourDimensions extends ThreeDimensions {
        default long getW() {
            return getCoords()[3];
        }

        default void setW(long w) {
            getCoords()[3] = w;
        }

        default void addW(long w) {
            setW(getW() + w);
        }
    }
}
