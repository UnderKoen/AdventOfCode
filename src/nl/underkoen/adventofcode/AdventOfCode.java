package nl.underkoen.adventofcode;

import java.util.List;

/**
 * Created by Under_Koen on 03/12/2019.
 */
public abstract class AdventOfCode {
    int a, b;

    public static void main(String[] args) throws Exception {
        String clsN = System.getProperty("sun.java.command");
        clsN = clsN.split(" ")[0];
        if (clsN.contains("/")) clsN = clsN.split("/")[1];

        Class<?> cls = Class.forName(clsN, false, Thread.currentThread().getContextClassLoader());
        if (AdventOfCode.class.isAssignableFrom(cls)) {
            Class<? extends AdventOfCode> day = (Class<? extends AdventOfCode>) cls;
            AdventOfCode adventOfCode = day.getConstructor().newInstance();
            try {
                adventOfCode.execute();
                adventOfCode.test();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        System.exit(0);
    }

    abstract int getDay();

    abstract void run(List<String> input);

    public int[] getCorrectOutput() {
        return new int[0];
    }

    private void output() {
        System.out.printf("Result day%sa:\n%s\n\nResult day%sb:\n%s\n\n", getDay(), a, getDay(), b);
    }

    private void execute() {
        run(Utils.getInput(getDay()));
        output();
    }

    private void test() {
        int[] correct = getCorrectOutput();
        if (correct.length == 0) return;
        if (a != correct[0]) throw new IllegalArgumentException("Result A is incorrect");
        if (correct.length >= 2 && b != correct[1]) throw new IllegalArgumentException("Result B is incorrect");
    }
}
