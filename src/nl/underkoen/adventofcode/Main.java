package nl.underkoen.adventofcode;

/**
 * Created by Under_Koen on 03/02/2020.
 */
public class Main {
    public static final String PACKAGE = "nl.underkoen.adventofcode";

    public static void main(String[] args) throws Exception {
        for (int i = 1; i <= 25; i++) {
            Class<?> cls = Class.forName(String.format("%s.Day%02d", PACKAGE, i));
            if (AdventOfCode.class.isAssignableFrom(cls)) {
                Class<? extends AdventOfCode> day = (Class<? extends AdventOfCode>) cls;
                AdventOfCode adventOfCode = day.getConstructor().newInstance();
                try {
                    long start = System.currentTimeMillis();
                    adventOfCode.run(Utils.getInput(i));
                    long end = System.currentTimeMillis();

                    adventOfCode.test();
                    System.out.printf("Day %s succeeded and toke: %s ms\n", i, end - start);
                } catch (Exception e) {
                    System.err.printf("Day %s failed\n", i);
                }
            }
        }
    }
}
