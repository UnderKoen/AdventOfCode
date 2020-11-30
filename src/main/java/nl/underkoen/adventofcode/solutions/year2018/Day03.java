package nl.underkoen.adventofcode.solutions.year2018;

import lombok.Getter;
import nl.underkoen.adventofcode.general.Position;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 extends Solution {
    @Getter private final int day = 3;
    @Getter private final int year = 2018;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{117948, 567};
    }

    @Override
    protected void run(List<String> input) {
        Pattern pattern = Pattern.compile("#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)");

        Map<Position, Integer> seen = new HashMap<>();
        Set<Long> ids = new HashSet<>();
        Map<Position, Long> seenIds = new HashMap<>();
        for (String s : input) {
            Matcher m = pattern.matcher(s);
            if (!m.find()) throw new IllegalArgumentException();
            long id = Long.parseLong(m.group(1));
            long left = Long.parseLong(m.group(2));
            long top = Long.parseLong(m.group(3));
            long width = Long.parseLong(m.group(4));
            long height = Long.parseLong(m.group(5));

            ids.add(id);
            for (long x = left; x < left + width; x++) {
                for (long y = top; y < top + height; y++) {
                    Position position = new Position(x, y);
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
                }
            }
        }

        b = ids.stream().findAny().orElseThrow();
    }
}