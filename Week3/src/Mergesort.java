public class Mergesort {

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static boolean isSorted(Comparable[] a, int init, int end) {
        for (int i = init + 1; i < end; i++)
            if(less(a[i],a[i-1]))
                return false;
        return true;
    }

    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        assert isSorted(a, lo, mid);
        assert isSorted(a, mid+1, hi);

        for (int k = lo; k <= hi; k++)
            aux[k] = a[k];


        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if(i > mid) a[k] = aux[j++];
            else if(j > hi) a[k] = aux[i++];
            else if(less(aux[j],aux[i])) a[k] = aux[j++];
            else a[k] = aux[i++];
        }

        assert isSorted(a, lo, hi);
    }

    private static void fasterMerge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {

        for (int i = lo; i <= mid; i++)
            aux[i] = a[i];

        for (int j = mid+1; j <= hi; j++)
            aux[j] = a[hi-j+mid+1];

        int i = lo, j = hi;
        for (int k = lo; k <= hi; k++)
            if (less(aux[j], aux[i])) a[k] = aux[j--];
            else                      a[k] = aux[i++];
    }

    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {

        if(lo >= hi)
            return;
        int mid = (lo + hi)/2;
        sort(a,aux,lo,mid);
        sort(a,aux,mid+1,hi);
        if (!less(a[mid+1], a[mid])) return;
        merge(a,aux,lo,mid,hi);

    }

    public static void sort(Comparable[] a)
    {
        Comparable[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length - 1);
    }



}
