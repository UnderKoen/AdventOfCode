package nl.underkoen.adventofcode.solutions.year2019;

import lombok.Getter;
import nl.underkoen.adventofcode.general.Holder;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.solutions.year2019.opcode.OpcodeRunner;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Consumer;

import static nl.underkoen.adventofcode.solutions.year2019.opcode.OpcodeRunner.parse;

/**
 * Created by Under_Koen on 22/12/2019.
 */
public class Day23 extends Solution {
    @Getter private final int day = 23;
    @Getter private final int year = 2019;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{22659, 17429};
    }

    @Override
    protected void run(List<String> input) {
        long[] program = parse(input);

        Map<Long, Computer> computers = new HashMap<>();
        Map<Long, Deque<Packet>> packets = new HashMap<>();
        Holder<Packet> nat = new Holder<>();

        for (long i = 0; i < 50; i++) {
            packets.put(i, new ArrayDeque<>());
            Computer computer = new Computer(i, new LinkedBlockingDeque<>(), program, (p) -> {
                if (p.address == 255) {
                    if (a == 0) a = p.y;
                    nat.setValue(p);
                    return;
                }
                packets.get(p.address).addLast(p);
            });
            computers.put(i, computer);
            new Thread(computer::start).start();
        }

        for (long i = 0; i < 50; i++) {
            computers.get(i).sendNumber(i);
        }

        long prev = 0;
        while (true) {
            for (long i = 0; i < 50; i++) computers.get(i).sendPacket(packets.get(i).pollFirst());
            if (packets.values().stream().allMatch(Collection::isEmpty)) {
                Packet packet = nat.getValue();
                computers.get(0l).sendPacket(nat.getValue());
                if (packet.y == prev) {
                    b = packet.y;
                    break;
                }
                prev = packet.y;
            }
        }
    }

    private static class Computer {
        long id;
        BlockingQueue<Long> queue;
        CountDownLatch latch;
        long[] program;
        int i = 0;
        Packet packet;
        Consumer<Packet> send;

        public Computer(long id, BlockingQueue<Long> queue, long[] program, Consumer<Packet> send) {
            this.id = id;
            this.queue = queue;
            this.program = Arrays.copyOf(program, program.length);
            this.send = send;
        }

        public void start() {
            OpcodeRunner.process(program, () -> {
                if (latch != null) latch.countDown();
                try {
                    return queue.take();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(-1);
                    return 0; //Unreachable
                }
            }, (l) -> {
                switch (i++) {
                    case 0:
                        packet = new Packet();
                        packet.address = l;
                        break;
                    case 1:
                        packet.x = l;
                        break;
                    case 2:
                        packet.y = l;
                        i = 0;
                        send.accept(packet);
                        break;
                }
            });
        }

        public void sendNumber(long i) {
            latch = new CountDownLatch(1);
            queue.add(i);
            try {
                latch.await();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
            latch = null;
        }

        public void sendPacket(Packet packet) {
            if (packet == null) {
                sendNumber(-1);
            } else {
                sendNumber(packet.x);
                sendNumber(packet.y);
            }
        }
    }

    private static class Packet {
        long address, x, y;
    }
}
