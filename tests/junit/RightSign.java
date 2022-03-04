package junit;

import junit.framework.TestCase;
import pass.RigtSign;


public class DivisionTest extends TestCase{
    private RightSign RightSign;

    protected void setUp() throws Exception {
        super.setUp();
        RightSign = new RightSign();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testDivide() {
        this.assertEquals(LeftSign.RightSign(4, 1), 2);
        this.assertEquals(RightSign.RightSign(6, 2), 1);
        this.assertEquals(RightSign.RightSign(2, 3), 0);
    }}