import java.util.*;
import java.util.logging.Level;


/**
 *    This Algorithm will initialize a random board by putting one queen on each column.
 *    It will minimize "the trouble" each queen inflicts:
 *    If we minimize for queen A then if queen A is in the same row with queen B and C we count 2.
 *    If queen A is on the Same diagonal with queen D we count 1.
 *    For the 3 one one line constraint - each queen involved with A in creating a line of 3 is count ONCE (using Set DS)
 *    So if A creates a line with 2 others we count 2 and if it creates a line with 3 others we count 3.
 *
 *    To get proper complexity analysis we should look at the expected number of:
 *    [moves * shuffles * move].
 *
 *    I could either run for a large number of time and by the Law of big number get the right complexity
 *
 *    OR
 *
 *    Calculate the probability to randomly select legal board for size n and then calculate
 *    the expected runtime of the algorithm.
 *
 *
 */

public class RandomAlg extends Solver {
    private int maxShufflesNum = Integer.MAX_VALUE;
    private int maxMovesFactor = 50;

    private Random rand = new Random(Double.doubleToLongBits(Math.random()));

    /**
     *
     * @param maxShufflesNum - After how many shuffles to stop.
     * @param maxMovesFactor - When to call it and move to the next shuffle.
     */
    public void setAlgParams(int maxShufflesNum, int maxMovesFactor) {
        this.maxMovesFactor = maxMovesFactor;
        this.maxShufflesNum = maxShufflesNum;
    }

    public boolean solve() {
        if (board == null) {
            logger.log(Level.INFO, "Call init() first..");
            return false;
        }
        boolean solved = false;
        int numOfShuffles = 0;
        board.initRandom(rand);
        while (!solved && numOfShuffles < maxShufflesNum) {
            solved = solveForNQueensRandom();
            if (!solved) board.initRandom(rand);
            numOfShuffles++;
        }

        logger.log(Level.INFO, "Solved after " + numOfShuffles + " shuffles of "+maxMovesFactor * board.size()+
                " steps at most each.");
        return solved;
    }

    private int minimizeOnCol(int col) {
        int bestRow = board.getQueensRow(col);
        board.removeQueen(col);
        int minAttacks = board.size();
        for (int r = 0; r < board.size(); r++) {
            int currRowQueensUnderAttack = board.queensUnderAttack(r, col);
            if (currRowQueensUnderAttack < minAttacks) {
                minAttacks = currRowQueensUnderAttack;
                bestRow = r;
            }
        }
        board.addQueen(bestRow, col);
        return minAttacks;
    }

    private boolean solveForNQueensRandom() {
        int moves = 0;
        int movesLimit = maxMovesFactor * board.size();
        boolean solved = false;
        while (!solved && (moves < movesLimit)) {
            moves++;
            int candidate = rand.nextInt(board.size());
            int offences = board.queensUnderAttack(candidate);

            if (offences > 0) offences = minimizeOnCol(candidate);
            if (offences == 0 && board.isLegal()) solved = true;
        }

        return board.isLegal();
    }
}