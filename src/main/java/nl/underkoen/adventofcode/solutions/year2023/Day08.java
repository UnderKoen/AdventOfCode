package nl.underkoen.adventofcode.solutions.year2023;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.NumberUtils;

import java.util.List;
import java.util.Map;

public class Day08 extends Solution {
    @Getter private final int day = 8;
    @Getter private final int year = 2023;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{};
    }

//    @Override
//    public String[] getCorrectOutputText() {
//        return new String[]{};
//    }

    @Override
    protected void run(Input input) {
        String directions = input.get(0);

        Map<String, Direction> map = input.stream().skip(2).map(Direction::new).toMap(d -> d.self, d -> d);

        a = distance("AAA", map, directions);

//        b = map.keySet().stream().filter(s -> s.endsWith("A")).mapToLong(s -> distance(s, s.substring(0, 2) + "Z", map, directions)).peek(System.out::println).sum();

        List<Long> longs = map.keySet().stream().filter(s -> s.endsWith("A")).map(s -> distance(s, map, directions)).toList();

        b = longs.get(0);

        for (int i = 1; i < longs.size(); i++) {
            b *= longs.get(i) / NumberUtils.gcd(b, longs.get(i));
        }

    }

    public long distance(String from, Map<String, Direction> map, String directions) {

        long dis = 0;
        String current = from;
        while (!current.endsWith("Z")) {
            for (char c : directions.toCharArray()) {
                Direction direction = map.get(current);
                if (c == 'L') current = direction.left;
                else current = direction.right;
                dis++;

                if (current.endsWith("Z")) break;
            }
        }

        System.out.println(from + " -> " + current + " = " + dis);

        return dis;
    }

    class Direction {
        String self;
        String left;
        String right;

        public Direction(String line) {
            this.self = line.split(" = ")[0];
            this.left = line.split(" = ")[1].split(", ")[0].substring(1);
            this.right = line.split(" = ")[1].split(", ")[1].replace(")", "");
        }

        @Override
        public String toString() {
            return "Direction{" +
                    "self='" + self + '\'' +
                    ", left='" + left + '\'' +
                    ", right='" + right + '\'' +
                    '}';
        }
    }


}