import edu.princeton.cs.algs4.*;

public class WQuickUnionPCUF {

    private int[] id;
    private int[] sz;
    private int count;

    private int root(int i){
        int root = i;
        while(root != id[root])
            root = id[root];
        while(i != root){
            int j = i;
            i = id[i];
            id[j] = root;
        }
        return root;
    }

    public WQuickUnionPCUF(int N){
        id = new int[N];
        sz = new int[N];
        count = N;
        for (int i = 0; i<N ; i++) {
            id[i] = i;
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
        }
        else{
            id[proot] = qroot;
            sz[qroot] += sz[proot];
        }
        count--;
    }

    public int count(){
        return count;
    }

    public int idLength(){
        return id.length;
    }


    public static void main(String[] args){
        int N = StdIn.readInt();
        WQuickUnionPCUF uf = new WQuickUnionPCUF(N);

        int p;

        while(!StdIn.isEmpty()){
            p = StdIn.readInt();
            int q = StdIn.readInt();
            if (p > uf.idLength() || q > uf.idLength()){
                StdOut.println("Out of Bounds");
            }
            else if(!uf.connected(p,q)){
                uf.union(p,q);
                StdOut.println(p+" "+q);
            }

        }

    }
}
