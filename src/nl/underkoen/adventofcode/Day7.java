package nl.underkoen.adventofcode;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static nl.underkoen.adventofcode.opcode.OpcodeRunner.parse;
import static nl.underkoen.adventofcode.opcode.OpcodeRunner.process;

/**
 * Created by Under_Koen on 06/12/2019.
 */
public class Day7 extends AdventOfCode {
    public static int processPhase(int[] program, Collection<Integer> phase) {
        int out = 0;
        for (Integer i : phase) out = process(program, new int[]{out, i});
        return out;
    }

    public static int processFeedbackPhase(int[] program, Collection<Integer> phase) {
        List<BlockingQueue<Integer>> inputs = new ArrayList<>(5);

        for (Integer i : phase) {
            BlockingQueue<Integer> queue = new LinkedBlockingDeque<>();
            queue.add(i);
            inputs.add(queue);
        }
        inputs.get(0).add(0);

        ExecutorService pool = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            final int j = i;
            IntSupplier inputF = () -> {
                try {
                    return inputs.get(j).take();
                } catch (Exception e) {
                    return 0;
                }
            };
            IntConsumer output = (p) -> inputs.get((j + 1) % 5).add(p);
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
    int getDay() {
        return 7;
    }

    @Override
    public int[] getCorrectOutput() {
        return new int[]{567045, 39016654};
    }

    @Override
    void run(List<String> input) {
        int[] program = parse(input);
        List<Set<Integer>> phases = IntStream.range(0, (int) Math.pow(5, 5))
                .mapToObj(i -> {
                    Set<Integer> phase = new LinkedHashSet<>();
                    for (int j = 0; j < 5; j++) phase.add(Math.max(i / (int) Math.pow(5, j) % 5, 0));
                    return phase;
                })
                .filter(phase -> phase.size() == 5)
                .collect(Collectors.toList());

        a = phases
                .stream()
                .mapToInt(phase -> processPhase(program, phase))
                .max()
                .orElse(0);

        b = phases
                .stream()
                .map(phase -> phase.stream()
                        .map(i -> i + 5)
                        .collect(Collectors.toList())
                )
                .mapToInt(phase -> processFeedbackPhase(program, phase))
                .max()
                .orElse(0);
    }
}