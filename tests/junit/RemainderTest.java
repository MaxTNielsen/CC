package junit;

import junit.framework.TestCase;
import pass.Remainder;

public class RemainderTest extends TestCase{
    private Remainder modulus;

    protected void setUp() throws Exception {
        super.setUp();
        modulus = new Remainder();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testDivide() {
        this.assertEquals(modulus.mod(20, 5), 0);
        this.assertEquals(modulus.mod(42, 1), 42);
        this.assertEquals(modulus.mod(7, 3), 1);
    }
}