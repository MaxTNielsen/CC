package junit;

import junit.framework.TestCase;
import pass.DoubleC;
public class DoubleTest extends TestCase{
    private DoubleC doubleTest;

    protected void setUp() throws Exception {
        super.setUp();
        doubleTest = new DoubleC();
    }

    public void testCompute() {
        assertEquals(doubleTest.tripple(2.0, 2.3, 0.2), 4.5 );

    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
