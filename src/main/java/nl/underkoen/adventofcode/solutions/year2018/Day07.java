package nl.underkoen.adventofcode.solutions.year2018;

import lombok.Getter;
import nl.underkoen.adventofcode.general.BiHolder;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;
import nl.underkoen.adventofcode.utils.MapUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day07 extends Solution {
    @Getter private final int day = 7;
    @Getter private final int year = 2018;

    @Override
    public String[] getCorrectOutputText() {
        return new String[]{"GKPTSLUXBIJMNCADFOVHEWYQRZ"};
    }

    @Override
    protected void run(List<String> input) {
        Map<String, List<String>> needsMap = new HashMap<>();
        Map<String, List<String>> needsCopy = new HashMap<>();
        Set<String> all = new HashSet<>();

        InputUtils.asRegexGroupList(input, "Step (.) must be finished before step (.) can begin\\.").forEach(steps -> {
            String needs = steps.get(0);
            String to = steps.get(1);

            MapUtils.Add(needsMap, to, needs);
            MapUtils.Add(needsCopy, to, needs);
            all.add(needs);
            all.add(to);
        });


        List<String> order = new ArrayList<>();
        Set<String> todo = new HashSet<>(all);

        while (!todo.isEmpty()) {
            List<String> canExecute = canExecute(needsCopy, todo);
            String current = canExecute.get(0);
            order.add(current);
            todo.remove(current);

            needsCopy.values().forEach(needs -> needs.remove(current));
        }

        StringBuilder text = new StringBuilder();
        for (String s : order) text.append(s);
        textA = text.toString();


        todo = new HashSet<>(all);
        List<BiHolder<Integer, String>> workers = new ArrayList<>();

        int i = 0;
        do {
            int t = i;
            workers.removeIf(e -> {
                if (e.getKey() >= t) return false;
                needsMap.values().forEach(needs -> needs.remove(e.getValue()));
                return true;
            });
            List<String> canExecute = canExecute(needsMap, todo);
            for (int j = workers.size(); j < 5; j++) {
                if (canExecute.size() == 0) break;
                String current = canExecute.get(0);
                canExecute.remove(current);
                todo.remove(current);
                workers.add(new BiHolder<>(t + current.charAt(0) - 5, current));
            }
            i++;
        } while (!workers.isEmpty());

        b = i - 1;
    }

    public List<String> canExecute(Map<String, List<String>> needsMap, Set<String> all) {
        List<String> canExecute = new ArrayList<>();
        for (String s : all) {
            List<String> needs = needsMap.get(s);
            if (needs == null || needs.isEmpty()) canExecute.add(s);
        }
        Collections.sort(canExecute);
        return canExecute;
    }
}