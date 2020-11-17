package nl.underkoen.adventofcode.solutions;

import nl.underkoen.adventofcode.Utils;

import java.util.List;

/**
 * Created by Under_Koen on 03/12/2019.
 */
public abstract class Solution implements SolutionInfo {
    protected long a, b;

    public static void main(String[] args) throws Exception {
        String clsN = System.getProperty("sun.java.command");
        clsN = clsN.split(" ")[0];
        if (clsN.contains("/")) clsN = clsN.split("/")[1];

        Class<?> cls = Class.forName(clsN, false, Thread.currentThread().getContextClassLoader());
        if (Solution.class.isAssignableFrom(cls)) {
            Class<? extends Solution> day = (Class<? extends Solution>) cls;
            Solution solution = day.getConstructor().newInstance();
            try {
                long start = System.currentTimeMillis();
                solution.execute();
                long end = System.currentTimeMillis();

                solution.test();
                System.out.printf("Took:%n%s ms%n", end - start);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        System.exit(0);
    }

    protected abstract void run(List<String> input);

    public long[] getCorrectOutput() {
        return new long[0];
    }

    private void output() {
        System.out.printf("%nResult day%sa:%n%s%n%nResult day%sb:%n%s%n%n", getDay(), a, getDay(), b);
    }

    public void execute() {
        execute(true);
    }

    public void execute(boolean output) {
        run(Utils.getInput(getYear(), getDay()));
        if (output) output();
    }

    public void test() {
        long[] correct = getCorrectOutput();
        if (correct.length == 0) return;
        if (a != correct[0]) throw new IllegalArgumentException("Result A is incorrect");
        if (correct.length >= 2 && b != correct[1]) throw new IllegalArgumentException("Result B is incorrect");
    }
}
