package nl.underkoen.adventofcode.solutions;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(Parameterized.class)
public class SolutionsTest {
    private static final String SOLUTIONS_PACKAGE = "nl.underkoen.adventofcode.solutions";

    private final Solution solution;

    public SolutionsTest(Solution solution) {
        this.solution = solution;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Solution> data() {
        return SolutionUtils.getAllSolutions(SOLUTIONS_PACKAGE).entrySet()
                .stream()
                .sorted(Comparator.comparingInt(Map.Entry::getKey))
                .flatMap(e -> e.getValue().stream())
                .collect(Collectors.toList());
    }

    @Test
    public void testSolution() {
        solution.loadInput();
        solution.execute();

        String[] correct = solution.getCorrectOutputText();
        if (correct.length >= 1) Assert.assertEquals(correct[0], solution.getA());
        if (correct.length >= 2) Assert.assertEquals(correct[1], solution.getB());

    }
}
