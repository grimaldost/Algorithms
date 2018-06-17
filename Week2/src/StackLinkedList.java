import edu.princeton.cs.algs4.StdIn;
import  edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class StackLinkedList<Item> implements Iterable<Item> {
    private Node first = null;
    private int numberOfNodes = 0;

    private class Node {
        Item item;
        Node next;
    }

    public void push(Item item) {
        Node newNode = new Node();
        newNode.item = item;
        newNode.next = first;
        first = newNode;
        numberOfNodes++;
    }

    public Item pop() {
        Item s = first.item;
        first = first.next;
        numberOfNodes--;
        return s;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size(){
        return numberOfNodes;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
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


    public static void main(String[] args) {
        StackLinkedList<String> stackLinkedList = new StackLinkedList<>();
        while (!StdIn.isEmpty())
        {
            String s = StdIn.readString();
            if (s.equals("-")) StdOut.print(stackLinkedList.pop());
            else stackLinkedList.push(s);
        }
    }
}
