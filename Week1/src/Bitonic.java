import java.util.Arrays;

public class Bitonic {

    private Bitonic(){}

    private int bitonicMax(int[] a){
        int n = a.length;
        int start = 0;
        int end = n-1;

        while(start<end){
            int m = (start+end)/2;
            if(m == start){
                if(a[start]<a[end])
                    return end;
                else
                    return start;
            }
            if(a[m] > a[m-1] && a[m]>a[m+1])
                return m;
            if(a[m] > a[m-1])
                start = m;
            else
                end = m;

        }

        return end;
    }

    public boolean search(int[] a, int key){
        int max = bitonicMax(a);

        int[] left = Arrays.copyOfRange(a,0,max);
        int[] right = Arrays.copyOfRange(a,max,a.length);

        if(Arrays.binarySearch(left,key) >= 0 || Arrays.binarySearch(right,key) >= 0 )
            return true;
        else
            return false;
    }


    static public void main(String[] args){


    }
}
