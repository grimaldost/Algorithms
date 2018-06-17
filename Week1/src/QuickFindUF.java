import edu.princeton.cs.algs4.*;

public class QuickFindUF {
    private int[] id;

    public QuickFindUF(int N){
        id = new int[N];
        for (int i = 0; i<N ; i++)
            id[i] = i;
    }

    public boolean connected( int p, int q){
        return id[p] == id[q];
    }

    public void union(int p, int q){
        int pId = id[p];
        int qId = id[q];

        for(int i = 0; i < id.length; i++)
            if(id[i] == pId) id[i] = qId;

    }

    public int idLength(){
        return id.length;
    }


    public static void main(String[] args){
        int N = StdIn.readInt();
        QuickFindUF uf = new QuickFindUF(N);

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
