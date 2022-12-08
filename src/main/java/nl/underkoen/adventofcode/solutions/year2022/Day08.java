package nl.underkoen.adventofcode.solutions.year2022;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.position.Position;
import nl.underkoen.adventofcode.general.position.multi.Area;
import nl.underkoen.adventofcode.general.position.multi.Line;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day08 extends Solution {
    @Getter private final int day = 8;
    @Getter private final int year = 2022;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{1708, 504000};
    }

    @Override
    protected void run(Input input) {
        Map<Position, Character> map = input.asCharMap();
        Area<Position> area = new Area<>(map.keySet());

        Set<Position> visible = new HashSet<>();

        map.forEach((p, c) -> {
            Position p1 = p.copy().setN(0, 0);
            Position p2 = p.copy().setN(0, area.max().getX());
            Position p3 = p.copy().setN(1, 0);
            Position p4 = p.copy().setN(1, area.max().getY());

            long s = 1L;
            for (Position p5 : List.of(p1, p2, p3, p4)) {
                boolean b = new Line<>(p, p5).stream()
                        .filter(pos -> !pos.equals(p))
                        .map(map::get)
                        .map(c::compareTo)
                        .allMatch(l -> l > 0);
                
                if (b) visible.add(p);

                long g = new Line<>(p, p5).stream()
                        .filter(pos -> !pos.equals(p))
                        .map(map::get)
                        .map(c::compareTo)
                        .takeWhile(l -> l > 0)
                        .count();

                if (g != (new Line<>(p, p5).size() - 1)) g++;

                s *= g;
            }

            b = Math.max(b, s);
        });

        a = visible.size();
    }
}