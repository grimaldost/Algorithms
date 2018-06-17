import edu.princeton.cs.algs4.*;

public class WQuickUnionByHeightUF {
    private int[] id;
    private int[] height;

    private int root(int i){
        while(i != id[i])
            i = id[i];
        return i;
    }

    public WQuickUnionByHeightUF(int N){
        id = new int[N];
        height = new int[N];
        for (int i = 0; i<N ; i++) {
            id[i] = i;
            height[i] = 1;
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
        if(height[proot] == height[qroot]){
            id[qroot] = proot;
            height[proot]++;
            return;
        }
        if(height[proot] > height[qroot]){
            id[qroot] = proot;
            //height[proot] += height[qroot];
        }
        else{
            id[proot] = qroot;
            //height[qroot] += height[proot];
        }
    }

    public int idLength(){
        return id.length;
    }


    public static void main(String[] args){
        int N = StdIn.readInt();
        WQuickUnionByHeightUF uf = new WQuickUnionByHeightUF(N);

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
