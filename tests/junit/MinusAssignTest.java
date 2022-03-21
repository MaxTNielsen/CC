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
    
    public void testRigtSign() {
        this.assertTrue(minusAssign.doMinusAssign(4, 5) == true);
        this.assertTrue(minusAssign.doMinusAssign(6, 7) == true);
        this.assertTrue(minusAssign.doMinusAssign(2, 3) == true);
    }
}
