import edu.princeton.cs.algs4.*;

public class QuickUnionPCUF {

    private int[] id;

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

    public QuickUnionPCUF(int N){
        id = new int[N];
        for (int i = 0; i<N ; i++)
            id[i] = i;
    }

    public boolean connected( int p, int q){
        return root(p) == root(q);
    }

    public void union(int p, int q){
        if(root(p) == root(q))
            return;
        id[root(p)] = root(q);
    }

    public int idLength(){
        return id.length;
    }


    public static void main(String[] args){
        int N = StdIn.readInt();
        QuickUnionPCUF uf = new QuickUnionPCUF(N);

        while(!StdIn.isEmpty()){
            int p = StdIn.readInt();
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

