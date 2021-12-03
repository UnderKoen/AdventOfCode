package nl.underkoen.adventofcode.solutions.year2019;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.LongConsumer;
import java.util.function.LongSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static nl.underkoen.adventofcode.solutions.year2019.opcode.OpcodeRunner.parse;
import static nl.underkoen.adventofcode.solutions.year2019.opcode.OpcodeRunner.process;

/**
 * Created by Under_Koen on 06/12/2019.
 */
public class Day07 extends Solution {
    @Getter private final int day = 7;
    @Getter private final int year = 2019;

    public static long processPhase(long[] program, Collection<Integer> phase) {
        long out = 0;
        for (Integer i : phase) out = process(program, new long[]{i, out});
        return out;
    }

    public static long processFeedbackPhase(long[] program, Collection<Integer> phase) {
        List<BlockingQueue<Long>> inputs = new ArrayList<>(5);

        for (Integer i : phase) {
            BlockingQueue<Long> queue = new LinkedBlockingDeque<>();
            queue.add((long) i);
            inputs.add(queue);
        }
        inputs.get(0).add(0L);

        ExecutorService pool = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            final int j = i;
            LongSupplier inputF = () -> {
                try {
                    return inputs.get(j).take();
                } catch (Exception e) {
                    return 0;
                }
            };
            LongConsumer output = (p) -> inputs.get((j + 1) % 5).add(p);
            pool.submit(() -> {
                process(program, inputF, output);
                latch.countDown();
            });
        }

        try {
            latch.await();
            return inputs.get(0).take();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public long[] getCorrectOutput() {
        return new long[]{567045, 39016654};
    }

    @Override
    protected void run(Input input) {
        long[] program = parse(input);
        List<Set<Integer>> phases = IntStream.range(0, (int) Math.pow(5, 5))
                .mapToObj(i -> {
                    Set<Integer> phase = new LinkedHashSet<>();
                    for (int j = 0; j < 5; j++) phase.add(Math.max(i / (int) Math.pow(5, j) % 5, 0));
                    return phase;
                })
                .filter(phase -> phase.size() == 5)
                .collect(Collectors.toList());

        a = (int) phases
                .stream()
                .mapToLong(phase -> processPhase(program, phase))
                .max()
                .orElse(0);

        b = (int) phases
                .stream()
                .map(phase -> phase.stream()
                        .map(i -> i + 5)
                        .collect(Collectors.toList())
                )
                .mapToLong(phase -> processFeedbackPhase(program, phase))
                .max()
                .orElse(0);
    }
}