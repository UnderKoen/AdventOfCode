package nl.underkoen.adventofcode.solutions.year2020;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;

import java.util.*;
import java.util.stream.Collectors;

public class Day22 extends Solution {
    @Getter private final int day = 22;
    @Getter private final int year = 2020;

    public static long playGame(List<Long> player1, List<Long> player2, boolean recursion) {
        player1 = new ArrayList<>(player1);
        player2 = new ArrayList<>(player2);

        while (player1.size() != 0 && player2.size() != 0) {
            playRound(player1, player2, recursion);
        }

        long r = 0;
        while (!player1.isEmpty()) r += player1.size() * player1.remove(0);
        while (!player2.isEmpty()) r += player2.size() * player2.remove(0);
        return r;
    }

    public static boolean playRound(List<Long> player1, List<Long> player2, boolean recursion) {
        long p1 = player1.remove(0);
        long p2 = player2.remove(0);

        boolean won = p1 > p2;

        if (recursion && p1 <= player1.size() && p2 <= player2.size()) {
            List<Long> sub1 = new ArrayList<>(player1.subList(0, (int) p1));
            List<Long> sub2 = new ArrayList<>(player2.subList(0, (int) p2));

            Set<Integer> rounds = new HashSet<>();
            while (sub1.size() != 0 && sub2.size() != 0) {
                if (won = !rounds.add(Objects.hash(sub1, sub2))) break;
                won = playRound(sub1, sub2, true);
            }
        }

        if (won) {
            player1.add(p1);
            player1.add(p2);
        } else {
            player2.add(p2);
            player2.add(p1);
        }

        return won;
    }

    @Override
    public long[] getCorrectOutput() {
        return new long[]{32629, 32519};
    }

    @Override
    protected void run(Input input) {
        List<List<String>> lists = InputUtils.asSubInputs(input).toList();
        List<Long> player1 = lists.get(0).stream()
                .skip(1)
                .map(Long::parseLong)
                .collect(Collectors.toList());

        List<Long> player2 = lists.get(1).stream()
                .skip(1)
                .map(Long::parseLong)
                .collect(Collectors.toList());

        a = playGame(player1, player2, false);
        b = playGame(player1, player2, true);
    }
}