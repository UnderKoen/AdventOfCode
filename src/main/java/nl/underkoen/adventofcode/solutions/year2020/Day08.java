package nl.underkoen.adventofcode.solutions.year2020;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.*;

public class Day08 extends Solution {
    @Getter private final int day = 8;
    @Getter private final int year = 2020;

    private final Map<String, Operation> operations = Map.of(
            "jmp", (context, par) -> context.setPos(context.pos + par - 1),
            "acc", (context, par) -> context.setAcc(context.acc + par),
            "nop", (context, par) -> { }
    );

    @Override
    public long[] getCorrectOutput() {
        return new long[]{1584, 920};
    }

    @Override
    protected void run(List<String> input) {
        a = run(input, operations, null).acc;

        Operation jmp = operations.get("jmp");
        Operation nop = operations.get("nop");

        Map<String, Operation> operations = new HashMap<>(this.operations);
        operations.put("jmp", swap(input, jmp, nop));
        operations.put("nop", swap(input, nop, jmp));

        b = run(input, operations, null).acc;
    }

    public Operation swap(List<String> input, Operation org, Operation oth) {
        return (context, parameter) -> {
            Context clone = new Context(context);
            org.execute(context, parameter);
            oth.execute(clone, parameter);

            run(input, this.operations, clone);
            if (!clone.loop) {
                context.acc = clone.acc;
                context.loop = true;
            }
        };
    }

    public Context run(List<String> input, Map<String, Operation> operations, Context context) {
        if (context == null) context = new Context(0, 0, false, new HashSet<>());
        while (context.pos < input.size() && !context.loop && !context.check()) {
            String line = input.get(context.pos);
            String[] parts = line.split(" ");

            context.pos++;
            int par = Integer.parseInt(parts[1]);
            operations.get(parts[0]).execute(context, par);
        }

        return context;
    }

    public interface Operation {
        void execute(Context context, int parameter);
    }

    @Data
    @AllArgsConstructor
    public static class Context {
        private int pos;
        private int acc;
        private boolean loop;
        Set<Integer> done;

        public Context(Context context) {
            this.pos = context.pos;
            this.acc = context.acc;
            this.loop = context.loop;
            this.done = new HashSet<>(context.done);
        }

        public boolean check() {
            loop = !done.add(pos);
            return loop;
        }
    }
}