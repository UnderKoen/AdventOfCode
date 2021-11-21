package nl.underkoen.adventofcode.general.linkedlist;

import nl.underkoen.adventofcode.general.position.Position;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

public class CircularLinkedListTest {
    private CircularLinkedList<Integer> list;

    @Before
    public void setUp() {
        list = new CircularLinkedList<>();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testReplaceAll() {
        //Arrange
        list.addAtHead(100);
        list.addAtHead(50);
        list.addAtHead(3);
        list.addAtTail(34);
        list.addAtHead(3);
        list.addAtTail(390);

        //Act
        Node<Integer> node = list.replaceAll(10000);

        //Check
        Assert.assertEquals("[10000]", list.toString());
        Assert.assertSame(node, list.getHead());
        Assert.assertSame(node, list.getTail());
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void testAddAtTail() {
        //Arrange

        //Act
        Node<Integer> head = list.addAtTail(1);
        list.addAtTail(2);
        list.addAtTail(3);
        list.addAtTail(4);
        Node<Integer> tail = list.addAtTail(5);

        //Check
        Assert.assertEquals("[1, 2, 3, 4, 5]", list.toString());
        Assert.assertSame(head, list.getHead());
        Assert.assertSame(tail, list.getTail());
        Assert.assertEquals(5, list.size());
    }

    @Test
    public void testAddAtHead() {
        //Arrange

        //Act
        Node<Integer> tail = list.addAtHead(1);
        list.addAtHead(2);
        list.addAtHead(3);
        list.addAtHead(4);
        Node<Integer> head = list.addAtHead(5);

        //Check
        Assert.assertEquals("[5, 4, 3, 2, 1]", list.toString());
        Assert.assertSame(head, list.getHead());
        Assert.assertSame(tail, list.getTail());
        Assert.assertEquals(5, list.size());
    }

    @Test
    public void testAddAt_Mix() {
        //Arrange

        //Act
        list.addAtHead(1);
        list.addAtTail(2);
        list.addAtTail(3);
        list.addAtHead(4);
        Node<Integer> tail = list.addAtTail(5);
        list.addAtHead(6);
        Node<Integer> head = list.addAtHead(7);

        //Check
        Assert.assertEquals("[7, 6, 4, 1, 2, 3, 5]", list.toString());
        Assert.assertSame(head, list.getHead());
        Assert.assertSame(tail, list.getTail());
        Assert.assertEquals(7, list.size());
    }

    @Test
    public void testAddAfter() {
        //Arrange
        list.addAtTail(1);
        list.addAtTail(2);
        Node<Integer> node = list.addAtTail(3);
        list.addAtTail(4);
        list.addAtTail(5);

        //Act
        list.addAfter(node, 6);

        //Check
        Assert.assertEquals("[1, 2, 3, 6, 4, 5]", list.toString());
        Assert.assertEquals(6, list.size());
    }

    @Test
    public void testAddAfter_Tail() {
        //Arrange
        list.addAtTail(1);
        list.addAtTail(2);
        list.addAtTail(3);
        list.addAtTail(4);
        Node<Integer> tail = list.addAtTail(5);

        //Act
        Node<Integer> node = list.addAfter(tail, 6);

        //Check
        Assert.assertEquals("[1, 2, 3, 4, 5, 6]", list.toString());
        Assert.assertSame(node, list.getTail());
        Assert.assertEquals(6, list.size());
    }

    @Test
    public void testAddBefore() {
        //Arrange
        list.addAtTail(1);
        list.addAtTail(2);
        Node<Integer> node = list.addAtTail(3);
        list.addAtTail(4);
        list.addAtTail(5);

        //Act
        list.addBefore(node, 6);

        //Check
        Assert.assertEquals("[1, 2, 6, 3, 4, 5]", list.toString());
        Assert.assertEquals(6, list.size());
    }

    @Test
    public void testAddBefore_Head() {
        //Arrange
        Node<Integer> head = list.addAtTail(1);
        list.addAtTail(2);
        list.addAtTail(3);
        list.addAtTail(4);
        list.addAtTail(5);

        //Act
        Node<Integer> node = list.addBefore(head, 6);

        //Check
        Assert.assertEquals("[6, 1, 2, 3, 4, 5]", list.toString());
        Assert.assertSame(node, list.getHead());
        Assert.assertEquals(6, list.size());
    }

    @Test
    public void testAdd_Mix() {
        //Arrange

        //Act
        list.addAtHead(1);
        Node<Integer> node2 = list.addAtTail(2);
        list.addAtTail(3);
        Node<Integer> node4 = list.addAtHead(4);
        list.addAtTail(5);
        list.addAtHead(6);
        list.addAtHead(7);
        list.addAfter(node4, 8);
        list.addBefore(node2, 9);
        Node<Integer> node10 = list.addAtHead(10);
        Node<Integer> node11 = list.addAtTail(11);
        Node<Integer> node12 = list.addAfter(node11, 12);
        Node<Integer> node13 = list.addBefore(node10, 13);

        //Check
        Assert.assertEquals("[13, 10, 7, 6, 4, 8, 1, 9, 2, 3, 5, 11, 12]", list.toString());
        Assert.assertSame(node13, list.getHead());
        Assert.assertSame(node12, list.getTail());
        Assert.assertEquals(13, list.size());
    }

    @Test
    public void testRemove() {
        //Arrange
        list.addAtTail(1);
        list.addAtTail(2);
        Node<Integer> node = list.addAtTail(3);
        list.addAtTail(4);
        list.addAtTail(5);

        //Act
        list.removeNode(node);

        //Check
        Assert.assertEquals("[1, 2, 4, 5]", list.toString());
        Assert.assertEquals(4, list.size());
    }

    @Test
    public void testRemove_Head() {
        //Arrange
        Node<Integer> node = list.addAtTail(1);
        Node<Integer> head = list.addAtTail(2);
        list.addAtTail(3);
        list.addAtTail(4);
        list.addAtTail(5);

        //Act
        list.removeNode(node);

        //Check
        Assert.assertEquals("[2, 3, 4, 5]", list.toString());
        Assert.assertSame(head, list.getHead());
        Assert.assertEquals(4, list.size());
    }

    @Test
    public void testRemove_Tail() {
        //Arrange
        list.addAtTail(1);
        list.addAtTail(2);
        list.addAtTail(3);
        Node<Integer> tail = list.addAtTail(4);
        Node<Integer> node = list.addAtTail(5);

        //Act
        list.removeNode(node);

        //Check
        Assert.assertEquals("[1, 2, 3, 4]", list.toString());
        Assert.assertSame(tail, list.getTail());
        Assert.assertEquals(4, list.size());
    }

    @Test
    public void testRemove_Last() {
        //Arrange
        Node<Integer> node = list.addAtTail(1);

        //Act
        list.removeNode(node);

        //Check
        Assert.assertEquals("[]", list.toString());
        Assert.assertNull(list.getHead());
        Assert.assertNull(list.getTail());
        Assert.assertEquals(0, list.size());
    }

    @Test
    public void testRemove_SecondToLast() {
        //Arrange
        Node<Integer> last = list.addAtTail(1);
        Node<Integer> node = list.addAtTail(2);

        //Act
        list.removeNode(node);

        //Check
        Assert.assertEquals("[1]", list.toString());
        Assert.assertSame(last, list.getHead());
        Assert.assertSame(last, list.getTail());
        Assert.assertEquals(1, list.size());
    }

    @Test(expected = IllegalStateException.class)
    public void testRemove_FullIllegal() {
        //Arrange
        list.addAtTail(1);
        list.addAtTail(2);

        Node<Integer> node = new Node<>(1);

        //Act
        list.removeNode(node);

        //Check
    }

    @Test(expected = IllegalStateException.class)
    public void testRemove_HalfIllegal() {
        //Arrange
        list.addAtTail(1);
        list.addAtTail(2);

        Node<Integer> node = new Node<>(1);
        node.prevNode = node;

        //Act
        list.removeNode(node);

        //Check
    }

    @Test
    public void testMix() {
        //Arrange

        //Act
        list.addAtHead(1);
        Node<Integer> node2 = list.addAtTail(2);
        list.addAtTail(3);
        Node<Integer> node4 = list.addAtHead(4);
        list.addAtTail(5);
        list.addAtHead(6);
        Node<Integer> node7 = list.addAtHead(7);
        list.addAfter(node4, 8);
        list.addBefore(node2, 9);
        list.removeNode(node7);
        Node<Integer> node10 = list.addAtHead(10);
        Node<Integer> node11 = list.addAtTail(11);
        Node<Integer> node12 = list.addAfter(node11, 12);
        Node<Integer> node13 = list.addBefore(node10, 13);
        list.removeNode(node10);

        //Check
        Assert.assertEquals("[13, 6, 4, 8, 1, 9, 2, 3, 5, 11, 12]", list.toString());
        Assert.assertSame(node13, list.getHead());
        Assert.assertSame(node12, list.getTail());
        Assert.assertEquals(11, list.size());
    }

    /**
     * Doesn't test modifications of the view because of the implemantation note of {@link nl.underkoen.adventofcode.general.linkedlist.CircularLinkedList#startingAt(Node)}
     */
    @Test
    public void testStartingAt() {
        //Arrange
        list.addAtTail(1);
        list.addAtTail(2);
        Node<Integer> node = list.addAtTail(3);
        list.addAtTail(4);
        list.addAtTail(5);

        //Act
        list = list.startingAt(node);

        //Check
        Assert.assertEquals("[3, 4, 5, 1, 2]", list.toString());
    }

    @Test
    public void testStartingAt_Null() {
        //Arrange

        //Act
        list = list.startingAt(null);

        //Check
        Assert.assertEquals("[]", list.toString());
    }

    @Test
    public void testIterator() {
        //Arrange
        Node<Integer> node1 = list.addAtTail(1);
        Node<Integer> node2 = list.addAtTail(2);
        Node<Integer> node3 = list.addAtTail(3);
        Node<Integer> node4 = list.addAtTail(4);
        Node<Integer> node5 = list.addAtTail(5);

        //Act
        Iterator<Node<Integer>> iterator = list.iterator();

        //Check
        Assert.assertTrue(iterator.hasNext());
        Assert.assertSame(node1, iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertSame(node2, iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertSame(node3, iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertSame(node4, iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertSame(node5, iterator.next());
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void testIteratorRemove() {
        //Arrange
        list.addAtTail(1);
        list.addAtTail(2);
        list.addAtTail(3);
        list.addAtTail(4);
        Node<Integer> node5 = list.addAtTail(5);

        //Act
        Iterator<Node<Integer>> iterator = list.iterator();

        iterator.next();
        iterator.next();
        iterator.remove();
        iterator.next();
        iterator.next();
        iterator.remove();

        //Check
        Assert.assertSame(node5, iterator.next());
        Assert.assertFalse(iterator.hasNext());
        Assert.assertEquals("[1, 3, 5]", list.toString());
        Assert.assertEquals(3, list.size());
    }

    @Test
    public void testToString() {
        //Arrange
        list.addAtTail(1);
        list.addAtTail(2);
        list.addAtTail(3);
        list.addAtTail(4);
        list.addAtTail(5);

        //Act
        String str = list.toString();

        //Check
        Assert.assertEquals("[1, 2, 3, 4, 5]", str);
    }

    @Test
    public void testToString_Single() {
        //Arrange
        list.addAtTail(1);

        //Act
        String str = list.toString();

        //Check
        Assert.assertEquals("[1]", str);
    }

    @Test
    public void testToString_Empty() {
        //Arrange

        //Act
        String str = list.toString();

        //Check
        Assert.assertEquals("[]", str);
    }

    @Test
    public void testToString_Complex() {
        //Arrange
        CircularLinkedList<Position> complexList = new CircularLinkedList<>();
        complexList.addAtHead(new Position(0, 0));

        //Act
        String str = complexList.toString();

        //Check
        Assert.assertEquals("[[0, 0]]", str);
    }

    @Test
    public void testGetHead() {
        //Arrange
        Node<Integer> head = list.addAtTail(1);
        list.addAtTail(2);
        list.addAtTail(3);
        list.addAtTail(4);
        list.addAtTail(5);

        //Act
        Node<Integer> node = list.getHead();

        //Check
        Assert.assertSame(head, node);
    }

    @Test
    public void testGetHead_Empty() {
        //Arrange

        //Act
        Node<Integer> node = list.getHead();

        //Check
        Assert.assertNull(node);
    }

    @Test
    public void testGetHead_Single() {
        //Arrange
        Node<Integer> head = list.addAtTail(1);

        //Act
        Node<Integer> node = list.getHead();

        //Check
        Assert.assertSame(head, node);
    }

    @Test
    public void testGetTail() {
        //Arrange
        list.addAtTail(1);
        list.addAtTail(2);
        list.addAtTail(3);
        list.addAtTail(4);
        Node<Integer> tail = list.addAtTail(5);

        //Act
        Node<Integer> node = list.getTail();

        //Check
        Assert.assertSame(tail, node);
    }

    @Test
    public void testGetTail_Empty() {
        //Arrange

        //Act
        Node<Integer> node = list.getTail();

        //Check
        Assert.assertNull(node);
    }

    @Test
    public void testGetTail_Single() {
        //Arrange
        Node<Integer> tail = list.addAtTail(1);

        //Act
        Node<Integer> node = list.getTail();

        //Check
        Assert.assertSame(tail, node);
    }

    @Test
    public void testSize() {
        //Arrange
        list.addAtTail(1);
        list.addAtTail(2);
        list.addAtTail(3);
        list.addAtTail(4);
        list.addAtTail(5);

        //Act
        int size = list.size();

        //Check
        Assert.assertEquals(5, size);
    }

    @Test
    public void testSize_Single() {
        //Arrange
        list.addAtTail(1);

        //Act
        int size = list.size();

        //Check
        Assert.assertEquals(1, size);
    }

    @Test
    public void testSize_Empty() {
        //Arrange

        //Act
        int size = list.size();

        //Check
        Assert.assertEquals(0, size);
    }

    @Test
    public void testClear() {
        //Arrange
        list.addAtTail(1);
        list.addAtTail(2);
        list.addAtTail(3);
        list.addAtTail(4);
        list.addAtTail(5);

        //Act
        list.clear();

        //Check
        //noinspection ConstantConditions
        Assert.assertEquals(0, list.size());
        Assert.assertNull(list.getHead());
        Assert.assertNull(list.getTail());
        Assert.assertEquals("[]", list.toString());
    }

    @Test
    public void testClear_Single() {
        //Arrange
        list.addAtTail(1);

        //Act
        list.clear();

        //Check
        //noinspection ConstantConditions
        Assert.assertEquals(0, list.size());
        Assert.assertNull(list.getHead());
        Assert.assertNull(list.getTail());
        Assert.assertEquals("[]", list.toString());
    }

    @Test
    public void testClear_Empty() {
        //Arrange

        //Act
        list.clear();

        //Check
        //noinspection ConstantConditions
        Assert.assertEquals(0, list.size());
        Assert.assertNull(list.getHead());
        Assert.assertNull(list.getTail());
        Assert.assertEquals("[]", list.toString());
    }
}