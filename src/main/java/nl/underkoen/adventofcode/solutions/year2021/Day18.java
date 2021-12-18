package nl.underkoen.adventofcode.solutions.year2021;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.CharacterUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class Day18 extends Solution {
    @Getter private final int day = 18;
    @Getter private final int year = 2021;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{3675, 4650};
    }

    @Override
    protected void run(Input input) {
        List<SnailFish> questions = new ArrayList<>();
        for (String line : input) {
            Stack<SnailFish> stack = new Stack<>();
            for (char c : line.toCharArray()) {
                int v = Character.getNumericValue(c);
                if (CharacterUtils.isClosingBracket(c)) {
                    SnailFish right = stack.pop();
                    SnailFish left = stack.pop();
                    stack.push(new SnailFish(left, right));
                } else if (v >= 0 && v <= 9) {
                    stack.push(new ConstantSnailFish(v));
                }
            }

            SnailFish question = stack.pop();
            question.reduce();
            questions.add(question);
        }

        for (SnailFish q1 : questions) {
            for (SnailFish q2 : questions) {
                if (q1 == q2) continue;
                SnailFish answer = new SnailFish(q1.clone(), q2.clone());
                answer.reduce();
                b = Math.max(b, answer.magnitude());
            }
        }

        SnailFish total = new SnailFish(questions.get(0), questions.get(1));
        total.reduce();
        for (int i = 2; i < questions.size(); i++) {
            total = new SnailFish(total, questions.get(i));
            total.reduce();
        }

        a = total.magnitude();
    }

    public static class SnailFish {
        private SnailFish left;
        private SnailFish right;

        public SnailFish(SnailFish left, SnailFish right) {
            this.left = left;
            this.right = right;
        }

        public void reduce() {
            String prev = "";
            while (!prev.equals(prev = toString())) {
                List<SnailFish> nested = nested(0);
                if (nested.size() > 0) {
                    nested.get(0).explode(this);
                    continue;
                }
                split(this);
            }
        }

        public void explode(SnailFish root) {
            ConstantSnailFish prev = root.prev(this);
            ConstantSnailFish next = root.next(this);
            SnailFish parent = root.parent(this);

            if (prev != null) prev.value += mostLeft().value;
            if (next != null) next.value += mostRight().value;
            parent.replace(this, new ConstantSnailFish(0));
        }

        public boolean split(SnailFish root) {
            if (left != null) {
                return left.split(root) || right.split(root);
            }

            ConstantSnailFish fish = (ConstantSnailFish) this;
            if (fish.value < 10) return false;
            long v = fish.value / 2;
            root.parent(this).replace(this, new SnailFish(new ConstantSnailFish(v), new ConstantSnailFish(fish.value - v)));
            return true;
        }

        public void replace(SnailFish what, SnailFish with) {
            if (left == what) left = with;
            if (right == what) right = with;
        }

        public List<SnailFish> nested(int depth) {
            if (depth == 4) return List.of(this);
            List<SnailFish> list = new ArrayList<>();
            list.addAll(left.nested(depth + 1));
            list.addAll(right.nested(depth + 1));
            return list;
        }

        public ConstantSnailFish prev(SnailFish fish) {
            ConstantSnailFish r = prev2(fish);
            if (r != null) return r;
            if (!contains(fish)) return null;
            SnailFish parent = parent(fish);
            while (parent.left.contains(fish)) {
                parent = parent(parent);
                if (parent == null) return null;
            }
            return parent.left.mostRight();
        }

        public ConstantSnailFish prev2(SnailFish fish) {
            if (left == null) return null;
            if (left == fish) return null;
            if (right == fish) return left.mostRight();
            ConstantSnailFish prev = left.prev2(fish);
            if (prev != null) return prev;
            return right.prev2(fish);
        }

        public ConstantSnailFish next(SnailFish fish) {
            ConstantSnailFish r = next2(fish);
            if (r != null) return r;
            if (!contains(fish)) return null;
            SnailFish parent = parent(fish);
            while (parent.right.contains(fish)) {
                parent = parent(parent);
                if (parent == null) return null;
            }
            return parent.right.mostLeft();
        }

        public ConstantSnailFish next2(SnailFish fish) {
            if (left == null) return null;
            if (left == fish) return right.mostLeft();
            if (right == fish) return null;
            ConstantSnailFish prev = left.next2(fish);
            if (prev != null) return prev;
            return left.next2(fish);
        }

        public boolean contains(SnailFish fish) {
            if (this == fish) return true;
            if (left == fish || right == fish) return true;
            if (left == null || right == null) return false;
            return left.contains(fish) || right.contains(fish);
        }

        public ConstantSnailFish mostLeft() {
            if (left == null) return (ConstantSnailFish) this;
            return left.mostLeft();
        }

        public ConstantSnailFish mostRight() {
            if (right == null) return (ConstantSnailFish) this;
            return right.mostRight();
        }

        public SnailFish parent(SnailFish fish) {
            if (left == null) return null;
            if (left == fish) return this;
            if (right == fish) return this;
            SnailFish prev = left.parent(fish);
            if (prev != null) return prev;
            return right.parent(fish);
        }

        public long magnitude() {
            return 3 * left.magnitude() + 2 * right.magnitude();
        }

        @Override
        public String toString() {
            return String.format("[%s,%s]", left.toString(), right.toString());
        }

        @Override
        protected SnailFish clone() {
            return new SnailFish(left.clone(), right.clone());
        }
    }

    public static class ConstantSnailFish extends SnailFish {
        private long value;

        public ConstantSnailFish(long value) {
            super(null, null);
            this.value = value;
        }

        @Override
        public long magnitude() {
            return value;
        }

        @Override
        public List<SnailFish> nested(int depth) {
            return List.of();
        }

        @Override
        public String toString() {
            return Objects.toString(value);
        }

        @Override
        protected SnailFish clone() {
            return new ConstantSnailFish(value);
        }
    }
}