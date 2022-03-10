package junit;

import pass.PrefixDecrement;
import junit.framework.TestCase;

public class PrefixDecrementTest extends TestCase{
    private PrefixDecrement prefixDecrement;

    protected void setUp() throws Exception {
        super.setUp();
        prefixDecrement = new PrefixDecrement();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testRigtSign() {
        this.assertEquals(prefixDecrement.doPrefixDec(1, 7), 8);
        this.assertEquals(prefixDecrement.doPrefixDec(6, 7), 13);
        this.assertEquals(prefixDecrement.doPrefixDec(2, 3), 5);
    }
}
