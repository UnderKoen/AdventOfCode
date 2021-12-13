package nl.underkoen.adventofcode.utils;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.position.AbstractPosition;
import nl.underkoen.adventofcode.general.position.Position;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PositionUtils {
    public static <T extends AbstractPosition<T>> T min(Collection<T> positions) {
        return positions.stream()
                .reduce(AbstractPosition::min)
                .orElseThrow();
    }

    public static <T extends AbstractPosition<T>> T max(Collection<T> positions) {
        return positions.stream()
                .reduce(AbstractPosition::max)
                .orElseThrow();
    }

    public static <T extends AbstractPosition<T>> List<T> between(T p1, T p2) {
        T min = p1.min(p2);
        T max = p1.max(p2);

        List<T> r = new ArrayList<>();
        addDimension(min, max, min.copy(), 0, r);
        return r;
    }

    private static <T extends AbstractPosition<T>> void addDimension(T min, T max, T current, int dimension, List<T> positions) {
        if (dimension >= min.getDimensions()) {
            positions.add(current);
            return;
        }

        for (long n = min.getN(dimension); n <= max.getN(dimension); n++) {
            T pos = current.copy();
            pos.setN(dimension, n);
            addDimension(min, max, pos, dimension + 1, positions);
        }
    }

    public static <T extends AbstractPosition<T>> List<T> rectangle(T origin, long width, long height) {
        return between(origin, origin.copyAdd(width - 1, height - 1));
    }

    public static <T extends AbstractPosition<T>> Map<T, Long> countNeighbours(Iterable<T> positions) {
        return countNeighbours(positions, p -> true);
    }

    public static <T extends AbstractPosition<T>> Map<T, Long> countNeighbours(Iterable<T> positions, Predicate<T> filter) {
        Map<T, Long> amount = new HashMap<>();
        for (T position : positions) {
            position.getNeighbours().stream()
                    .filter(filter)
                    .forEach(p -> MapUtils.increaseLong(amount, p));
        }
        return amount;
    }

    public static void print(Collection<Position> all) {
        print(all, (p, b) -> b ? "#" : ".");
    }

    public static void print(Collection<Position> all, BiFunction<Position, Boolean, String> convert) {
        System.out.println(toString(all, convert));
    }

    public static String toString(Collection<Position> all) {
        return toString(all, (p, b) -> b ? "#" : ".");
    }

    public static String toString(Collection<Position> all, BiFunction<Position, Boolean, String> convert) {
        Position min = min(all);
        Position max = max(all);

        StringBuilder string = new StringBuilder();
        for (long y = min.getY(); y <= max.getY(); y++) {
            for (long x = min.getX(); x <= max.getX(); x++) {
                Position pos = new Position(x, y);
                string.append(convert.apply(pos, all.contains(pos)));
            }
            string.append("\n");
        }

        return string.toString();
    }

    public static List<Letter> asLetters(Collection<Position> positions) {
        long maxX = PositionUtils.max(positions).getX();

        Set<Position> current = new HashSet<>();
        long start = 0;

        List<Letter> letters = new ArrayList<>();

        for (long x = 0; x <= maxX; x++) {
            long finalX = x, finalStart = start;
            Set<Position> line = positions.stream()
                    .filter(p -> p.getX() == finalX)
                    .map(Position::copy)
                    .peek(p -> p.subX(finalStart))
                    .collect(Collectors.toSet());

            current.addAll(line);

            if (line.size() != 0 && x != maxX) continue;

            start = x + 1;

            if (current.size() == 0) continue;

            Letter letter = new Letter(current);
            letters.add(letter);
            current = new HashSet<>();
        }

        return letters;
    }

    public static String asLettersString(Collection<Position> positions) {
        StringBuilder string = new StringBuilder();
        List<Letter> letters = asLetters(positions);
        for (Letter letter : letters) {
            Character character = letter.asChar();
            if (character == null) {
                System.err.println("Unknown Letter");
                System.out.println(StringUtils.combineMultilines(letters));
                return null;
            }
            string.append(character);
        }
        return string.toString();
    }

    public static class Letter {
        public static final Letter A = new Letter("""
                .##.
                #..#
                #..#
                ####
                #..#
                #..#""");

        public static final Letter B = new Letter("""
                ###.
                #..#
                ###.
                #..#
                #..#
                ###.""");

        public static final Letter C = new Letter("""
                .##.
                #..#
                #...
                #...
                #..#
                .##.""");

        public static final Letter D = new Letter("""
                ....
                ....
                ....
                ....
                ....
                ....""");

        public static final Letter E = new Letter("""
                ####
                #...
                ###.
                #...
                #...
                ####""");

        public static final Letter F = new Letter("""
                ####
                #...
                ###.
                #...
                #...
                #...""");

        public static final Letter G = new Letter("""
                .##.
                #..#
                #...
                #.##
                #..#
                .###""");

        public static final Letter H = new Letter("""
                #..#
                #..#
                ####
                #..#
                #..#
                #..#""");

        public static final Letter I = new Letter("""
                ....
                ....
                ....
                ....
                ....
                ....""");

        public static final Letter J = new Letter("""
                ..##
                ...#
                ...#
                ...#
                #..#
                .##.""");

        public static final Letter K = new Letter("""
                #..#
                #.#.
                ##..
                #.#.
                #.#.
                #..#""");

        public static final Letter L = new Letter("""
                #...
                #...
                #...
                #...
                #...
                ####""");

        public static final Letter M = new Letter("""
                ....
                ....
                ....
                ....
                ....
                ....""");

        public static final Letter N = new Letter("""
                ....
                ....
                ....
                ....
                ....
                ....""");

        public static final Letter O = new Letter("""
                .##.
                #..#
                #..#
                #..#
                #..#
                .##.""");

        public static final Letter P = new Letter("""
                ###.
                #..#
                #..#
                ###.
                #...
                #...""");

        public static final Letter Q = new Letter("""
                ....
                ....
                ....
                ....
                ....
                ....""");

        public static final Letter R = new Letter("""
                ###.
                #..#
                #..#
                ###.
                #.#.
                #..#""");

        public static final Letter S = new Letter("""
                .###
                #...
                #...
                .##.
                ...#
                ###.""");

        public static final Letter T = new Letter("""
                ....
                ....
                ....
                ....
                ....
                ....""");

        public static final Letter U = new Letter("""
                #..#
                #..#
                #..#
                #..#
                #..#
                .##.""");

        public static final Letter V = new Letter("""
                ....
                ....
                ....
                ....
                ....
                ....""");

        public static final Letter W = new Letter("""
                ....
                ....
                ....
                ....
                ....
                ....""");

        public static final Letter X = new Letter("""
                ....
                ....
                ....
                ....
                ....
                ....""");

        public static final Letter Y = new Letter("""
                #...#
                #...#
                .#.#.
                ..#..
                ..#..
                ..#..""");

        public static final Letter Z = new Letter("""
                ####
                ...#
                ..#.
                .#..
                #...
                ####""");


        public static final BiMap<Character, Letter> letters = HashBiMap.create(24);

        static {
            letters.put('A', A);
            letters.put('B', B);
            letters.put('C', C);
//            letters.put('D', D);
            letters.put('E', E);
            letters.put('F', F);
            letters.put('G', G);
            letters.put('H', H);
//            letters.put('I', I);
            letters.put('J', J);
            letters.put('K', K);
            letters.put('L', L);
//            letters.put('M', M);
//            letters.put('N', N);
            letters.put('O', O);
            letters.put('P', P);
//            letters.put('Q', Q);
            letters.put('R', R);
            letters.put('S', S);
//            letters.put('T', T);
            letters.put('U', U);
//            letters.put('V', V);
//            letters.put('W', W);
//            letters.put('X', X);
            letters.put('Y', Y);
            letters.put('Z', Z);
        }

        private final Set<Position> positions;

        public Letter(String string) {
            positions = Input.of(Arrays.stream(string.split("\n")).toList())
                    .mapChar((c, p) -> c == '#' ? p : null)
                    .filter(Objects::nonNull)
                    .toSet();
        }

        private Letter(Set<Position> positions) {
            this.positions = positions;
        }

        public Character asChar() {
            return letters.inverse().get(this);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Letter letter)) return false;

            return positions.equals(letter.positions);
        }

        @Override
        public int hashCode() {
            return positions.hashCode();
        }

        @Override
        public String toString() {
            return StringUtils.combineMultilines(
                    List.of(
                            PositionUtils.toString(positions, (p, b) -> b ? "##" : ".."),
                            "..\n..\n..\n..\n..\n..\n"
                    )
            );
        }
    }
}
