package nl.underkoen.adventofcode.solutions.year2020;

import lombok.Getter;
import lombok.Value;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;
import nl.underkoen.adventofcode.utils.MapUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day16 extends Solution {
    @Getter private final int day = 16;
    @Getter private final int year = 2020;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{26053, 1515506256421L};
    }

    @Override
    protected void run(List<String> input) {
        List<List<String>> subInputs = InputUtils.asSubInputs(input);
        List<Field> fields = subInputs.get(0).stream()
                .map(Field::parse)
                .collect(Collectors.toList());

        List<Ticket> tickets = subInputs.get(2).stream()
                .skip(1)
                .map(Ticket::parse)
                .collect(Collectors.toList());

        tickets.removeIf(t -> t.values.stream()
                .filter(i -> fields.stream().noneMatch(c -> c.valid(i)))
                .peek(i -> a += i)
                .count() != 0);

        Map<Field, List<Long>> valid = new HashMap<>();

        for (Field field : fields) {
            Map<Long, Long> amount = new HashMap<>();
            for (Ticket ticket : tickets) {
                long i = 0;
                for (Long value : ticket.getValues()) {
                    if (field.valid(value)) MapUtils.increaseLong(amount, i);
                    i++;
                }
            }
            amount.forEach((index, count) -> {
                if (count == tickets.size()) MapUtils.add(valid, field, index);
            });
        }

        for (int t = 0; t < valid.size(); t++) {
            for (Field field : fields) {
                List<Long> possible = valid.get(field);
                if (possible.size() != 1) continue;

                long only = possible.get(0);
                for (Field f : fields) {
                    if (field == f) continue;
                    MapUtils.remove(valid, f, only);
                }
            }
        }

        List<Long> your = InputUtils.asNumberList(subInputs.get(1)).collect(Collectors.toList());

        b = fields.stream()
                .filter(f -> f.name.startsWith("departure"))
                .map(valid::get)
                .mapToLong(l -> l.get(0))
                .map(i -> your.get((int) i))
                .reduce(1, (l1, l2) -> l1 * l2);
    }

    @Value
    public static class Ticket {
        List<Long> values;

        public static Ticket parse(String str) {
            String[] parts = str.split(",");
            return new Ticket(Arrays.stream(parts).map(Long::parseLong).collect(Collectors.toList()));
        }
    }

    @Value
    public static class Field {
        String name;
        Constraint constraint1;
        Constraint constraint2;

        public static Field parse(String str) {
            String[] parts = str.split(": | or ");
            return new Field(parts[0], Constraint.parse(parts[1]), Constraint.parse(parts[2]));
        }

        public boolean valid(long v) {
            return constraint1.valid(v) || constraint2.valid(v);
        }
    }

    @Value
    public static class Constraint {
        long lower;
        long higher;

        public static Constraint parse(String str) {
            String[] parts = str.trim().split("-");
            return new Constraint(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
        }

        public boolean valid(long v) {
            return lower <= v && v <= higher;
        }
    }
}