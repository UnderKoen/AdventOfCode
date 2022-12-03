package nl.underkoen.adventofcode.general.linkedlist;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.underkoen.adventofcode.general.stream.EStream;

import javax.annotation.Nonnull;
import java.util.AbstractCollection;
import java.util.Iterator;

@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor
public class LinkedList<T> extends AbstractCollection<Node<T>> {
    private Node<T> head = null;
    private Node<T> tail = null;
    @Getter(AccessLevel.NONE) private int size;

    protected void placeNode(Node<T> prev, Node<T> node, Node<T> next) {
        if (prev != null) prev.nextNode = node;
        else setHead(node);

        if (next != null) next.prevNode = node;
        else setTail(node);

        node.prevNode = prev;
        node.nextNode = next;
    }

    protected Node<T> makeNode(T value) {
        return new Node<>(value);
    }

    /**
     * Adds a node as head and tail, this removes all previous nodes
     *
     * @param value the value of the new node
     * @return the new node
     */
    public Node<T> replaceAll(T value) {
        Node<T> newNode = makeNode(value);
        placeNode(null, newNode, null);

        setSize(1);

        return newNode;
    }

    public Node<T> addAfter(Node<T> node, T value) {
        Node<T> newNode = makeNode(value);

        Node<T> next = node.nextNode;
        placeNode(node, newNode, next);

        setSize(size + 1);

        return newNode;
    }

    public Node<T> addBefore(Node<T> node, T value) {
        Node<T> newNode = makeNode(value);

        Node<T> prev = node.prevNode;
        placeNode(prev, newNode, node);

        setSize(size + 1);

        return newNode;
    }

    /**
     * Inserts values at the tail
     *
     * @param value the value of the node to insert
     * @return the node of the value
     */
    public Node<T> addAtTail(T value) {
        if (getTail() == null) return replaceAll(value);
        else return addAfter(getTail(), value);
    }

    /**
     * Inserts values at the head
     *
     * @param value the value of the node to insert
     * @return the node of the value
     */
    public Node<T> addAtHead(T value) {
        if (getHead() == null) return replaceAll(value);
        else return addBefore(getHead(), value);
    }

    public void removeNode(@Nonnull Node<T> node) {
        if (node == getHead() && node == getTail()) {
            clear();
            return;
        }

        Node<T> prev = node.prevNode;
        Node<T> next = node.nextNode;

        node.prevNode = null;
        node.nextNode = null;

        if (node == getHead()) setHead(next);
        if (node == getTail()) setTail(prev);

        if (prev != null) prev.nextNode = next;
        if (next != null) next.prevNode = prev;

        setSize(size - 1);
    }

    @Override
    public Iterator<Node<T>> iterator() {
        return new Iterator<>() {
            Node<T> current = getHead();
            Node<T> last = null;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Node<T> next() {
                last = current;
                if (last == getTail()) current = null;
                else current = current.getNextNode();
                return last;
            }

            @Override
            public void remove() {
                LinkedList.this.removeNode(last);
            }
        };
    }

    @Override
    public void clear() {
        setHead(null);
        setTail(null);
        setSize(0);
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

    @Override
    public EStream<Node<T>> stream() {
        return EStream.of(super.stream());
    }

    @Override
    public EStream<Node<T>> parallelStream() {
        return EStream.of(super.parallelStream());
    }
}
