package nl.underkoen.adventofcode.solutions.year2018;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day04 extends Solution {
    @Getter private final int day = 4;
    @Getter private final int year = 2018;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{143415, 49944};
    }

    @Override
    protected void run(List<String> input) {
        Collections.sort(input);

        Pattern guardId = Pattern.compile("Guard #(\\d+)");

        int guard = -1;
        int start = -1;
        Map<Integer, Map<Integer, Integer>> times = new HashMap<>();
        for (String s : input) {
            Matcher m = guardId.matcher(s);
            if (m.find()) {
                guard = Integer.parseInt(m.group(1));
                times.putIfAbsent(guard, new HashMap<>());
            } else {
                LocalDateTime moment = LocalDateTime.parse(s.split("]")[0], DateTimeFormatter.ofPattern("'['yyyy-MM-dd HH:mm"));
                if (s.contains("falls")) start = moment.getMinute();
                else {
                    int end = moment.getMinute();
                    for (int i = start; i < end; i++) {
                        Map<Integer, Integer> t = times.get(guard);
                        t.put(i, t.getOrDefault(i, 0) + 1);
                    }
                }
            }
        }

        Map.Entry<Integer, Map<Integer, Integer>> bestGuard = times.entrySet().stream()
                .max(Comparator.comparing(e -> e.getValue().values().stream()
                        .mapToInt(v -> v)
                        .sum()
                )).orElseThrow();

        int bestDay = bestGuard.getValue().entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow()
                .getKey();

        a = bestGuard.getKey() * bestDay;

        Map.Entry<Integer, Map<Integer, Integer>> bestGuardAlt = times.entrySet().stream()
                .max(Comparator.comparing(e -> e.getValue().values().stream()
                        .mapToInt(v -> v)
                        .max()
                        .orElse(0)
                )).orElseThrow();

        int bestDayAlt = bestGuardAlt.getValue().entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow()
                .getKey();

        b = bestGuardAlt.getKey() * bestDayAlt;
    }
}