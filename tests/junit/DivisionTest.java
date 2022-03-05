package junit;

import junit.framework.TestCase;
import pass.Division;
public class DivisionTest extends TestCase{
    private Division division;

    protected void setUp() throws Exception {
        super.setUp();
        division = new Division();
    }

    public void testCompute() {
        assertEquals(division.divide(0, 42), 0);
        assertEquals(division.divide(42, 1), 42);
        assertEquals(division.divide(127, 3), 42);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
