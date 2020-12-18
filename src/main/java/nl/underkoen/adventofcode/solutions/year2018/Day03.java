package nl.underkoen.adventofcode.solutions.year2018;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.underkoen.adventofcode.general.position.Position;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;
import nl.underkoen.adventofcode.utils.MapUtils;
import nl.underkoen.adventofcode.utils.PositionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        Map<Position, Long> seen = new HashMap<>();

        List<Claim> claims = InputUtils.asAllNumbers(input)
                .map(s -> s.collect(Collectors.toList()))
                .map(Claim::new)
                .peek(c -> c.rect().forEach(p -> MapUtils.increaseLong(seen, p)))
                .collect(Collectors.toList());

        a = seen.values().stream()
                .filter(l -> l > 1)
                .count();

        b = claims.stream()
                .filter(c -> c.rect().stream().allMatch(p -> seen.get(p) == 1))
                .findAny()
                .orElseThrow()
                .getId();
    }

    @AllArgsConstructor
    @Getter
    public static class Claim {
        private final long id, left, top, width, height;

        public Claim(List<Long> list) {
            this(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4));
        }

        public List<Position> rect() {
            return PositionUtils.rectangle(new Position(left, top), width, height);
        }
    }
}