package junit;

import pass.MinusAssign;
import junit.framework.TestCase;

public class MinusAssignTest extends TestCase{
    private MinusAssign minusAssign;

    protected void setUp() throws Exception {
        super.setUp();
        minusAssign = new MinusAssign();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testMinusAssign() {
        MinusAssignTest.assertEquals(minusAssign.doMinusAssign(4, 5), -1);
    }
}
