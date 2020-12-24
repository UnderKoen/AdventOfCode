package nl.underkoen.adventofcode.solutions.year2020;

import lombok.Getter;
import nl.underkoen.adventofcode.general.position.Position;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.ConwayUtils;
import nl.underkoen.adventofcode.utils.MapUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day24 extends Solution {
    @Getter private final int day = 24;
    @Getter private final int year = 2020;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{479, 4135};
    }

    @Override
    protected void run(List<String> input) {
        Map<Position, Long> tiles = new HashMap<>();
        for (String s : input) {
            Position position = new Position();
            while (!s.isBlank()) {
                if (s.startsWith("sw")) {
                    position.addY(-1);
                    s = s.substring(2);
                } else if (s.startsWith("ne")) {
                    position.addY(1);
                    s = s.substring(2);
                } else {
                    switch (s.charAt(0)) {
                        case 'n' -> position.addY(1);
                        case 'e' -> position.addX(1);
                        case 's' -> position.subY(1);
                        case 'w' -> position.subX(1);
                    }
                    s = s.substring(1);
                }
            }
            MapUtils.increaseLong(tiles, position);
        }

        Set<Position> positions = tiles.entrySet().stream()
                .filter(e -> e.getValue() % 2 == 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        a = positions.size();
        b = ConwayUtils.calculate(positions, 100, (n, s, p) -> n == 2 || n == 1 && s, p -> {
            Set<Position> n = p.getNeighbours();
            n.remove(p.copyAdd(1, 1));
            n.remove(p.copyAdd(-1, -1));
            return n;
        }).size();
    }
}