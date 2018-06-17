/******************************************************************************
 *  Author: Grimaldo Stanzani Junior
 *  Compilation:  javac Solver.java
 *  Execution:    java Solver puzzle.txt
 *
 * A Immutable data type solver for the solution of 8-puzzle problem using
 * A* algorithm
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class Solver {
    private Node finalNode;             // final Node with solution

    /**
     * Helper class to hold each search node
     */
    private class Node implements Comparable<Node> {
        final private Board board;
        final private Node predecessor;
        final private int moves;
        final private int priority;


        public Node(Board board, Node predecessor, int moves) {
            this.board = board;
            this.predecessor = predecessor;
            this.moves = moves;

            this.priority = moves + board.manhattan();
        }

        public Iterable<Node> nextNodes() {
            Queue<Node> queue = new Queue<>();

            for(Board b : board.neighbors()) {
                if(predecessor == null)
                    queue.enqueue(new Node(b, this, moves + 1));
                else if (!b.equals(predecessor.board))
                    queue.enqueue(new Node(b, this, moves + 1));
            }
            return queue;
        }

        public Iterable<Board> path() {
            Stack<Board> stack = new Stack<>();
            Node n = this;
            while (n != null) {
                stack.push(n.board);
                n = n.predecessor;
            }

            return stack;
        }


        @Override
        public int compareTo(Node o) {
            return this.priority - o.priority;
        }
    }



    /**
     * Find a solution for the initial board using A* algorithm
     *
     * @param initial initial board to be solved
     */
    public Solver(Board initial) {
        if (initial == null) throw new java.lang.IllegalArgumentException();

        Node initialNode = new Node(initial, null, 0);
        MinPQ<Node> pq = new MinPQ<>();
        pq.insert(initialNode);

        Node twinInitialNode = new Node(initial.twin(), null, 0);
        MinPQ<Node> twinPq = new MinPQ<>();
        twinPq.insert(twinInitialNode);

        while (true) {
            Node searchNode = pq.delMin();

            if (searchNode.board.isGoal()) {
                finalNode = searchNode;
                break;
            }
            for (Node n : searchNode.nextNodes())
                    pq.insert(n);

            Node twinSearchNode = twinPq.delMin();
            if (twinSearchNode.board.isGoal()) {
                finalNode = null;
                break;
            }
            for (Node n : twinSearchNode.nextNodes())
                twinPq.insert(n);
        }
    }

    /**
     * Return true if the initial board is solvable
     *
     * @return true if the initial board is solvable
     */
    public boolean isSolvable() {
        return finalNode != null;
    }

    /**
     * Return the min number of nodes to solve initial board;
     * -1 if unsolvable
     *
     * @return the min number of nodes to solve initial board;
     * -1 if unsolvable
     */
    public int moves() {
        if(finalNode != null)
            return finalNode.moves;
        return -1;
    }

    /**
     * Sequence of boards in solution with minimum number of moves;
     * null if unsolvable
     *
     * @return
     */
    public Iterable<Board> solution() {
        if(finalNode != null)
            return finalNode.path();
        return null;
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

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
