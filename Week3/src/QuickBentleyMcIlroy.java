public class QuickBentleyMcIlroy {

    private static boolean less(Comparable v, Comparable w) {
        if (v == w) return false;
        return v.compareTo(w) < 0;
    }

    private static boolean eq(Comparable v, Comparable w) {
        if (v == w) return true;
        return v.compareTo(w) == 0;
    }

    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    public static void sort(Comparable[] a) {
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int i = lo, j = hi+1;
        int p = lo, q = hi+1;
        Comparable v = a[lo];
        while (true) {
            while(less(a[++i],v))
                if(i == hi) break;
            while(less(v,a[--j]))
                if(j == lo) break;

            if (i == j && eq(a[i], v))
                exch(a, ++p, i);
            if (i >= j)
                break;

            exch(a,i,j);
            if(eq(v, a[i]))
                exch(a,p++,i);
            if(eq(v,a[j]))
                exch(a,q--,j);
        }

        i = j + 1;
        for (int k = lo; k <= p; k++)
            exch(a, k, j--);
        for (int k = hi; k >= q; k--)
            exch(a, k, i++);

        sort(a, lo, j);
        sort(a, i, hi);
    }
}
