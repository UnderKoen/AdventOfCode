package nl.underkoen.adventofcode.solutions.year2019;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.position.Position;
import nl.underkoen.adventofcode.general.tuple.BiHolder;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.PositionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static nl.underkoen.adventofcode.solutions.year2019.opcode.OpcodeRunner.parse;
import static nl.underkoen.adventofcode.solutions.year2019.opcode.OpcodeRunner.process;

/**
 * Created by Under_Koen on 10/12/2019.
 */
public class Day11 extends Solution {
    @Getter private final int day = 11;
    @Getter private final int year = 2019;

    private static Map<Position, Long> run(long[] program, long defaultValue) {
        Map<Position, Long> canvas = new HashMap<>();
        Position pos = new Position();
        BiHolder<Boolean, Long> info = new BiHolder<>(true, 0L);

        process(program, () -> canvas.getOrDefault(pos, defaultValue), l -> {
            if (info.setKey(!info.getKey())) canvas.put(pos.copy(), l);
            else {
                info.setValue((info.getValue() + l * 2 + 3) % 4);
                if (info.getValue() % 2 == 0) pos.addY(1 - info.getValue());
                else pos.addX(1 - (info.getValue() - info.getValue() % 2));
            }
        });

        return canvas;
    }

    @Override
    public String[] getCorrectOutputText() {
        return new String[]{"2184", "AHCHZEPK"};
    }

    @Override
    protected void run(Input input) {
        long[] program = parse(input);

        a = run(program, 0).size();

        Map<Position, Long> canvas = run(program, 1);

        List<Position> positions = canvas.keySet().stream()
                .filter(p -> canvas.get(p) > 0)
                .peek(p -> p.mulY(-1))
                .toList();

        textB = PositionUtils.asLettersString(positions);
    }
}
