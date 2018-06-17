import edu.princeton.cs.algs4.*;

public class WQuickUnionUF {

    private int[] id;
    private int[] sz;
    private int[] largest;

    private int root(int i){
        while(i != id[i])
            i = id[i];
        return i;
    }

    public WQuickUnionUF(int N){
        id = new int[N];
        sz = new int[N];
        largest = new int[N];
        for (int i = 0; i<N ; i++) {
            id[i] = i;
            largest[i] = i;
            sz[i] = 1;
        }
    }

    public boolean connected( int p, int q){
        return root(p) == root(q);
    }

    public void union(int p, int q){
        int proot = root(p);
        int qroot = root(q);

        if(proot == qroot)
            return;
        if(sz[proot] > sz[qroot]){
            id[qroot] = proot;
            sz[proot] += sz[qroot];
            if(largest[proot] < largest[qroot]){
                largest[proot] = largest[qroot];
            }
        }
        else{
            id[proot] = qroot;
            sz[qroot] += sz[proot];
            if(largest[qroot] < largest[proot]){
                largest[qroot] = largest[proot];
            }
        }
    }

    public int find(int i){
        return largest[root(i)];
    }

    public int idLength(){
        return id.length;
    }


    public static void main(String[] args){
        int N = StdIn.readInt();
        WQuickUnionUF uf = new WQuickUnionUF(N);

        while(!StdIn.isEmpty()){
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (p > uf.idLength() || q > uf.idLength()){
                StdOut.println("Out of Bounds");
            }
            else if(p == -1){
                StdOut.println(uf.find(q));
            }
            else if(!uf.connected(p,q)){
                uf.union(p,q);
                StdOut.println(p+" "+q);
            }

        }

    }
}
