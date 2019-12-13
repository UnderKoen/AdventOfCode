package nl.underkoen.adventofcode;

import nl.underkoen.adventofcode.general.IntHolder;
import nl.underkoen.adventofcode.general.Position;
import nl.underkoen.adventofcode.opcode.OutputOpcode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static nl.underkoen.adventofcode.opcode.OpcodeRunner.parse;
import static nl.underkoen.adventofcode.opcode.OpcodeRunner.process;

/**
 * Created by Under_Koen on 12/12/2019.
 */
public class Day13 extends AdventOfCode {
    public static Position[] getPositionsInGame(Map<Position, Integer> game, int tile) {
        return game.entrySet().stream()
                .filter(e -> e.getValue() == tile)
                .map(Map.Entry::getKey)
                .toArray(Position[]::new);
    }

    @Override
    int getDay() {
        return 13;
    }

    @Override
    public long[] getCorrectOutput() {
        return new long[]{320, 15156};
    }

    @Override
    void run(List<String> input) {
        OutputOpcode.setDefaultPrint(false);

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
