package junit;

import junit.framework.TestCase;
import pass.LeftShift;


public class LeftShiftTest extends TestCase{
    private LeftShift leftShift;

    protected void setUp() throws Exception {
        super.setUp();
        leftShift = new LeftShift();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testLeftSign() {
        this.assertEquals(leftShift.leftShift_(4, 1), 8);
        this.assertEquals(leftShift.leftShift_(6, 2), 24);
        this.assertEquals(leftShift.leftShift_(2, 3), 16);
    }
}