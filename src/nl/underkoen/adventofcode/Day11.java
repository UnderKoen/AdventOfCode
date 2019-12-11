package nl.underkoen.adventofcode;

import nl.underkoen.adventofcode.opcode.OutputOpcode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static nl.underkoen.adventofcode.opcode.OpcodeRunner.parse;
import static nl.underkoen.adventofcode.opcode.OpcodeRunner.process;

/**
 * Created by Under_Koen on 10/12/2019.
 */
public class Day11 extends AdventOfCode {
    public static boolean step;
    public static int dir;
    public static int count;
    public static int[] pos;
    public static Map<String, Long> color;

    private static void run(long[] program, long defaultValue) {
        color = new HashMap<>();
        step = true;
        pos = new int[]{0, 0};
        dir = 0;

        process(program, () -> color.getOrDefault(String.format("%s, %s", pos[0], pos[1]), defaultValue), (l) -> {
            if (step) {
                String posS = String.format("%s, %s", pos[0], pos[1]);
                Long o = color.getOrDefault(posS, defaultValue);
                if (o != l) {
                    if (color.put(posS, l) == null) {
                        count++;
                    }
                }
            } else {
                switch ((int) l) {
                    case 0:
                        dir -= 1;
                        if (dir < 0) dir = 3;
                        break;
                    case 1:
                        dir += 1;
                        if (dir > 3) dir = 0;
                        break;
                    default:
                        System.out.println("ERROR");
                        break;
                }

                switch (dir) {
                    case 0:
                        pos[1]++;
                        break;
                    case 1:
                        pos[0]++;
                        break;
                    case 2:
                        pos[1]--;
                        break;
                    case 3:
                        pos[0]--;
                        break;
                    default:
                        System.out.println("ERROR 2");
                        break;
                }
            }
            step = !step;
        });
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
        OutputOpcode.defaultPrint = false;

        long[] program = parse(input);

        run(program, 0);
        a = color.size();

        run(program, 1);
        for (int i = 0; i >= -5; i--) {
            for (int j = 1; j < 40; j++) {
                long k = color.getOrDefault(String.format("%s, %s", j, i), 1L);
                if (k == 0) System.out.print("  ");
                else System.out.print("##");
            }
            System.out.println();
        }
    }
}
