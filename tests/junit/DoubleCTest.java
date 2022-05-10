package junit;

import pass.DoubleC;
import junit.framework.TestCase;
public class DoubleCTest extends TestCase{

    private DoubleC doubleC;

    protected void setUp() throws Exception {
        super.setUp();
        doubleC = new DoubleC();
    }

    public void testCompute() {
        assertEquals(doubleC.mul(0.0,40.0), 0.0);
        assertEquals(doubleC.tripple(40.0,0.0,2.0), 42);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
