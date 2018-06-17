import java.util.Iterator;

public class QueueLinkedList <Item> implements Iterable<Item> {
        private Node first = null;
        private Node last = null;
        private int numberOfNodes = 0;

        private class Node {
            Item item;
            QueueLinkedList.Node next;
        }

        public void enqueue(Item item) {
            Node oldLast = last;
            last = new Node();
            last.item = item;
            last.next = null;
            if (isEmpty()) first = last;
            else oldLast.next = last;
        }

        public Item dequeue() {
            Item s = first.item;
            first = first.next;
            numberOfNodes--;
            if (isEmpty()) last = null;
            return s;
        }

        public boolean isEmpty() {
            return first == null;
        }

        public int size(){
            return numberOfNodes;
        }

        public Iterator<Item> iterator() {
            return new QueueLinkedList.ListIterator();
        }

        private class ListIterator implements Iterator<Item> {
            private Node current = first;
            public boolean hasNext() { return current != null; }
            public void remove() { /* not supported */ }
            public Item next() {
                Item item = current.item;
                current = current.next;
                return  item;
            }
        }
}
