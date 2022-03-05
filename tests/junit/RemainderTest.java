package junit;

import junit.framework.TestCase;
import pass.Remainder;
public class RemainderTest extends TestCase{
    private Remainder remainder;

    protected void setUp() throws Exception {
        super.setUp();
        remainder = new Remainder();
    }

    public void testCompute() {
        assertEquals(remainder.modulo(127, 3), 1);
        assertEquals(remainder.modulo(42, 1), 0);
        assertEquals(remainder.modulo(0, 42), 0);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
