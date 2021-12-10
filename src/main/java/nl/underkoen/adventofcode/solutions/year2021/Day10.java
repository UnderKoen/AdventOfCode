package nl.underkoen.adventofcode.solutions.year2021;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.stream.EStream;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.CharacterUtils;

import java.util.*;

public class Day10 extends Solution {
    @Getter private final int day = 10;
    @Getter private final int year = 2021;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{339411, 2289754624L};
    }

    @Override
    protected void run(Input input) {
        List<Long> longs = new ArrayList<>();
        for (String s : input) {
            Stack<Character> stack = new Stack<>();
            Optional<Character> corrupt = EStream.of(s)
                    .filter(CharacterUtils::isClosingBracket, stack::push)
                    .dropWhile(c -> CharacterUtils.isPair(c, stack.pop()))
                    .findFirst();

            if (corrupt.isPresent()) {
                switch (corrupt.get()) {
                    case ')' -> a += 3;
                    case ']' -> a += 57;
                    case '}' -> a += 1197;
                    case '>' -> a += 25137;
                }
                continue;
            }

            if (stack.size() == 0) continue;
            long val = EStream.of(stack).reverse()
                    .mapToLong(c -> switch (c) {
                        case '(' -> 1;
                        case '[' -> 2;
                        case '{' -> 3;
                        case '<' -> 4;
                        default -> 0;
                    })
                    .reduce(0, (prev, n) -> prev * 5 + n);
            longs.add(val);
        }

        longs.sort(Comparator.naturalOrder());
        b = longs.get(longs.size() / 2);
    }
}