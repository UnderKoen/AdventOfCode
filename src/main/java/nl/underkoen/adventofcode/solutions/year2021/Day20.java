package nl.underkoen.adventofcode.solutions.year2021;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.position.Position;
import nl.underkoen.adventofcode.general.position.area.Area;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.*;

public class Day20 extends Solution {
    @Getter private final int day = 20;
    @Getter private final int year = 2021;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{5065, 14790};
    }

    @Override
    protected void run(Input input) {
        List<Input> inputs = input.asSubInputs().toList();

        List<Character> enhancement = inputs.get(0).asCharacters().flatMap(s -> s).toList();

        Set<Position> positions = inputs.get(1).mapChar((c, p) -> c == '#' ? p : null)
                .filter(Objects::nonNull)
                .toSet();


        Area<Position> area = new Area<>(positions);
        Image start = new Image(positions, enhancement);
        for (int i = 0; i < 2; i++) {
            start = new Image(start, enhancement);
            area = area.expanded(new Position(1, 1));
        }

        for (Position position : area.between()) {
            if (start.getPixel(position)) a++;
        }

        for (int i = 0; i < 48; i++) {
            start = new Image(start, enhancement);
            area = area.expanded(new Position(1, 1));
        }

        for (Position position : area.between()) {
            if (start.getPixel(position)) b++;
        }
    }

    public static class Image {
        private final Image prev;
        private final Map<Position, Boolean> image = new HashMap<>();
        private final List<Character> enhancement;

        public Image(Image prev, List<Character> enhancement) {
            this.prev = prev;
            this.enhancement = enhancement;
        }

        public Image(Set<Position> positions, List<Character> enhancement) {
            this.enhancement = enhancement;
            this.prev = null;
            positions.forEach(p -> image.put(p, true));
        }

        public boolean getPixel(Position position) {
            if (image.containsKey(position)) return image.get(position);
            if (prev == null) return false;
            int i = 0;
            for (long y = position.getY() - 1; y <= position.getY() + 1; y++) {
                for (long x = position.getX() - 1; x <= position.getX() + 1; x++) {
                    Position p = new Position(x, y);
                    i <<= 1;
                    if (prev.getPixel(p)) i++;
                }
            }
            Character c = enhancement.get(i);
            image.put(position, c == '#');
            return c == '#';
        }
    }
}