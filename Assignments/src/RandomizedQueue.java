/******************************************************************************
 *  Author: Grimaldo Stanzani Junior
 *  Compilation:  javac-algs4 RandomizedQueue.java
 *  Dependencies: java.util.Iterator java.util.NoSuchElementException
 *                edu.princeton.cs.algs4.StdRandom
 *
 * Data type that support enqueue and dequeue items, with removed items been
 * chosen uniformly at random from item in the data structure.
 *
 ******************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * @param <Item> the generic type of an item in this queue
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;   // array of items
    private int n;      // number of items on queue

    // resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= n;

        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    /**
     * Construct a empty randomized queue
     */
    public RandomizedQueue() {
        a = (Item[]) new Object[1];
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
     * Return the number of items in queue.
     *
     * @return the number of items in queue
     */
    public int size() {
        return n;
    }

    /**
     * Add a item to the queue.
     *
     * @param item the item to add
     * @throws IllegalArgumentException if argument is null
     */
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Null argument");

        if (n == a.length) resize(2*a.length);
        a[n++] = item;
    }

    /**
     * Remove and return a random item.
     *
     * @return a random item from the queue
     * @throws NoSuchElementException if the queue is empty
     */
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Empty queue");

        int rdm = StdRandom.uniform(n);
        Item item = a[rdm];
        a[rdm] = a[n-1];
        a[n-1] = null;
        n--;

        if (n > 0 && n == a.length/4) resize(a.length/2);
        return item;
    }

    /**
     * Return a random item, without removing it.
     *
     * @return a random item from the queue
     * @throws NoSuchElementException if the queue is empty
     */
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Empty queue");

        int rdm = StdRandom.uniform(n);
        return a[rdm];
    }

    /**
     * Return an independent iterator over items in random order
     *
     * @return an independent iterator over items in random order
     */
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int[] rdn;
        private int pos;

        public ArrayIterator() {
            rdn = new int[n];
            pos = 0;
            for(int i = 0; i < n; i++)
                rdn[i] = i;
            StdRandom.shuffle(rdn);
        }

        public boolean hasNext()  { return pos < n;                             }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = a[rdn[pos]];
            pos++;
            return item;
        }
    }


    public static void main(String[] args ){
        RandomizedQueue<Integer> a = new RandomizedQueue<>();

        for (int i = 0; i < 10; i++)
            a.enqueue(i);

        for (int j : a) {
            StdOut.println("----"+j);
            for (int k : a)
                StdOut.println(k);
        }
    }
}
