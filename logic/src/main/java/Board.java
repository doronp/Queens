import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Board - keeps state. Array of queen as array index is column and value is row i.e. [0,1,2,3]
 * is a board with queens filling its main diagonal.
 * -1 means a column without a queen.
 */
public class Board {

    final static Logger logger = Logger.getLogger("Logger");

    private int[] queens = null;
    private int boardSize = 0;

    public void printBoard() {
        if (queens == null) return;

        StringBuilder sb = new StringBuilder();
        sb.append(System.lineSeparator());
        for (int r = 0; r < boardSize; r++) {
            for (int j = 0; j < boardSize; j++) {
                int val = r == queens[j] ? 1 : 0;
                sb.append(val).append(" ");
            }
            sb.append(System.lineSeparator());
        }
        logger.log(Level.INFO, sb.toString());
    }

    public void addQueen(int row, int col) {
        queens[col] = row;
    }

    public void removeQueen(int col) {
        queens[col] = -1;
    }

    public void init(int n) {
        boardSize = n;
        queens = new int[n];
        Arrays.fill(queens, -1);
    }

    /**
     *
     * For optimization sake we start by looking for 3 queens forming a line - Any 2 and the [row, col] position.
     * While we are at it we check for each of those 2 queens if they happen to break on of the chess rules...
     * For that - same row, same column and equal row - col for the same diagonal or row+column for the
     * same secondary diagonal.
     *
     * @param row
     * @param col
     * @param numOfColumnsToCheck - Count from the left move right numOfColumnsToCheck columns,
     *                              column by column counting the number of queens the coordinate
     *                              [row, col] offends. See RandomAlg documentation for more context.
     *
     * @return - How many queens are offended by coordinate [row, col]
     *
     */
    private int numQueensUnderAttack(int row, int col, int numOfColumnsToCheck) {
        HashSet<Integer> offendingQueens = new HashSet<>();

        //In case there is only one queen on the board
        if (numOfColumnsToCheck == 1) {
            int soleQueen = 1 - col;
            if (queens[soleQueen] == row ||
                    (row - col == queens[soleQueen] - soleQueen) ||
                    (col + row == soleQueen + queens[soleQueen])) {
                return 1;
            }
            return 0;
        }

        for (int i = 0; i < numOfColumnsToCheck - 1; ++i) {
            if (i == col) continue;

            int aRow = queens[i];
            int aCol = i;
            if (aRow == row || (row - col == aRow - aCol) || (col + row == aCol + aRow)) {
                offendingQueens.add(i);
                continue;
            }

            for (int j = i + 1; j < numOfColumnsToCheck; ++j) {
                int bRow = queens[j];
                int bCol = j;
                if (j == col) continue;

                if (bRow == row || (row - col == bRow - bCol) || (col + row == bCol + bRow)) {
                    offendingQueens.add(j);
                    continue;
                }

                //[ Ax * (By - Cy) + Bx * (Cy - Ay) + Cx * (Ay - By) ]
                if (aCol * (bRow - row) + bCol * (row - aRow) + col * (aRow - bRow) == 0) {
                    offendingQueens.add(i);
                    offendingQueens.add(j);
                }
            }
        }
        return offendingQueens.size();
    }

    public int size() {
        return boardSize;
    }

    public void initRandom(Random randGenerator) {
        queens = new int[boardSize];
        for (int i = 0; i < boardSize; i++) {
            queens[i] = randGenerator.nextInt(boardSize);
        }
    }

    public int getQueensRow(int col) {
        return queens[col];
    }

    /**
     *
     * @param row
     * @param col
     * @return true iff the coordinate [row,col] is safe (legal)
     */
    public boolean isLegal(int row, int col) {
        return numQueensUnderAttack(row, col, col) == 0;
    }

    /**
     *
     * @return true iff this board is legal under the problem constraints.
     */
    public boolean isLegal() {
        boolean res = true;

        for (int i = 0; i < boardSize; i++) {
            if (queens[i] == -1 || !isLegal(queens[i], i)) {
                res = false;
                break;
            }
        }
        return res;
    }

    public int queensUnderAttack(int row, int col) {
        return numQueensUnderAttack(row, col, boardSize);
    }

    public int queensUnderAttack(int col) {
        return numQueensUnderAttack(queens[col], col, boardSize);
    }
}