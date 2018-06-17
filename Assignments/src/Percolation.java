/*----------------------------------------------------------------
 *  Author:        Grimaldo Stanzani Junior
 *  Written:       13/05/2018
 *
 *  Compilation:   javac Percolation.java
 *
 *  Percolation data type.
 *
 *----------------------------------------------------------------*/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int _n;                             // number of rows and columns
    private boolean[][] _sites;                 // 2D array of sites
    private WeightedQuickUnionUF _unionFind;    // data type to do and check site connections
    private WeightedQuickUnionUF _unionFindBW;  // data type to check full sites
    private int _openSites;                     // number of open sites
    private int _upperEdge;                     // 1D index of upper edge
    private int _lowerEdge;                     // 1D index of lower edge

    /**
     * Convert 2D coordinates to 1D coordinate.
     *
     * @param row row index
     * @param col column index
     * @return 1D coordinate
     */
    private int xyTo1D(int row, int col){
        return col + (row - 1)*_n;
    }

    /**
     * Check if row and column indices are valid.
     *
     * @param row row index
     * @param col column index
     */
    private void checkIndices(int row, int col){
        if (row <= 0 || row > _n)
            throw new IllegalArgumentException("row index out of bounds");
        if (col <= 0 || col > _n)
            throw new IllegalArgumentException("column index out of bounds");
    }

    /**
     * Check if site (row, col) neighbors are open and connect to each of them that it is.
     *
     * @param row row index
     * @param col column index
     */
    private void connectWithNeighbors(int row, int col){
        // Connect with virtual sites
        if (row == 1) {
            _unionFind.union(_upperEdge, xyTo1D(row, col));
            _unionFindBW.union(_upperEdge, xyTo1D(row, col));
        }
        if (row == _n)
            _unionFind.union(_lowerEdge, xyTo1D(row,col));

        // upper neighbor
        if(row > 1 && _sites[row-2][col-1]) {
            _unionFind.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            _unionFindBW.union(xyTo1D(row, col), xyTo1D(row - 1, col));
        }
        // lower neighbor
        if(row < _n && _sites[row][col-1]) {
            _unionFind.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            _unionFindBW.union(xyTo1D(row, col), xyTo1D(row + 1, col));
        }
        // left neighbor
        if(col > 1 && _sites[row-1][col-2]) {
            _unionFind.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            _unionFindBW.union(xyTo1D(row, col), xyTo1D(row, col - 1));
        }
        // lower neighbor
        if(col < _n && _sites[row-1][col]) {
            _unionFind.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            _unionFindBW.union(xyTo1D(row, col), xyTo1D(row, col + 1));
        }
    }

    /**
     * Create a n-by-n array of sites(initially all blocked).
     * A union find data type is used to do and check site connections. This object is
     * initialized with n^2+2 elements, that's represents all sites plus two virtual ones
     * for upper and lower edges. It percolates if upper eddge is connected with lower edge
     * A second union find object is used to check if a site is full, without backwash
     *
     * @param n grid size
     */
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("grid size out of bounds");

        _n = n;
        _sites = new boolean[n][n];
        _unionFind = new WeightedQuickUnionUF(n*n + 2);
        _unionFindBW = new WeightedQuickUnionUF(n*n + 1);
        _openSites = 0;
        _upperEdge = 0;
        _lowerEdge = n*n + 1;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                _sites[i][j] = false;
    }

    /**
     * Open site (row, col) if this is not open yet.
     *
     * @param row row index
     * @param col column index
     */
    public void open(int row, int col) {
        checkIndices(row, col);
        if (!isOpen(row, col)){
            _sites[row - 1][col - 1] = true;
            connectWithNeighbors(row, col);
            _openSites++;
        }
    }

    /**
     * Check if site (row, col) is open
     *
     * @param row row index
     * @param col column index
     * @return    if site is open
     */
    public boolean isOpen(int row, int col) {
        checkIndices(row, col);
        return _sites[row - 1][col - 1];
    }

    /**
     * Check if site (row, col) is connected with upper edge.
     *
     * @param row row index
     * @param col column index
     * @return if site is connected with upper edge
     */
    public boolean isFull(int row, int col) {
        checkIndices(row, col);
        return _unionFindBW.connected(_upperEdge, xyTo1D(row, col));
    }

    /**
     * Number of open sites in grid
     *
     * @return number of open sites
     */
    public int numberOfOpenSites(){
      return _openSites;
    }

    /**
     * Check if upper edge is connected with lower edge
     *
     * @return true if upper edge is connected with lower edge
     */
    public boolean percolates() {
        return _unionFind.connected(_upperEdge, _lowerEdge);
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        Percolation percolation = new Percolation(n);

        while(true) {
            int row = StdIn.readInt();
            int col = StdIn.readInt();
            percolation.open(row, col);
        }
    }
}
