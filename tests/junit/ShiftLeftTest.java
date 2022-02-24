package junit;

import pass.ShiftLeft;
import junit.framework.TestCase;

public class ShiftLeftTest extends TestCase{
    private ShiftLeft shiftLeft;

    protected void setUp() throws Exception {
        super.setUp();
        shiftLeft = new ShiftLeft();
    }

    public void testCompute() {
        assertEquals(shiftLeft.shiftL(0, 42), 0);
        assertEquals(shiftLeft.shiftL(42, 1), 84);
        assertEquals(shiftLeft.shiftL(127, 3), 1016);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
