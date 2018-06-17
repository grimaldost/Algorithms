/*----------------------------------------------------------------
 *  Author:        Grimaldo Stanzani Junior
 *  Written:       13/05/2018
 *
 *  Compilation:   javac PercolationStats.java
 *  Execution:     java PercolationStats n trials
 *
 *  Estimates probability p* value, that represents the threshold
 *  value such that when p < p* a random n-by-n grid almost never
 *  percolates, and when p > p*, a random n-byn grid almost always
 *  percolates, where p is site vacancy probability.
 *
 *----------------------------------------------------------------*/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import java.lang.Math;

public class PercolationStats {
    private int _trials;          // number of independent computational experiments
    private double _mean;         // mean of threshold probability values
    private double _stdDeviation; // mean of threshold probability values

    /**
     *  Perform trails independent computational experiments on a n-by-n
     *  grid, and calculate the fraction of open sites prob[i].
     *  Also Calculate the mean and standard deviation;
     *
     * @param n      grid size
     * @param trials number of independent computational experiments
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0)
            throw new IllegalArgumentException("n out of bounds");
        if (trials <= 0)
            throw new IllegalArgumentException("trials out of bounds");

        double[] prob = new double[trials];
        _trials = trials;
        for (int i = 0; i < trials; i++){
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1,n+1);
                int col = StdRandom.uniform(1,n+1);
                percolation.open(row,col);
            }
            prob[i] = (double)percolation.numberOfOpenSites()/(n*n);
        }
        _mean = StdStats.mean(prob);
        _stdDeviation = StdStats.stddev(prob);
    }

    /**
     * Return the mean of percolation threshold
     *
     * @return mean of percolation threshold
     */
    public double mean() {
        return _mean;
    }

    /**
     * Return the standard deviation of percolation threshold
     *
     * @return standard deviation of threshold
     */
    public double stddev() {
        return _stdDeviation;
    }

    /**
     * Return the low endpoint of 95% confidence interval
     *
     * @return low endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return _mean - 1.96*_stdDeviation/(Math.sqrt((double)_trials));
    }

    /**
     * Return the high endpoint of 95% confidence interval
     *
     * @return high endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return _mean + 1.96*_stdDeviation/(Math.sqrt((double)_trials));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats perStats = new PercolationStats(n, trials);

        StdOut.println("mean                    = " + perStats.mean());
        StdOut.println("stddev                  = " + perStats.stddev());
        StdOut.println("95% confidence interval = [" + perStats.confidenceLo() + ", "
                + perStats.confidenceHi() + "]");
    }
}
