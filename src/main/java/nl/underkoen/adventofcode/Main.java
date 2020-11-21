package nl.underkoen.adventofcode;

import com.google.common.reflect.ClassPath;
import lombok.SneakyThrows;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.solutions.SolutionInfo;

import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by Under_Koen on 03/02/2020.
 */
public class Main {
//    public static final String PACKAGE = Solution.class.getPackageName();
//
//    public static void main(String[] args) {
//        PrintStream out = System.out;
//
//        if (args.length >= 1 && args[0].equals("--show-ouput"))
//        System.setOut(new PrintStream(new OutputStream() {
//            @Override
//            public void write(int b) {
//            }
//        }));
//
//        Map<Integer, List<Solution>> solutions = getAllSolutions();
//
//        for (Map.Entry<Integer, List<Solution>> entry : solutions.entrySet()) {
//            out.printf("%n%n=== YEAR %d ===%n%n%n", entry.getKey());
//            entry.getValue().sort(Comparator.comparingInt(SolutionInfo::getDay));
//            for (Solution solution : entry.getValue()) {
//                long start = System.currentTimeMillis();
//                try {
//                    solution.execute(false);
//                    long end = System.currentTimeMillis();
//
//                    solution.test();
//                    out.printf("Day %s succeeded and took %s ms%n", solution.getDay(), end - start);
//                } catch (Exception e) {
//                    long end = System.currentTimeMillis();
//                    System.err.printf("Day %s failed, after %s ms%n", solution.getDay(), end - start);
//                }
//            }
//        }
//
//        System.exit(0);
//    }
//
//    public static class RunToday {
//        public static void main(String[] args) {
//            LocalDateTime date = LocalDateTime.now();
//
//            Solution solution = getAllSolutions()
//                    .get(date.getYear())
//                    .stream()
//                    .filter(s -> s.getDay() == date.getDayOfMonth())
//                    .findAny()
//                    .orElseThrow(() -> new IllegalStateException("Today's solution not found"));
//
//            solution.execute();
//        }
//    }
//
//    public static class RunLast {
//        public static void main(String[] args) {
//            Solution solution = getAllSolutions()
//                    .entrySet()
//                    .stream()
//                    .max(Comparator.comparingInt(Map.Entry::getKey))
//                    .orElseThrow(RuntimeException::new)
//                    .getValue()
//                    .stream()
//                    .max(Comparator.comparingInt(SolutionInfo::getDay))
//                    .orElseThrow(RuntimeException::new);
//
//            solution.execute();
//        }
//    }
}
