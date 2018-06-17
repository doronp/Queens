import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BruteForceAlgTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void solve1() {
        Board b = new Board();
        b.init(4);
        Solver s = new BruteForceAlg();
        s.init(b);
        assertTrue(s.solve());

    }

    @Test
    public void solve2() {
        Board b = new Board();
        b.init(2);
        Solver s = new BruteForceAlg();
        s.init(b);
        assertFalse(s.solve());
    }

}
