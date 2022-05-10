package junit;

import junit.framework.TestCase;

import pass.RightShiftAssign;

public class RightShiftAssignTest extends TestCase{

    private RightShiftAssign rightShiftAssign;

    protected void setUp() throws Exception {
        super.setUp();
        rightShiftAssign = new RightShiftAssign();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testRightShiftAssign() {
        this.assertTrue(rightShiftAssign.doRightShiftAssign(4, 1) == true);
        this.assertTrue(rightShiftAssign.doRightShiftAssign(6, 2) == true);
        this.assertTrue(rightShiftAssign.doRightShiftAssign(2, 3) == true);
    }
}