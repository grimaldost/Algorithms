/******************************************************************************
 *  Author: Grimaldo Stanzani Junior
 *  Compilation:  javac Board.java
 *  Execution:    java Board puzzle.txt
 *
 * Immutable data Type Board
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private final int n;           // board dimension
    private final int[][] board;   // 2-dimension array representing board
    private int blankRow;          // Blank square row index
    private int blankCol;          // Blank square col index


    /**
     * Return the goal value of a position (row,col) on board
     *
     * @param row row index
     * @param col col index
     * @return goal value
     */
    private int goal(int row, int col) {
        if (row == n-1 && col == n-1)
            return 0;
        return col + row*n + 1;
    }

    /**
     * Construct a board from a n-by-n array of blocks
     *
     * @param blocks source n-by-n int array
     */
    public Board(int[][] blocks) {
        n = blocks.length;
        board = new int[n][n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                int value = blocks[i][j];
                board[i][j] = value;
                if (value == 0) {
                    blankRow = i;
                    blankCol = j;
                }
            }
    }

    /**
     * Return board dimension n
     *
     * @return board dimension n
     */
    public int dimension() {
        return n;
    }

    /**
     * Return the number of blocks in the wrong position
     *
     * @return number of blocks in the wrong position
     */
    public int hamming() {
        int count = 0;
        for (int row = 0; row < n; row++)
            for (int col = 0; col < n; col++)
                if (board[row][col] != goal(row, col) && board[row][col] != 0)
                    count++;
        return count;
    }

    /**
     * Return sum of Manhattan distances between blocks and goal
     *
     * @return sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {
        int count = 0;
        for (int row = 0; row < n; row++)
            for (int col = 0; col < n; col++)
                count += manhattan(row, col);
        return count;
    }

    private int manhattan(int row, int col) {
        int value = board[row][col];
        if (value == 0) return 0;
        int rowGoal = (value - 1)/n;
        int colGoal = (value - 1)%n;

        return abs(rowGoal - row) + abs(colGoal - col);
    }

    private int abs(int a) {
        if (a < 0) return -a;
        else return  a;
    }

    /**
     * Return if board is the goal board
     *
     * @return true if board is the goal board, otherwise false;
     */
    public boolean isGoal() {
        for (int row = 0; row < n; row++)
            for (int col = 0; col < n; col++)
                if (board[row][col] != goal(row, col))
                    return false;
        return true;
    }

    /**
     * Return a board obtained by exchanging any pair of blocks
     *
     * @return a board obtained by exchanging any pair of blocks
     */
    public Board twin() {
        int[][] twinArray = new int[n][n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                twinArray[i][j] = board[i][j];

        int iSwap;
        int jSwap;

        if (twinArray[0][0] != 0) {
            iSwap = 0;
            jSwap = 0;
        }
        else {
            iSwap = 0;
            jSwap = 1;
        }

        if (twinArray[1][0] != 0) {
            int swap = twinArray[iSwap][jSwap];
            twinArray[iSwap][jSwap] = twinArray[1][0];
            twinArray[1][0] = swap;
        }
        else {
            int swap = twinArray[iSwap][jSwap];
            twinArray[iSwap][jSwap] = twinArray[1][1];
            twinArray[1][1] = swap;
        }

        return new Board(twinArray);
    }

    /**
     * Compare this board with y and return true if they are equal
     *
     * @param y object to compare
     * @return true if this equal y
     */
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board that = (Board) y;
        if(this.n != that.n) return false;
        for (int row = 0; row < n; row++)
            for (int col = 0; col < n; col++)
                if (this.board[row][col] != that.board[row][col])
                    return false;
        return true;
    }


    /**
     * Return a iterable over all possible neighboring boards
     *
     * @return all neighboring boards
     */
    public Iterable<Board> neighbors() {
        Queue<Board> neighbors = new Queue<>();

        if (blankRow > 0)
            neighbors.enqueue(new Board(neighbor(blankRow - 1, blankCol)));
        if (blankRow < n - 1)
            neighbors.enqueue(new Board(neighbor(blankRow + 1, blankCol)));
        if (blankCol > 0)
            neighbors.enqueue(new Board(neighbor(blankRow, blankCol - 1)));
        if (blankCol < n - 1)
            neighbors.enqueue(new Board(neighbor(blankRow, blankCol + 1)));


        return neighbors;
    }

    private int[][] neighbor(int row, int col) {
        int[][] neighborArray = new int[n][n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                neighborArray[i][j] = board[i][j];

        neighborArray[blankRow][blankCol] = neighborArray[row][col];
        neighborArray[row][col] = 0;

        return neighborArray;
    }


    /**
     * Return string representation of the board
     *
     * @return string representation of the board
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }


    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        StdOut.println(initial);
        //StdOut.println(initial.twin());
        //StdOut.println(initial.isGoal());
        //StdOut.println(initial.hamming());
        //StdOut.println(initial.manhattan());

        for (Board b : initial.neighbors())
            StdOut.println(b);
    }
}
