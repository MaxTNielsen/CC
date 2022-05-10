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
        this.assertEquals(prefixDecrement.doPrefixDec(1, 7), 0);
    }
}
