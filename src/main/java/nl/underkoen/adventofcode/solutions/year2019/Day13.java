package nl.underkoen.adventofcode.solutions.year2019;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.general.IntHolder;
import nl.underkoen.adventofcode.general.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static nl.underkoen.adventofcode.solutions.year2019.opcode.OpcodeRunner.parse;
import static nl.underkoen.adventofcode.solutions.year2019.opcode.OpcodeRunner.process;

/**
 * Created by Under_Koen on 12/12/2019.
 */
public class Day13 extends Solution {
    @Getter private final int day = 13;
    @Getter private final int year = 2019;

    public static Position[] getPositionsInGame(Map<Position, Integer> game, int tile) {
        return game.entrySet().stream()
                .filter(e -> e.getValue() == tile)
                .map(Map.Entry::getKey)
                .toArray(Position[]::new);
    }

    @Override
    public long[] getCorrectOutput() {
        return new long[]{320, 15156};
    }

    @Override
    protected void run(List<String> input) {
        long[] program = parse(input);
        program[0] = 2;

        Map<Position, Integer> game = new HashMap<>();
        Position current = new Position();
        IntHolder i = new IntHolder(0);

        process(program, () -> {
            if (a == 0) a = getPositionsInGame(game, 2).length;

            return Long.compare(getPositionsInGame(game, 4)[0].getX(), getPositionsInGame(game, 3)[0].getX());
        }, l -> {
            switch (i.addValue(1) % 3) {
                case 0:
                    current.setX(l);
                    break;
                case 1:
                    current.setY(l);
                    break;
                case 2:
                    if (current.getX() == -1) b = l;
                    else game.put(current.copy(), (int) l);
                    break;
            }
        });
    }
}
