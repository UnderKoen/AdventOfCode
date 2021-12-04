package nl.underkoen.adventofcode.solutions.year2020;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.stream.EStream;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.Collection;
import java.util.stream.Collectors;

public class Day06 extends Solution {
    @Getter private final int day = 6;
    @Getter private final int year = 2020;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{6775, 3356};
    }

    @Override
    protected void run(Input input) {
        input.asSubInputs()
                .map(Input::asCharacters)
                .map(s -> s.map(cs -> cs.collect(Collectors.toSet())))
                .map(EStream::toMutable)
                .forEach(chars -> {
                    a += chars.stream()
                            .flatMap(Collection::stream)
                            .distinct()
                            .count();

                    for (int i = 1; i < chars.size(); i++) {
                        chars.get(0).retainAll(chars.get(i));
                    }

                    b += chars.get(0).size();
                });
    }
}