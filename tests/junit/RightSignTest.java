package junit;

import junit.framework.TestCase;
import pass.RightSign;


public class RightSignTest extends TestCase{

    private RightSign RightSign;

    protected void setUp() throws Exception {
        super.setUp();
        RightSign = new RightSign();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testDivide() {
        RightSignTest.assertEquals(RightSign.doRightSign(4, 1), 2);
    }
}