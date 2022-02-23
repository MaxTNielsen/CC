package junit;

import junit.framework.TestCase;
import pass.RightShift;


public class RightShiftTest extends TestCase{
    private RightShift rightShift;

    protected void setUp() throws Exception {
        super.setUp();
        rightShift = new RightShift();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testRigtSign() {
        this.assertEquals(rightShift.doRightShift(4, 1), 2);
        this.assertEquals(rightShift.doRightShift(6, 2), 1);
        this.assertEquals(rightShift.doRightShift(2, 3), 0);
    }
}