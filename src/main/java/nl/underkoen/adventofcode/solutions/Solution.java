package nl.underkoen.adventofcode.solutions;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Under_Koen on 03/12/2019.
 */
public abstract class Solution implements SolutionInfo {
    protected long a, b;
    protected String textA, textB;
    protected Boolean submit;

    public static void main(String[] args) throws Exception {
        String clsN = System.getProperty("sun.java.command");
        clsN = clsN.split(" ")[0];
        if (clsN.contains("/")) clsN = clsN.split("/")[1];

        Class<?> cls = Class.forName(clsN, false, Thread.currentThread().getContextClassLoader());
        if (Solution.class.isAssignableFrom(cls)) {
            Class<? extends Solution> day = (Class<? extends Solution>) cls;
            Solution solution = day.getConstructor().newInstance();
            SolutionRunner.run(solution, true, true, true, null);
        }
        System.exit(0);
    }

    protected abstract void run(List<String> input);

    public long[] getCorrectOutput() {
        return new long[0];
    }

    public String[] getCorrectOutputText() {
        return Arrays.stream(getCorrectOutput()).mapToObj(Long::toString).toArray(String[]::new);
    }

    public void output() {
        System.out.printf("%nResult day%sa:%n%s%n%nResult day%sb:%n%s%n%n", getDay(), getA(), getDay(), getB());
    }

    private List<String> input;

    public void loadInput() {
        input = SolutionUtils.getInput(getYear(), getDay());
    }

    public void execute() {
        run(input);
    }

    public void test() {
        String[] correct = getCorrectOutputText();
        if (correct.length == 0) return;
        if (!getA().equals(correct[0])) throw new IllegalArgumentException("Result A is incorrect");
        if (correct.length >= 2 && !getB().equals(correct[1]))
            throw new IllegalArgumentException("Result B is incorrect");
    }

    public String getA() {
        if (textA != null) return textA;
        return Long.toString(a);
    }

    public String getB() {
        if (textB != null) return textB;
        return Long.toString(b);
    }

    public boolean isSubmit() {
        if (submit == null) return getCorrectOutputText().length != 2 && !(getA().equals("0") && getB().equals("0"));
        return submit;
    }

    @Override
    public String toString() {
        return String.format("Year %s, day %s", getYear(), getDay());
    }
}
