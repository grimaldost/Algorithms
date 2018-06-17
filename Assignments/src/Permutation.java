/******************************************************************************
 *  Author: Grimaldo Stanzani Junior
 *  Compilation:  javac-algs4 Permutation.java
 *  Executation:  java-algs4 Permutation k < sample.txt
 *  Dependencies: StdIn StdOut
 *
 * Client that takes an integer k as a command-line and reads in a sequence of
 * strings from standard input. After then, prints exactly k of them, uniformly
 * random, each item from sequence at most once.
 *
 ******************************************************************************/
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args){
        int k = Integer.parseInt(args[0]);
        int n = 0;

        RandomizedQueue<String> queue = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            if(n < k) {
                String item = StdIn.readString();
                queue.enqueue(item);
                n++;
            }
            else if (k > 0) {
                String item = StdIn.readString();
                String possible = queue.dequeue();
                int i = StdRandom.uniform(2);
                if(i == 1)
                    queue.enqueue(item);
                else
                    queue.enqueue(possible);
            }
        }

        Iterator<String> it = queue.iterator();

        for(int i = 0; i < k; i++) {
            StdOut.println(it.next());
        }
    }
}
