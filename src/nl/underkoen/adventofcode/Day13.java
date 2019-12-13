package nl.underkoen.adventofcode;

import nl.underkoen.adventofcode.general.Holder;
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
        Map<Position, Integer> game = new HashMap<>();
        Position current = new Position();
        Holder<Integer> i = new Holder<>(0);
        process(program, () -> 0L, l -> {
            switch (i.getValue()) {
                case 0:
                    i.setValue(1);
                    current.setX(l);
                    break;
                case 1:
                    i.setValue(2);
                    current.setY(l);
                    break;
                case 2:
                    i.setValue(0);
                    game.put(current.copy(), (int) l);
                    break;
                default:
                    break;
            }
        });

        a = game.values().stream()
                .filter(j -> j == 2)
                .count();

        program[0] = 2;

        game.clear();
        current.set(0, 0);
        i.setValue(0);
        Holder<Integer> score = new Holder<>(0);
        process(program, () -> {
            Position paddle = game.entrySet().stream()
                    .filter(e -> e.getValue() == 3)
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElseThrow();

            Position ball = game.entrySet().stream()
                    .filter(e -> e.getValue() == 4)
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElseThrow();

            return Long.compare(ball.getX(), paddle.getX());
        }, l -> {
            switch (i.getValue()) {
                case 0:
                    i.setValue(1);
                    current.setX(l);
                    break;
                case 1:
                    i.setValue(2);
                    current.setY(l);
                    break;
                case 2:
                    i.setValue(0);
                    if (current.getX() == -1) {
                        score.setValue((int) l);
                    } else game.put(current.copy(), (int) l);
                    break;
                default:
                    break;
            }
        });

        b = score.getValue();
    }
}
