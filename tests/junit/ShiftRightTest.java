package junit;
import pass.ShiftRight;
import junit.framework.TestCase;
public class ShiftRightTest extends TestCase{
    private ShiftRight shiftRight;

    protected void setUp() throws Exception {
        super.setUp();
        shiftRight = new ShiftRight();
    }

    public void testCompute() {
        assertEquals(shiftRight.shiftR(0, 42), 0);
        assertEquals(shiftRight.shiftR(42, 1), 21);
        assertEquals(shiftRight.shiftR(127, 3), 15);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
