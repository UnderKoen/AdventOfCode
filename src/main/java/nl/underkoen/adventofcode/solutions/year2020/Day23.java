package nl.underkoen.adventofcode.solutions.year2020;

import lombok.Getter;
import nl.underkoen.adventofcode.general.linkedlist.CircularLinkedList;
import nl.underkoen.adventofcode.general.linkedlist.Node;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Day23 extends Solution {
    @Getter private final int day = 23;
    @Getter private final int year = 2020;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{49725386, 538935646702L};
    }

    @Override
    protected void run(List<String> input) {
        CircularLinkedList<Long> nums = new CircularLinkedList<>();
        Map<Long, Node<Long>> values = new HashMap<>();

        for (char c : input.get(0).toCharArray()) {
            long l = Long.parseLong(Character.toString(c));
            values.put(l, nums.add(l));
        }

        for (long l = 10; l <= 1000000; l++) {
            values.put(l, nums.add(l));
        }

        Node<Long> current = nums.getHead();

        for (long i = 0; i <= 10000000; i++) {
            if (i % 1000 == 0) System.out.print("\r" + i);
            long value = current.getValue();

            Stack<Node<Long>> pickedUp = new Stack<>();
            pickedUp.add(current.getNextNode());
            pickedUp.add(pickedUp.peek().getNextNode());
            pickedUp.add(pickedUp.peek().getNextNode());

            long des = value - 1;
            long min = 1;
            long max = 1000000;
            Node<Long> desNode = values.get(des);
            while (desNode == null || pickedUp.contains(desNode)) {
                des--;
                if (des < min) des = max;
                desNode = values.get(des);
            }

            for (Node<Long> node : pickedUp) {
                nums.remove(node);
            }

            while (!pickedUp.isEmpty()) {
                Node<Long> pick = pickedUp.pop();
                values.put(pick.getValue(), nums.addAfter(desNode, pick.getValue()));
            }

            current = current.getNextNode();
        }
        System.out.println();

//        textA = "";
//        nums = nums.startingAt(values.get(1L));
//        nums.remove(nums.getHead());
//        for (Node<Long> node : nums) {
//            textA += node.getValue();
//        }

        b = values.get(1L).getNextNode().getValue() * values.get(1L).getNextNode().getNextNode().getValue();
    }
}