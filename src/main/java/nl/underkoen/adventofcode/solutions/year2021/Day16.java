package nl.underkoen.adventofcode.solutions.year2021;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Day16 extends Solution {
    @Getter private final int day = 16;
    @Getter private final int year = 2021;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{974, 180616437720L};
    }

    @Override
    protected void run(Input input) {
        String binary = input.asCharacters()
                .flatMap(s -> s)
                .map(Object::toString)
                .map(c -> Long.parseLong(c, 16))
                .map(l -> Long.toString(l, 2))
                .map(s -> String.format("%4s", s))
                .map(s -> s.replace(' ', '0'))
                .reduce("", (s, s2) -> s + s2);

        Stack<BITS> queue = new Stack<>();
        queue.add(new BITS(binary));
        while (!queue.isEmpty()) {
            BITS bits = queue.pop();
            a += bits.getVersion();
            if (bits.getType() == 4) continue;
            queue.addAll(bits.getSubPackets());
        }

        b = new BITS(binary).value();
    }

    public static class BITS {
        private final String binary;

        public BITS(String binary) {
            this.binary = binary;
        }

        public int getVersion() {
            return Integer.parseInt(binary.substring(0, 3), 2);
        }

        public int getType() {
            return Integer.parseInt(binary.substring(3, 6), 2);
        }

        public int startPoint() {
            int start = 6;
            if (getType() == 4) return start;
            start++;
            if (!lengthType()) start += 15;
            else start += 11;
            return start;
        }

        public boolean lengthType() {
            return binary.charAt(6) == '1';
        }

        public int amountSubTypes() {
            return Integer.parseInt(binary.substring(7, 7 + 11), 2);
        }

        public int lengthSubTypes() {
            return Integer.parseInt(binary.substring(7, 7 + 15), 2);
        }

        public List<String> getPackets() {
            List<String> packets = new ArrayList<>();
            int i = startPoint();
            while (binary.charAt(i) == '1') {
                packets.add(binary.substring(i + 1, i + 5));
                i += 5;
            }
            packets.add(binary.substring(i + 1, i + 5));
            return packets;
        }

        public List<BITS> getSubPackets() {
            String sub = binary.substring(startPoint());
            List<BITS> bits = new ArrayList<>();
            int l = 0;
            if (lengthType()) {
                for (int i = 0; i < amountSubTypes(); i++) {
                    BITS last = new BITS(sub.substring(l));
                    bits.add(last);
                    l += last.length();
                }

            } else {
                int max = lengthSubTypes();
                while (l < max) {
                    BITS last = new BITS(sub.substring(l));
                    bits.add(last);
                    l += last.length();
                }
            }
            return bits;
        }

        public int length() {
            if (getType() == 4) return getPackets().stream().mapToInt(s -> s.length() + 1).sum() + startPoint();
            if (!lengthType()) {
                return lengthSubTypes() + startPoint();
            } else {
                return startPoint() + getSubPackets().stream().mapToInt(BITS::length).sum();
            }
        }

        public long value() {
            if (getType() == 4) return Long.parseLong(getPackets().stream().reduce("", (s, s2) -> s + s2), 2);
            List<BITS> subPackets = getSubPackets();
            return switch (getType()) {
                case 0 -> subPackets.stream().mapToLong(BITS::value).sum();
                case 1 -> subPackets.stream().mapToLong(BITS::value).reduce(1, (left, right) -> left * right);
                case 2 -> subPackets.stream().mapToLong(BITS::value).min().orElseThrow();
                case 3 -> subPackets.stream().mapToLong(BITS::value).max().orElseThrow();
                case 5 -> subPackets.get(0).value() > subPackets.get(1).value() ? 1 : 0;
                case 6 -> subPackets.get(0).value() < subPackets.get(1).value() ? 1 : 0;
                case 7 -> subPackets.get(0).value() == subPackets.get(1).value() ? 1 : 0;
                default -> throw new IllegalArgumentException();
            };
        }
    }
}