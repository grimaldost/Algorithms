import edu.princeton.cs.algs4.*;

public class ErdosRenyi {


    public static int Count(int N){
        int  conCount = 0;
        WQuickUnionPCUF uf = new WQuickUnionPCUF(N);

        while(uf.count() > 1){
            int i = StdRandom.uniform(N);
            int j = StdRandom.uniform(N);
            uf.union(i,j);
            conCount++;
        }

        return conCount;
    }

    public static void main(String[] args){
        int N = StdIn.readInt();
        int trials = StdIn.readInt();
        int[] edges = new int[trials];

        for (int i = 0; i < trials; i++){
            edges[i] = Count(N);
        }

        StdOut.println("1/2 n ln n = " + 0.5 * N * Math.log(N));
        StdOut.println("mean       = " + StdStats.mean(edges));
        StdOut.println("stddev     = " + StdStats.stddev(edges));
    }
}
