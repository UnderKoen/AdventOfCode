package nl.underkoen.adventofcode.solutions.year2020;

import lombok.Getter;
import lombok.Value;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.map.collection.HashMapList;
import nl.underkoen.adventofcode.general.map.collection.MapList;
import nl.underkoen.adventofcode.general.map.counter.HashMapCounter;
import nl.underkoen.adventofcode.general.map.counter.MapCounter;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day16 extends Solution {
    @Getter private final int day = 16;
    @Getter private final int year = 2020;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{26053, 1515506256421L};
    }

    @Override
    protected void run(Input input) {
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

        MapList<Field, Long> valid = new HashMapList<>();

        for (Field field : fields) {
            MapCounter<Long, Long> amount = new HashMapCounter<>(0L);
            for (Ticket ticket : tickets) {
                long i = 0;
                for (Long value : ticket.getValues()) {
                    if (field.valid(value)) amount.increase(i);
                    i++;
                }
            }
            amount.forEach((index, count) -> {
                if (count == tickets.size()) valid.add(field, index);
            });
        }

        valid.reduceSelf();

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