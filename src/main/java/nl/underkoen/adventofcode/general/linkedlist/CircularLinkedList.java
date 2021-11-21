package nl.underkoen.adventofcode.general.linkedlist;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;

@Getter
@NoArgsConstructor
public class CircularLinkedList<T> extends LinkedList<T> {
    @Override
    public Node<T> getTail() {
        Node<T> head = getHead();
        if (head == null) return null;
        return getHead().prevNode;
    }

    @Override
    public Node<T> replaceAll(T value) {
        Node<T> newNode = super.replaceAll(value);

        newNode.nextNode = newNode;
        newNode.prevNode = newNode;

        return newNode;
    }

    @Override
    public Node<T> addAfter(Node<T> node, T value) {
        Node<T> newNode = super.addAfter(node, value);

        if (node == getTail()) setTail(newNode);

        return newNode;
    }

    @Override
    public Node<T> addBefore(Node<T> node, T value) {
        Node<T> newNode = super.addBefore(node, value);

        if (node == getHead()) setHead(newNode);

        return newNode;
    }

    @Override
    public void removeNode(@Nonnull Node<T> node) {
        if (node.prevNode == null || node.nextNode == null)
            throw new IllegalStateException("In a circular linked list a half node is not allowed!");

        super.removeNode(node);
    }

    /**
     * Makes a view starting from that node
     *
     * @param node the node from where to start
     * @return the view
     */
    public CircularLinkedList<T> startingAt(Node<T> node) {
        if (node == null) return new CircularLinkedList<T>();
        return new CircularLinkedListView<T>(node, this);
    }

    private static class CircularLinkedListView<T> extends CircularLinkedList<T> {
        private final CircularLinkedList<T> original;

        public CircularLinkedListView(Node<T> head, CircularLinkedList<T> original) {
            setHead(head);
            this.original = original;
        }

        @Override
        protected void setSize(int size) {
            original.setSize(size);
        }

        @Override
        public int size() {
            return original.size();
        }
    }
}
