package junit;
import junit.framework.TestCase;
import pass.ShiftUnsignedRight;

public class ShiftUnsignedRightTest extends TestCase{
    private ShiftUnsignedRight shiftUnsignedRight;

    protected void setUp() throws Exception {
        super.setUp();
        shiftUnsignedRight = new ShiftUnsignedRight();
    }

    public void testCompute() {
        assertEquals(shiftUnsignedRight .shiftUR(0, 42), 0);
        assertEquals(shiftUnsignedRight .shiftUR(42, 1), 21);
        assertEquals(shiftUnsignedRight .shiftUR(127, 3), 15);
        assertEquals(shiftUnsignedRight .shiftUR(-9, 2), 1073741821);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
