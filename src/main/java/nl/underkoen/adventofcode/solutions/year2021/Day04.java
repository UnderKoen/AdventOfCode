package nl.underkoen.adventofcode.solutions.year2021;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.stream.EBoolStream;
import nl.underkoen.adventofcode.general.stream.EStream;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.MatrixUtils;

import java.util.Arrays;
import java.util.List;

public class Day04 extends Solution {
    @Getter private final int day = 4;
    @Getter private final int year = 2021;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{65325, 4624};
    }

    @Override
    protected void run(Input input) {
        List<Input> inputs = input.asSubInputs().toMutable();

        List<Long> bingoNums = inputs.get(0).asNumbers().toList();
        inputs.remove(0);

        List<Bingo> bingos = EStream.of(inputs).map(Bingo::new).toMutable();

        for (Long num : bingoNums) {
            for (Bingo bingo : bingos) {
                bingo.mark(num);

                if (a == 0 && bingo.isBingo()) {
                    a = num * bingo.getUnmarkedSum();
                }

                if (bingos.size() == 1 && bingo.isBingo()) {
                    b = num * bingo.getUnmarkedSum();
                    return;
                }

            }

            bingos.removeIf(Bingo::isBingo);
        }
    }

    public static class Bingo {
        public final long[][] card = new long[5][5];
        public final boolean[][] marked = new boolean[5][5];

        public Bingo(List<String> in) {
            for (int i = 0; i < in.size(); i++) {
                card[i] = Arrays.stream(
                                in.get(i)
                                        .trim()
                                        .split(" +")
                        )
                        .mapToLong(Long::parseLong)
                        .toArray();
            }
        }

        public void mark(long num) {
            for (int i = 0; i < card.length; i++) {
                for (int j = 0; j < card[i].length; j++) {
                    if (card[i][j] == num) {
                        marked[i][j] = true;
                    }
                }
            }
        }

        public boolean isBingo() {
            if (EStream.of(marked)
                    .mapToBool(EBoolStream::all)
                    .any()
            ) return true;

            return EStream.of(MatrixUtils.rotateMatrixRight(marked))
                    .mapToBool(EBoolStream::all)
                    .any();
        }

        public long getUnmarkedSum() {
            long sum = 0;
            for (int i = 0; i < card.length; i++) {
                for (int j = 0; j < card[i].length; j++) {
                    if (!marked[i][j]) {
                        sum += card[i][j];
                    }
                }
            }
            return sum;
        }
    }
}