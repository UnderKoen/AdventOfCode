package nl.underkoen.adventofcode.solutions.year2022;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.map.collection.HashMapCollection;
import nl.underkoen.adventofcode.general.map.collection.HashMapStack;
import nl.underkoen.adventofcode.general.map.collection.MapCollection;
import nl.underkoen.adventofcode.general.map.collection.MapStack;
import nl.underkoen.adventofcode.general.stream.ELongStream;
import nl.underkoen.adventofcode.general.stream.EStream;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Day05 extends Solution {
    @Getter private final int day = 5;
    @Getter private final int year = 2022;

    @Override
    public String[] getCorrectOutputText() {
        return new String[]{"VCTFTJQCG", "GCFGLDNJZ"};
    }

    @Override
    protected void run(Input input) {
        List<Input> subInputs = input.asSubInputs().toList();

        MapStack<Long, Character> stacks = subInputs.get(0).transpose().stream()
                .skip(1)
                .takeNth(4)
                .map(String::trim)
                .map(s -> s.substring(0, s.length() - 1))
                .map(s -> EStream.of(s).reverse().toList())
                .indexed()
                .collect(HashMapStack::new, (s, b) -> s.addAll(1L + b.getKey(), b.getValue()), MapCollection::addAll);

        MapStack<Long, Character> stacks2 = stacks.deepCopy();

        subInputs.get(1)
                .asLineNumbers()
                .map(Stream::toList)
                .forEach(longs ->
                        ELongStream.range(0, longs.get(0))
                                .map(l -> longs.get(1))
                                .map(stacks::pop)
                                .forEach(c -> stacks.add(longs.get(2), c))
                );

        textA = stacks.values().stream()
                .map(Stack::pop)
                .map(Objects::toString)
                .collect(Collectors.joining());

        subInputs.get(1)
                .asLineNumbers()
                .map(Stream::toList)
                .forEach(longs ->
                        ELongStream.range(0, longs.get(0))
                                .map(l -> longs.get(1))
                                .map(stacks2::pop)
                                .reverse()
                                .forEach(c -> stacks2.add(longs.get(2), c))
                );

        textB = stacks2.values().stream()
                .map(Stack::pop)
                .map(Objects::toString)
                .collect(Collectors.joining());
    }
}