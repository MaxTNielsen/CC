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
        this.assertEquals(RightSign.RightSign(4, 1), 2);
        this.assertEquals(RightSign.RightSign(6, 2), 1);
        this.assertEquals(RightSign.RightSign(2, 3), 0);
    }}