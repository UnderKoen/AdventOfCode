package nl.underkoen.adventofcode.general.linkedlist;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.AbstractCollection;
import java.util.Iterator;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CircularLinkedList<T> extends AbstractCollection<Node<T>> {
    private Node<T> head = null;
    private Node<T> tail = null;
    @Getter(AccessLevel.NONE) private int size;

    /**
     * Inserts values at the tail
     *
     * @param value the value of the node to insert
     * @return the node of the value
     */
    public Node<T> addAtTail(T value) {
        if (tail == null) return replaceAll(value);
        else return addAfter(getTail(), value);
    }

    /**
     * Inserts values at the head
     *
     * @param value the value of the node to insert
     * @return the node of the value
     */
    public Node<T> addAtHead(T value) {
        if (head == null) return replaceAll(value);
        else return addBefore(getHead(), value);
    }

    /**
     * Adds a node as head and tail, this removes all previous nodes
     *
     * @param value the value of the new node
     * @return the new node
     */
    public Node<T> replaceAll(T value) {
        Node<T> newNode = new Node<>(value);

        newNode.nextNode = newNode;
        newNode.prevNode = newNode;

        head = newNode;
        tail = newNode;

        size = 1;

        return newNode;
    }

    public Node<T> addAfter(Node<T> node, T value) {
        Node<T> newNode = new Node<>(value);
        Node<T> next = node.nextNode;

        node.nextNode = newNode;
        next.prevNode = newNode;
        newNode.nextNode = next;
        newNode.prevNode = node;

        if (node == tail) tail = newNode;

        size++;

        return newNode;
    }

    public Node<T> addBefore(Node<T> node, T value) {
        Node<T> newNode = new Node<>(value);
        Node<T> prevNode = node.prevNode;

        node.prevNode = newNode;
        prevNode.nextNode = newNode;
        newNode.prevNode = prevNode;
        newNode.nextNode = node;

        if (head == node) head = newNode;

        size++;

        return newNode;
    }

    public void remove(Node<T> node) {
        Node<T> prev = node.prevNode;
        Node<T> next = node.nextNode;

        if (prev == null || next == null)
            throw new IllegalStateException("In a circular linked list a half node is not allowed!");

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

        size--;
    }

    //TODO look at implNote

    /**
     * Makes a view starting from that node
     *
     * @param node the node from where to start
     * @return the view
     * @implNote View doesn't really act as a view, because when a element is added that would be a head or tail in the original it isn't updated<br>
     * Example of this:<br>
     * Original list: [1, 2, 3, 4, 5]<br>
     * View starting at 3: [3, 4, 5, 1, 2]<br>
     * <br>
     * When in the view addBefore(1, 6) is called this would be the situation after<br>
     * Original list: [1, 2, 3, 4, 5]<br>
     * View starting at 3: [3, 4, 5, 6, 1, 2]<br>
     * <br>
     * Here only the view knows of the existence of the node 6.<br>
     * This is because the iterator starts at the head which hasn't changed for the original and end at the tail<br>
     */
    public CircularLinkedList<T> startingAt(Node<T> node) {
        if (node == null) return new CircularLinkedList<T>();
        return new CircularLinkedList<T>(node, node.prevNode, size);
    }

    @Override
    public Iterator<Node<T>> iterator() {
        return new Iterator<>() {
            Node<T> current = head;
            Node<T> last = null;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Node<T> next() {
                last = current;
                if (last == tail) current = null;
                else current = current.getNextNode();
                return last;
            }

            @Override
            public void remove() {
                CircularLinkedList.this.remove(last);
            }
        };
    }

    @Override
    public void clear() {
        head = null;
        tail = null;

        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        Iterator<Node<T>> it = iterator();
        if (!it.hasNext())
            return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        while (true) {
            Node<T> e = it.next();
            sb.append(e.getValue());
            if (!it.hasNext())
                return sb.append(']').toString();
            sb.append(',').append(' ');
        }
    }
}
