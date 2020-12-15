package nl.underkoen.adventofcode.solutions.year2020;

import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14 extends Solution {
    @Getter private final int day = 14;
    @Getter private final int year = 2020;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{12408060320841L, 4466434626828L};
    }

    @Override
    protected void run(List<String> input) {
        Map<Long, Long> mem = new HashMap<>();
        Map<Long, Long> mem2 = new HashMap<>();

        String mask = "";
        for (String s : input) {
            if (s.contains("mask")) {
                mask = s.replace("mask = ", "");
            } else {
                s = s.replace("mem[", "");
                String[] split = s.split("] = ");
                long i2 = calc(Long.parseLong(split[1]), mask);

                char[] addr = new char[36];
                String f = Long.toString(Long.parseLong(split[0]), 2);
                while (f.length() < 36) f = "0" + f;
                for (int i = 0; i < mask.length(); i++) {
                    char c = mask.charAt(i);
                    if (c == '0') addr[i] = f.charAt(i);
                    else addr[i] = c;
                }

                mem.put(Long.parseLong(split[0]), i2);
                put(String.valueOf(addr), Long.parseLong(split[1]), mem2);
            }
        }

        a = mem.values().stream().mapToLong(v -> v).sum();
        b = mem2.values().stream().mapToLong(v -> v).sum();
    }

    public void put(String mask, long value, Map<Long, Long> mem) {
        if (!mask.contains("X")) {
            mem.put(Long.parseLong(mask, 2), value);
            return;
        }
        put(mask.replaceFirst("X", "0"), value, mem);
        put(mask.replaceFirst("X", "1"), value, mem);
    }

    public long calc(long v, String mask) {
        String f = Long.toString(v, 2);
        while (f.length() < 36) f = "0" + f;
        char[] r = new char[36];
        for (int i = 0; i < mask.length(); i++) {
            char c = mask.charAt(i);
            if (c == 'X') {
                r[i] = f.charAt(i);
            } else {
                r[i] = c;
            }
        }

        return Long.parseLong(String.valueOf(r), 2);
    }
}