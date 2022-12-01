package nl.underkoen.adventofcode.solutions.year2016;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.position.Position;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.HashSet;
import java.util.Set;

public class Day01 extends Solution {
    @Getter private final int day = 1;
    @Getter private final int year = 2016;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{271, 153};
    }

    @Override
    protected void run(Input input) {
        int r = 0;
        Position p = new Position();
        Set<Position> positions = new HashSet<>();

        for (String s : input.asSplit().toList()) {
            r += s.charAt(0) == 'R' ? 1 : -1;
            r += 4;
            r %= 4;

            long n = Long.parseLong(s.substring(1));
            Position old = p.copy();

            switch (r) {
                case 0 -> p.addX(n);
                case 1 -> p.addY(n);
                case 2 -> p.addX(-n);
                case 3 -> p.addY(-n);
            }

            for (Position position : old.line(p)) {
                if (position.equals(old)) continue;
                if (!positions.add(position) && b == 0) {
                    b = position.distanceOrigin();
                }
            }
        }

        a = p.distanceOrigin();
    }
}