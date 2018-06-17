import edu.princeton.cs.algs4.*;
import java.util.Arrays;


public class ThreeSum {

    private ThreeSum(){}

    public static int count(int[] a){
        int n = a.length;
        int count = 0;

        for (int i = 0;i < n; i++)
            for (int j = i+1;j < n;j++)
                for (int k = j+1;k < n;k++)
                    if(a[i] + a[j] + a[k] == 0)
                        count++;

        return count;
    }

    public static int countFast(int[] a){
        Arrays.sort(a);
        int n = a.length;
        int count = 0;

        for(int i = 0;i < n;i++){
            int start = i+1;
            int end = n-1;
            while(start < end) {
                if (a[i] + a[start] + a[end] == 0) {
                    count++;
                    if(a[start] == a[start+1])
                        start += 1;
                    else
                        end -= 1;
                } else if (a[i] + a[start] + a[end] > 0) {
                    end -= 1;
                } else {
                    start += 1;
                }
            }
        }

        return count;
    }



    static public void main(String[] args){
        int[] a = {-1,2,3,-2,-4,-3,0,5};

        int v = countFast(a);

        StdOut.println(v);
    }
}
