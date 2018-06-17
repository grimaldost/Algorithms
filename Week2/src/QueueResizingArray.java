import java.util.Iterator;

public class QueueResizingArray<Item> implements Iterable<Item>{
    private Item[] items;
    private int n = 0;
    private int head = 0;
    private int tail = 0;

    private void resize(int capacity) {
        Item[] newItems = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++)
            newItems[i] = items[(head + i)%items.length];
        items = newItems;
        head = 0;
        tail = n;
    }

    public QueueResizingArray() {
        items = (Item[]) new Object[1];
    }

    public boolean isEmpty()
    { return n == 0; }

    public void enqueue(Item item) {
        if (n == items.length) resize(2*items.length);
        items[tail++] = item;
        if(tail == items.length)
            tail = 0;
        n++;
    }

    public Item dequeue() {
        Item item = items[head++];
        items[head-1] = null;
        if(head == items.length)
            head = 0;
        n--;
        if (n > 0 && n == items.length/4) resize(items.length/2);
        return item;
    }

    public Iterator<Item> iterator() {
        return new QueueResizingArray.ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private int i = 0;
        public boolean hasNext() { return i < n; }
        public void remove() { /* not supported */ }
        public Item next() {
            Item item = items[(head + i)%items.length];
            i++;
            return item;
        }
    }
}
