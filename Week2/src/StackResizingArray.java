import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class StackResizingArray<Item> implements Iterable<Item>{
    private Item[] items;
    private int n = 0;

    private void resize(int capacity) {
        Item[] newItems = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++)
            newItems[i] = items[i];
        items = newItems;
    }

    public StackResizingArray() {
        items = (Item[]) new Object[2];
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public void push(Item item) {
        if (n == items.length) resize(2*items.length);
        items[n++] = item;
    }

    public Item pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = items[--n];
        items[n] = null;
        if (n > 0 && n == items.length/4) resize(items.length/2);
        return item;
    }

    public int size() {
        return n;
    }

    public Iterator<Item> iterator() {
        return new StackResizingArray.ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private int i = n;
        public boolean hasNext() { return i > 0; }
        public void remove() { /* not supported */ }
        public Item next() {
            Item item = items[--i];
            return  item;
        }
    }


    public static void main(String[] args) {
        StackResizingArray<String> stackLinkedList = new StackResizingArray<>();
        while (!StdIn.isEmpty())
        {
            String s = StdIn.readString();
            if (s.equals("-")) StdOut.print(stackLinkedList.pop());
            else stackLinkedList.push(s);
        }
    }
}
