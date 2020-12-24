package nl.underkoen.adventofcode.general.linkedlist;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Iterator;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CircularLinkedList<T> implements Iterable<Node<T>> {
    private Node<T> head = null;
    private Node<T> tail = null;

    public Node<T> add(T value) {
        Node<T> newNode = new Node<>(value);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.nextNode = newNode;
            newNode.prevNode = tail;
        }

        tail = newNode;
        tail.nextNode = head;
        head.prevNode = tail;

        return newNode;
    }

    public Node<T> addAfter(Node<T> node, T value) {
        if (node == tail) return add(value);

        Node<T> newNode = new Node<>(value);
        Node<T> next = node.nextNode;

        node.nextNode = newNode;
        next.prevNode = newNode;
        newNode.nextNode = next;
        newNode.prevNode = node;

        return newNode;
    }

    public void remove(Node<T> node) {
        Node<T> prev = node.prevNode;
        Node<T> next = node.nextNode;

        if (prev == null || next == null) return;

        node.prevNode = null;
        node.nextNode = null;

        if (node == head) {
            head = next;
            if (node == tail) {
                head = null;
                tail = null;
                return;
            }
        }

        if (node == tail) {
            tail = prev;
        }

        prev.nextNode = next;
        next.prevNode = prev;
    }

    public CircularLinkedList<T> startingAt(Node<T> node) {
        if (node == null) return new CircularLinkedList<T>();
        return new CircularLinkedList<T>(node, node.prevNode);
    }

    @Override
    public Iterator<Node<T>> iterator() {
        return new Iterator<Node<T>>() {
            Node<T> current = head;
            boolean first = true;

            @Override
            public boolean hasNext() {
                return current != null && (first || current != head);
            }

            @Override
            public Node<T> next() {
                first = false;
                Node<T> c = current;
                current = current.getNextNode();
                return c;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("[");

        Iterator<Node<T>> iterator = iterator();
        while (iterator.hasNext()) {
            Node<T> next = iterator.next();
            string.append(next.getValue());
            if (iterator.hasNext()) string.append(", ");
        }

        string.append("]");
        return string.toString();
    }
}
