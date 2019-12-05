package nl.underkoen.adventofcode;

import java.util.List;

/**
 * Created by Under_Koen on 03/12/2019.
 */
public abstract class AdventOfCode {
    int a, b;

    abstract int getDay();

    abstract void run(List<String> input);

    public int[] getCorrectOutput() {
        return new int[0];
    }

    private void output() {
        System.out.printf("Result day%sa:\n%s\n\nResult day%sb:\n%s\n", getDay(), a, getDay(), b);
    }

    private void test() {
        int[] correct = getCorrectOutput();
        if (correct.length == 0) return;
        if (a != correct[0] || (correct.length >= 2 && b != correct[1])) throw new IllegalArgumentException("Output is incorrect");
    }

    private void execute() {
        run(Utils.getInput(getDay()));
        output();
        test();
    }

    public static void main(String[] args) throws Exception {
        String clsN = System.getProperty("sun.java.command");
        clsN = clsN.split(" ")[0];
        if (clsN.contains("/")) clsN = clsN.split("/")[1];

        Class<?> cls = Class.forName(clsN, false, Thread.currentThread().getContextClassLoader());
        if (AdventOfCode.class.isAssignableFrom(cls)) {
            Class<? extends AdventOfCode> day = (Class<? extends AdventOfCode>) cls;
            AdventOfCode adventOfCode = day.getConstructor().newInstance();
            adventOfCode.execute();
        }
    }
}
