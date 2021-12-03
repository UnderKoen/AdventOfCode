package nl.underkoen.adventofcode.solutions.year2020;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.linkedlist.CircularLinkedList;
import nl.underkoen.adventofcode.general.linkedlist.Node;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Day23 extends Solution {
    @Getter private final int day = 23;
    @Getter private final int year = 2020;

    public static void run(long iterations, long max, CircularLinkedList<Long> nums, Map<Long, Node<Long>> values) {
        Node<Long> current = nums.getHead();
        for (long i = 0; i < iterations; i++) {
            if (i % 1000 == 0) System.out.print("\r" + i);
            long value = current.getValue();

            Stack<Node<Long>> pickedUp = new Stack<>();
            pickedUp.add(current.getNextNode());
            pickedUp.add(pickedUp.peek().getNextNode());
            pickedUp.add(pickedUp.peek().getNextNode());

            long des = value - 1;
            long min = 1;
            Node<Long> desNode = values.get(des);
            while (desNode == null || pickedUp.contains(desNode)) {
                des--;
                if (des < min) des = max;
                desNode = values.get(des);
            }

            for (Node<Long> node : pickedUp) {
                nums.removeNode(node);
            }

            while (!pickedUp.isEmpty()) {
                Node<Long> pick = pickedUp.pop();
                values.put(pick.getValue(), nums.addAfter(desNode, pick.getValue()));
            }

            current = current.getNextNode();
        }
        System.out.println();
    }

    @Override
    public long[] getCorrectOutput() {
        return new long[]{49725386, 538935646702L};
    }

    @Override
    protected void run(Input input) {
        CircularLinkedList<Long> nums = new CircularLinkedList<>();
        Map<Long, Node<Long>> values = new HashMap<>();

        for (char c : input.get(0).toCharArray()) {
            long l = Long.parseLong(Character.toString(c));
            values.put(l, nums.addAtTail(l));
        }

        run(100, 20, nums, values);

        textA = "";
        nums = nums.startingAt(values.get(1L));
        nums.removeNode(nums.getHead());
        for (Node<Long> node : nums) {
            textA += node.getValue();
        }

        nums = new CircularLinkedList<>();
        values = new HashMap<>();

        for (char c : input.get(0).toCharArray()) {
            long l = Long.parseLong(Character.toString(c));
            values.put(l, nums.addAtTail(l));
        }

        for (long l = 10; l <= 1000000; l++) {
            values.put(l, nums.addAtTail(l));
        }

        run(10000000, 1000000, nums, values);

        b = values.get(1L).getNextNode().getValue() * values.get(1L).getNextNode().getNextNode().getValue();
    }
}