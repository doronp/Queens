import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RandomAlgTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void setAlgParams() {
    }

    @Test
    public void solve() {
        Solver s = new RandomAlg();
        assertFalse(s.solve());
    }
}