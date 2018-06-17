/******************************************************************************
 *  Author: Grimaldo Stanzani Junior
 *  Compilation:  javac-algs4 Deque.java
 *  Dependencies: java.util.Iterator java.util.NoSuchElementException
 *
 *  A double-ended queue, or deque. Supports adding and removing items from
 *  either the front and the back of the data structure.
 *
 ******************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

/**
 * @param <Item> the generic type of an item in this queue
 */
public class Deque<Item> implements Iterable<Item> {
    private Node<Item> front;   // beginning of queue
    private Node<Item> back;    // end of queue
    private int n;              // number of items in queue

    /**
     * Helper linked list class.
     */
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> previous;
    }

    /**
     * Initialize a empty queue.
     */
    public Deque() {
        front = null;
        back = null;
        n = 0;
    }

    /**
     * Return true if the queue is empty.
     *
     * @return {@code true} if the queue is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * Return the number of elements in the queue.
     *
     * @return the number of elements in the queue
     */
    public int size() {
        return n;
    }

    /**
     * Add a item to the beginning of the queue.
     *
     * @param item the item to add
     * @throws IllegalArgumentException if argument is null
     */
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("Null argument");

        Node<Item> newNode = new Node<>();
        newNode.item = item;
        newNode.next = front;
        newNode.previous = null;
        if (isEmpty()) back = newNode;
        else front.previous = newNode;
        front = newNode;
        n++;
    }

    /**
     * Add a item in the end of the queue.
     *
     * @param item the item to add
     * @throws IllegalArgumentException if argument is null
     */
    public void addLast(Item item){
        if (item == null) throw new IllegalArgumentException("Null argument");

        Node<Item> newNode = new Node<>();
        newNode.item = item;
        newNode.next = null;
        newNode.previous = back;
        if(isEmpty()) front = newNode;
        else back.next = newNode;
        back = newNode;
        n++;
    }

    /**
     *  Remove and return the item that is in the beginning of queue.
     *
     * @return the item that was in the beginning of queue
     * @throws NoSuchElementException if the queue is empty
     */
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Empty queue");

        Item item = front.item;
        if(front == back) {
            front = null;
            back = null;
        }
        else {
            front = front.next;
            front.previous = null;
        }
        n--;
        return item;
    }


    /**
     *  Remove and return the item that is in the end of queue.
     *
     * @return the item that was in the end of queue
     * @throws NoSuchElementException if the queue is empty
     */
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Empty queue");

        Item item = back.item;
        if(back == front) {
            back = null;
            front = null;
        }
        else {
            back = back.previous;
            back.next = null;
        }
        n--;
        return item;
    }

    /**
     * return an iterator over items in order from front to end.
     *
     * @return an iterator over items in order from front to end
     */
    public Iterator<Item> iterator() {
        return new ListIterator<Item>(front);
    }

    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }


    public static void main(String[] args) {
        Deque<Integer> a = new Deque<>();


        for (int i = 0; i < 10; i++)
            a.addLast(i);

        for (int i = 0; i < 10; i++)
            StdOut.println(a.removeLast());
    }
}
