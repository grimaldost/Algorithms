import edu.princeton.cs.algs4.StdOut;

public class QuickBest {

    private static void exch(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    private static void Best(int[] a,int lo,int hi) {
        if(hi <= lo)
            return;
        int mid = (lo + hi)/2;
        Best(a,lo,mid-1);
        Best(a,mid + 1,hi);
        exch(a,lo,mid);

    }


    public static int[] Best(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++)
            a[i] = i;
        Best(a, 0, n-1);
        return a;
    }


    public static void main(String[] args) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        int n = 15; //Integer.parseInt(args[0]);
        int[] a = Best(n);


        for(int i = 0; i< n; i++){
            StdOut.print(alphabet.charAt(a[i]));
        }
    }

}
