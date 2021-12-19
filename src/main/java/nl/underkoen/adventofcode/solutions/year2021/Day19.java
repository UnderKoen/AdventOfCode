package nl.underkoen.adventofcode.solutions.year2021;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.map.collection.HashMapList;
import nl.underkoen.adventofcode.general.map.collection.MapList;
import nl.underkoen.adventofcode.general.map.counter.LongMapCounter;
import nl.underkoen.adventofcode.general.position.Position3D;
import nl.underkoen.adventofcode.general.tuple.BiHolder;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.*;
import java.util.stream.Collectors;

public class Day19 extends Solution {
    @Getter private final int day = 19;
    @Getter private final int year = 2021;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{398, 10965};
    }

    @Override
    protected void run(Input input) {
        MapList<Integer, Position3D> map = new HashMapList<>();

        int scanner = 0;
        for (Input strings : input.asSubInputs().toList()) {
            for (int i = 1; i < strings.size(); i++) {
                String s = strings.get(i);
                String[] splits = s.split(",");
                map.add(scanner, new Position3D(Long.parseLong(splits[0]), Long.parseLong(splits[1]), Long.parseLong(splits[2])));
            }
            scanner++;
        }

        Map<Integer, BiHolder<Position3D, Orientation>> results = new HashMap<>();
        results.put(0, new BiHolder<>(new Position3D(), new Orientation(1, 2, 3)));

        Set<Position3D> beacons = new HashSet<>(map.get(0));
        while (results.size() != map.size()) {
            for (int i : map.keySet()) {
                if (results.containsKey(i)) continue;
                BiHolder<Position3D, Orientation> relative = relative(beacons, map.get(i));
                if (relative == null) continue;
                results.put(i, relative);

                List<Position3D> b = map.get(i).stream().map(relative.getValue()::convert).map(relative.getKey()::copyAdd).toList();
                beacons.addAll(b);
            }
        }

        a = beacons.size();

        for (BiHolder<Position3D, Orientation> p1 : results.values()) {
            for (BiHolder<Position3D, Orientation> p2 : results.values()) {
                b = Math.max(b, p1.getKey().distance(p2.getKey()));
            }
        }
    }

    public static BiHolder<Position3D, Orientation> relative(Collection<Position3D> list1, Collection<Position3D> list2) {
        for (Orientation orientation : Orientation.orientations) {
            LongMapCounter<Position3D> counter3 = new LongMapCounter<>();
            Set<Position3D> test = list2.stream().map(orientation::convert).collect(Collectors.toSet());
            for (Position3D p1 : list1) {
                for (Position3D p2 : test) {
                    if (p1 == p2) continue;
                    Position3D sub = p1.copySub(p2);
                    counter3.increase(sub);
                }
            }
            if (counter3.max() >= 12) return new BiHolder<>(counter3.maxKey(), orientation);
        }
        return null;
    }

    @ToString
    @EqualsAndHashCode
    public static class Orientation {
        static Set<Orientation> orientations = new HashSet<>();

        static {
            for (int x = -3; x <= 3; x++) {
                if (x == 0) continue;
                for (int y = -3; y <= 3; y++) {
                    if (y == 0) continue;
                    for (int z = -3; z <= 3; z++) {
                        if (z == 0) continue;
                        orientations.add(new Orientation(x, y, z));
                    }
                }
            }
        }

        final int p1;
        final int p2;
        final int p3;

        public Orientation(int p1, int p2, int p3) {
            this.p1 = p1;
            this.p2 = p2;
            this.p3 = p3;
        }

        public Position3D convert(Position3D position) {
            Position3D r = new Position3D();

            long n1 = position.getN(Math.abs(p1) - 1);
            r.addN(0, p1 > 0 ? n1 : -n1);

            long n2 = position.getN(Math.abs(p2) - 1);
            r.addN(1, p2 > 0 ? n2 : -n2);

            long n3 = position.getN(Math.abs(p3) - 1);
            r.addN(2, p3 > 0 ? n3 : -n3);

            return r;
        }
    }
}