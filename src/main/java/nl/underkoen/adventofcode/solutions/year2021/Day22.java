package nl.underkoen.adventofcode.solutions.year2021;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.position.Position3D;
import nl.underkoen.adventofcode.general.position.multi.Area;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day22 extends Solution {
    @Getter private final int day = 22;
    @Getter private final int year = 2021;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{};
    }

    @Override
    protected void run(Input input) {
        List<Thing> areas = input.asRegexGroupList("(.{2,3}) x=(\\-?\\d+)\\.\\.(\\-?\\d+),y=(\\-?\\d+)\\.\\.(\\-?\\d+),z=(\\-?\\d+)\\.\\.(\\-?\\d+)")
                .map(Stream::toList)
                .map(Thing::new)
                .toList();

        Area<Position3D> test = new Area<>(new Position3D(-50, -50, -50), new Position3D(50, 50, 50));

        Set<Area<Position3D>> on = new HashSet<>();

        for (Thing area : areas) {
            on = on.stream().flatMap(a -> a.without(area).stream()).collect(Collectors.toSet());
            if (area.on) on.add(area);
        }

        a = on.stream().filter(area -> area.within(test)).mapToLong(Area::size).sum();
        b = on.stream().mapToLong(Area::size).sum();
    }

    public static class Thing extends Area<Position3D> {
        public final boolean on;

        public Thing(List<String> l) {
            super(
                    new Position3D(
                            Long.parseLong(l.get(1)),
                            Long.parseLong(l.get(3)),
                            Long.parseLong(l.get(5))
                    ),
                    new Position3D(
                            Long.parseLong(l.get(2)),
                            Long.parseLong(l.get(4)),
                            Long.parseLong(l.get(6))
                    )
            );
            on = l.get(0).equals("on");
        }

        @Override
        public String toString() {
            return (on ? "On " : "Off ") + super.toString();
        }
    }
}