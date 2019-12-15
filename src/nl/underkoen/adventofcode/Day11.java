package nl.underkoen.adventofcode;

import nl.underkoen.adventofcode.general.BiHolder;
import nl.underkoen.adventofcode.general.Position;
import nl.underkoen.adventofcode.opcode.OutputOpcode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.LongStream;

import static nl.underkoen.adventofcode.opcode.OpcodeRunner.parse;
import static nl.underkoen.adventofcode.opcode.OpcodeRunner.process;

/**
 * Created by Under_Koen on 10/12/2019.
 */
public class Day11 extends AdventOfCode {
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
    int getDay() {
        return 11;
    }

    @Override
    public long[] getCorrectOutput() {
        return new long[]{2184};
    }

    @Override
    void run(List<String> input) {
        OutputOpcode.setDefaultPrint(false);

        long[] program = parse(input);

        a = run(program, 0).size();

        Map<Position, Long> canvas = run(program, 1);

        Position min = Position.min(canvas.keySet());
        Position max = Position.max(canvas.keySet());

        LongStream.range(min.getY(), max.getY() + 1)
                .map(y -> -y).sorted().map(y -> -y)
                .mapToObj(y -> LongStream.range(min.getX(), max.getX())
                        .mapToObj(x -> new Position(x, y))
                        .mapToLong(pos -> canvas.getOrDefault(pos, 0L))
                        .mapToObj(l -> l == 0 ? "  " : "##")
                        .reduce(String::concat)
                        .orElse("")
                )
                .forEachOrdered(System.out::println);

        //Should output AHCHZEPK
    }
}
