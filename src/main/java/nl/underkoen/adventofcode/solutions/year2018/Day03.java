package nl.underkoen.adventofcode.solutions.year2018;

import lombok.Getter;
import nl.underkoen.adventofcode.general.Position;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;

import java.util.*;
import java.util.stream.Collectors;

public class Day03 extends Solution {
    @Getter private final int day = 3;
    @Getter private final int year = 2018;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{117948, 567};
    }

    @Override
    protected void run(List<String> input) {
        Map<Position, Integer> seen = new HashMap<>();
        Set<Long> ids = new HashSet<>();
        Map<Position, Long> seenIds = new HashMap<>();

        InputUtils.asAllNumbers(input)
                .map(s -> s.collect(Collectors.toList()))
                .forEach(nums -> {
                    long id = nums.get(0);
                    long left = nums.get(1);
                    long top = nums.get(2);
                    long width = nums.get(3);
                    long height = nums.get(4);

                    ids.add(id);
                    Position.rectangle(new Position(left, top), width, height).forEach(position -> {
                        if (seen.containsKey(position)) {
                            int amount = seen.get(position);
                            seen.replace(position, amount + 1);
                            ids.remove(id);
                            ids.remove(seenIds.get(position));
                            if (amount == 0) a++;
                        } else {
                            seen.put(position, 0);
                            seenIds.put(position, id);
                        }
                    });
                });

        b = ids.stream().findAny().orElseThrow();
    }
}