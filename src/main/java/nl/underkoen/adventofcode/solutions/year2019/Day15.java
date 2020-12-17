package nl.underkoen.adventofcode.solutions.year2019;

import lombok.Getter;
import nl.underkoen.adventofcode.general.Holder;
import nl.underkoen.adventofcode.general.position.Position;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.solutions.year2019.opcode.StopOpcode;

import java.util.*;
import java.util.stream.Collectors;

import static nl.underkoen.adventofcode.solutions.year2019.opcode.OpcodeRunner.parse;
import static nl.underkoen.adventofcode.solutions.year2019.opcode.OpcodeRunner.process;

/**
 * Created by Under_Koen on 15/12/2019.
 */
public class Day15 extends Solution {
    @Getter private final int day = 15;
    @Getter private final int year = 2019;

    public static void checkDistance(Map<Position, Integer> dis, Set<Position> all, Position current, int index) {
        all.remove(current);
        dis.put(current, index);

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if ((x == 0) == (y == 0)) continue;
                Position pos = current.copyAdd(x, y);
                if (all.contains(pos)) checkDistance(dis, all, pos, index + 1);
            }
        }
    }

    @Override
    public long[] getCorrectOutput() {
        return new long[]{250, 332};
    }

    @Override
    protected void run(List<String> input) {
        long[] program = parse(input);

        Position position = new Position();
        Map<Position, Integer> dir = new HashMap<>();
        Map<Position, Integer> map = new HashMap<>();
        Holder<Long> dup = new Holder<>(0L);

        process(program, () -> dir.getOrDefault(position, 1), l -> {
            int d = dir.getOrDefault(position, 1);
            Position nPos = position.copyAdd(((d * 2) - 7) % 3 % 2, (3 - (d * 2)) % 3 % 2);
            dir.put(position.copy(), d % 4 + 1);

            map.put(nPos, (int) l);
            if (l != 0) position.set(nPos);
            if (l == 2) {
                long size = map.size();
                if (dup.setValue(size) == size) throw new StopOpcode();
            }
        });

        Map<Position, Integer> dis = new HashMap<>();
        Set<Position> all = map.keySet().stream()
                .filter(p -> map.get(p) != 0)
                .collect(Collectors.toSet());

        checkDistance(dis, new HashSet<>(all), new Position(), 0);
        a = dis.get(position);

        dis.clear();
        checkDistance(dis, new HashSet<>(all), position, 0);
        b = Collections.max(dis.values());
    }
}
