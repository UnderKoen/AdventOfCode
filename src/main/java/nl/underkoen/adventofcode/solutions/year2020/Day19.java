package nl.underkoen.adventofcode.solutions.year2020;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Day19 extends Solution {
    @Getter private final int day = 19;
    @Getter private final int year = 2020;

    public static Rule check(long rule, Map<Long, Rule> rules, Map<Long, String> r) {
        if (rules.containsKey(rule)) return rules.get(rule);

        String ru = r.get(rule);
        Rule rule1;
        if (ru.contains("\"")) {
            char c = ru.replace("\"", "").charAt(0);
            rule1 = new Rule(c);
        } else {
            String[] split = ru.split(" \\| ");
            rule1 = new Rule();
            for (String s : split) {
                List<Rule> rul = new ArrayList<>();
                for (String s1 : s.split(" ")) {
                    rul.add(check(Long.parseLong(s1), rules, r));
                }
                if (rule1.toObeyL == null) rule1.toObeyL = rul;
                else rule1.toObeyR = rul;
            }
        }

        rules.put(rule, rule1);
        return rule1;
    }

    @Override
    public long[] getCorrectOutput() {
        return new long[]{156, 363};
    }

    @Override
    protected void run(Input input) {
        List<List<String>> lists = InputUtils.asSubInputs(input);
        List<String> rules = lists.get(0);

        Map<Long, String> r = new HashMap<>();
        for (String rule : rules) {
            String[] split = rule.split(": ");
            r.put(Long.parseLong(split[0]), split[1]);
        }

        Map<Long, Rule> ru = new HashMap<>();

        Rule rule0 = check(0, ru, r);
        Pattern compile = Pattern.compile(rule0.toRegex());
        for (String s : lists.get(1)) {
            if (compile.matcher(s).matches()) a++;
        }

        rules.remove("8: 42");
        rules.add("8: 42 | 42 1000");
        for (int i = 1000; i < 1100; i++) {
            rules.add(i + ": 42 | 42 " + (i + 1));
        }
        rules.add("1100: 42");

        rules.remove("11: 42 31");
        rules.add("11: 42 31 | 42 2000 31");
        for (int i = 2000; i < 2100; i++) {
            rules.add(i + ": 42 31 | 42 " + (i + 1) + " 31");
        }
        rules.add("2100: 42 31");

        r.clear();
        for (String rule : rules) {
            String[] split = rule.split(": ");
            r.put(Long.parseLong(split[0]), split[1]);
        }

        ru.clear();

        rule0 = check(0, ru, r);
        System.out.println(rule0.toRegex());
        compile = Pattern.compile(rule0.toRegex());
        for (String s : lists.get(1)) {
            if (compile.matcher(s).matches()) b++;
        }
    }

    public static class Rule {
        String regex = null;
        private List<Rule> toObeyL;
        private List<Rule> toObeyR;
        private Character c;

        public Rule() {
        }

        public Rule(char c) {
            this.c = c;
        }

        public String toRegex() {
            if (regex != null) return regex;
            if (c != null) {
                regex = Character.toString(c);
                return regex;
            }

            String l = "";
            if (toObeyL != null) for (Rule rule : toObeyL) {
                l += rule.toRegex();
            }
            if (toObeyR == null) {
                regex = l;
                return regex;
            }

            String r = "";
            for (Rule rule : toObeyR) {
                r += rule.toRegex();
            }

            regex = "(?:" + l + "|" + r + ")";
            return regex;
        }

        public boolean validate(String str) {
            return false;
        }
    }
}