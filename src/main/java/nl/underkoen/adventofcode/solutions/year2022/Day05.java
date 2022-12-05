package nl.underkoen.adventofcode.solutions.year2022;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.stream.EStream;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.*;
import java.util.stream.Stream;

public class Day05 extends Solution {
    @Getter private final int day = 5;
    @Getter private final int year = 2022;

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
        List<Input> subInputs = input.asSubInputs().toList();

        Map<Long, Stack<Character>> stacks = new HashMap<>();
        for (String s : subInputs.get(0).stream().reverse().skip(1).toList()) {
            long i = 0;
            long row = 0;
            for (char c : s.toCharArray()) {
                if (i++ % 4 == 1) {
                    row++;
                    if (c == ' ') continue;
                    if (!stacks.containsKey(row)) stacks.put(row, new Stack<>());
                    stacks.get(row).push(c);
                }
            }
        }


        Map<Long, Stack<Character>> stacks2 = new HashMap<>();
        stacks.forEach((l, cs) -> stacks2.put(l, new Stack<>()));
        stacks.forEach((l, cs) -> stacks2.get(l).addAll(cs));

        for (List<Long> longs : subInputs.get(1).asLineNumbers().map(Stream::toList).toList()) {
            for (long i = 0; i < longs.get(0); i++) {
                char c = stacks.get(longs.get(1)).pop();
                stacks.get(longs.get(2)).push(c);
            }
        }

        textA = "";
        for (Stack<Character> value : stacks.values()) {
            textA += value.pop();
        }

        for (List<Long> longs : subInputs.get(1).asLineNumbers().map(Stream::toList).toList()) {
            Stack<Character> s = new Stack<>();
            for (long i = 0; i < longs.get(0); i++) {
                char c = stacks2.get(longs.get(1)).pop();
                s.push(c);
            }

            for (Character character : EStream.of(s).reverse().toList()) {
                stacks2.get(longs.get(2)).push(character);
            }
        }

        textB = "";
        for (Stack<Character> value : stacks2.values()) {
            textB += value.pop();
        }
    }
}