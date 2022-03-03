package junit;

import junit.framework.TestCase;
import pass.LeftSign;


public class DivisionTest extends TestCase{
    private LeftSign LeftSign;

    protected void setUp() throws Exception {
        super.setUp();
        LeftSign = new LeftSign();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testDivide() {
        this.assertEquals(LeftSign.LeftSign(4, 1), 8);
        this.assertEquals(LeftSign.LeftSign(6, 2), 24);
        this.assertEquals(LeftSign.LeftSign(2, 3), 16);
    }
}