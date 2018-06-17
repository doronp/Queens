import java.util.logging.Level;

/**
 * Simple brute force algorithm which places a queen on a free row column by column while verifying it
 * satisfies the constraints each time. If it gets stuck (reach to a column where it cannot find a proper row)
 * it will backtrack.
 *
 *
 */
public class BruteForceAlg extends Solver {

    public boolean solve(){
        if (board == null) {
            logger.log(Level.INFO, "Call init() first..");
            return false;
        }
        if (solver(0)) {
            return true;
        } else {
            logger.log(Level.INFO, "No solutions...");
            return false;
        }
    }


    private boolean solver(int col) {
        if (col == board.size()) {
            return true;
        }

        for (int row = 0; row < board.size(); row++) {
            if (board.isLegal(row, col)) {
                board.addQueen(row, col);
                if (solver(col + 1)) return true;
                board.removeQueen(col);
            }
        }
        return false;
    }
}
