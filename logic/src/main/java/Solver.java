import java.util.logging.Logger;

public abstract class Solver {

    protected Board board = null;

    public Solver(){

    }

    public final static Logger logger = Logger.getLogger("Logger");

    public void init(Board board){
        this.board = board;
    }

    public abstract  boolean solve();

}
