package nl.underkoen.adventofcode.solutions.year2021;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.position.Position;
import nl.underkoen.adventofcode.general.position.area.Area;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.ArrayList;
import java.util.List;

public class Day17 extends Solution {
    @Getter private final int day = 17;
    @Getter private final int year = 2021;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{30628, 4433};
    }

    @Override
    protected void run(Input input) {
        List<Long> longs = input.asAllNumbers()
                .flatMap(s -> s)
                .toList();

        Area<Position> area = new Area<>(new Position(longs.get(0), longs.get(2)), new Position(longs.get(1), longs.get(3)));

        long maxX = area.max().getX() * 2;
        long minY = area.min().getY();
        long maxY = -minY;

        for (long x = 0; x < maxX; x++) {
            for (long y = minY; y < maxY; y++) {
                List<Position> traject = traject(x, y, area);
                if (traject == null) continue;
                a = Math.max(a, traject.stream().mapToLong(Position::getY).max().orElse(0));
                b++;
            }
        }
    }

    private static List<Position> traject(long vx, long vy, Area<Position> area) {
        List<Position> traject = new ArrayList<>();

        Position position = new Position();
        while (!(position.inside(area) || vy < 0 && vy < area.min().getY()) || vx > area.max().getX()) {
            position.add(vx, vy);
            traject.add(position.copy());

            if (vx > 0) vx--;
            else if (vx < 0) vx++;

            vy--;
        }

        if (!position.inside(area)) return null;

        return traject;
    }
}