package nl.underkoen.adventofcode.solutions.year2021;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.*;

public class Day23 extends Solution {
    @Getter private final int day = 23;
    @Getter private final int year = 2021;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{16157, 43481};
    }

    @Override
    protected void run(Input input) {
        List<Character> col1 = new ArrayList<>();
        List<Character> col2 = new ArrayList<>();
        List<Character> col3 = new ArrayList<>();
        List<Character> col4 = new ArrayList<>();

        int i = 0;
        for (Character c : input.asCharacters()
                .flatMap(s -> s)
                .filter(Character::isUpperCase)
                .toList()) {
            (switch (i++ % 4) {
                case 0 -> col1;
                case 1 -> col2;
                case 2 -> col3;
                default -> col4;
            }).add(c);
        }

        a = test(new State(col1, col2, col3, col4, new Character[11])).orElseThrow();

        size = 4;

        col1.add(1, 'D');
        col1.add(1, 'D');

        col2.add(1, 'C');
        col2.add(1, 'B');

        col3.add(1, 'B');
        col3.add(1, 'A');

        col4.add(1, 'A');
        col4.add(1, 'C');

        b = test(new State(col1, col2, col3, col4, new Character[11])).orElseThrow();
    }

    public static int size = 2;

    public static Map<Character, Long> cost = Map.of(
            'A', 1L,
            'B', 10L,
            'C', 100L,
            'D', 1000L
    );

    public static List<Integer> skip = List.of(2, 4, 6, 8);

    public static Map<State, OptionalLong> cache = new HashMap<>();

    public static long t = 0;

    public static OptionalLong test(State state) {
        if (cache.containsKey(state)) return cache.get(state);

        List<Long> options = new ArrayList<>();

        if (!state.isHallwayEmpty()) {
            for (int i = 0; i < state.hallway.length; i++) {
                Character c = state.hallway[i];
                if (c == null) continue;
                if (!state.canEnter(c)) continue;
                int colI = State.cols.get(c);
                int loc = skip.get(colI);

                int steps = 0;
                boolean reach = true;
                for (int j = i; j != loc; j = loc > j ? j + 1 : j - 1) {
                    if (steps++ == 0) continue;
                    if (state.hallway[j] == null) continue;
                    reach = false;
                    break;
                }

                if (!reach) continue;
                State nState = state.copy();
                nState.hallway[i] = null;
                long e = cost.get(c);

                long enterCost = (size - nState.col(c).size()) * e;
                nState.col(c).add(c);

                OptionalLong test = test(nState);
                if (test.isPresent()) options.add(test.getAsLong() + steps * e + enterCost);
            }
        }

        for (int i = 0; i < 4; i++) {
            if (state.canRemove(i)) {
                State nState = state.copy();

                int start = skip.get(i);
                long startCost = (size - nState.col(i).size());

                Character c = nState.pop(i);
                long e = cost.get(c);
                startCost *= e;


                int steps = 0;
                for (int j = start; j >= 0; j--) {
                    steps++;
                    if (skip.contains(j)) continue;
                    if (nState.hallway[j] != null) break;
                    State nnState = nState.copy();
                    nnState.hallway[j] = c;

                    OptionalLong test = test(nnState);
                    if (test.isPresent()) options.add(test.getAsLong() + startCost + steps * e);
                }

                steps = 0;
                for (int j = start; j < 11; j++) {
                    steps++;
                    if (skip.contains(j)) continue;
                    if (nState.hallway[j] != null) break;
                    State nnState = nState.copy();
                    nnState.hallway[j] = c;

                    OptionalLong test = test(nnState);
                    if (test.isPresent()) options.add(test.getAsLong() + startCost + steps * e);
                }
            }
        }

        OptionalLong v;
        if (state.isColDone(0) && state.isColDone(1) && state.isColDone(2) && state.isColDone(3))
            v = OptionalLong.of(0);
        else v = options.stream().mapToLong(l -> l).min();

        cache.put(state, v);

        return v;
    }

    @EqualsAndHashCode
    @RequiredArgsConstructor
    @ToString
    public static class State {
        public static BiMap<Character, Integer> cols = HashBiMap.create(Map.of(
                'A', 0,
                'B', 1,
                'C', 2,
                'D', 3
        ));

        private final List<Character> col1;
        private final List<Character> col2;
        private final List<Character> col3;
        private final List<Character> col4;
        private final Character[] hallway;

        public boolean isHallwayEmpty() {
            for (Character character : hallway) if (character != null) return false;
            return true;
        }

        public boolean isColDone(int i) {
            List<Character> col = col(i);
            if (col.size() != size) return false;

            char c = cols.inverse().get(i);

            return col.stream().allMatch(c2 -> c2 == c);
        }

        public boolean canRemove(int i) {
            List<Character> col = col(i);
            if (col.size() == 0) return false;

            Character c = cols.inverse().get(i);

            return !col.stream().allMatch(c2 -> c2 == c);

        }

        public boolean canEnter(char c) {
            List<Character> col = col(c);
            return col.stream().allMatch(c2 -> c2 == c);
        }

        public Character pop(int i) {
            return col(i).remove(0);
        }

        public void push(int i, Character c) {
            col(i).add(0, c);
        }

        public List<Character> col(char c) {
            return col(cols.get(c));
        }

        public List<Character> col(int i) {
            return switch (i) {
                case 0 -> col1;
                case 1 -> col2;
                case 2 -> col3;
                default -> col4;
            };
        }

        public State copy() {
            return new State(
                    new ArrayList<>(col1),
                    new ArrayList<>(col2),
                    new ArrayList<>(col3),
                    new ArrayList<>(col4),
                    Arrays.copyOf(hallway, 11)
            );
        }
    }
}