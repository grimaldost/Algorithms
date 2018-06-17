public class MaxPQ <Key extends Comparable<Key>> {
    private Key[] pq;                    // store items at indices 1 to n
    private int n;                       // number of items on priority queue


    private boolean less(int a, int b) {
        return pq[a].compareTo(pq[b]) < 0;
    }

    private void exch(int a, int b) {
        Key swap = pq[a];
        pq[a] = pq[b];
        pq[b] = swap;
    }

    private void swim(int k) {
        while (k > 1 && less(k/2,k)) {
            exch(k,k/2);
            k /= 2;
        }
    }

    private void sink(int k) {
        while (2*k <= n) {
            int j = 2*k;
            if (j < n && less(j, j + 1))
                j++;
            if (!less(k,j))
                break;
            exch(k, j);
            k = j;
        }
    }

    public MaxPQ(int initCapacity) {
        pq = (Key[]) new Object[initCapacity + 1];
        n = 0;
    }

    public MaxPQ() {
        this(1);
    }

    public void insert(Key v) {
        pq[++n] = v;
        swim(n);
    }

    public Key delMax() {
        Key max = pq[1];
        exch(1,n--);
        sink(1);
        pq[n+1] = null;
        return max;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }


}
