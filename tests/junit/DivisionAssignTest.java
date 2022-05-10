package junit;

import junit.framework.TestCase;
import pass.DivisionAssign;


public class DivisionAssignTest extends TestCase{
    private DivisionAssign division;
    private int a;

    protected void setUp() throws Exception {
        super.setUp();
        division = new DivisionAssign();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testDivideAndAssign() {
        this.assertTrue(division.divideAndAssign(0, 42) == true);
        this.assertTrue(division.divideAndAssign(42, 1) == true);
    }
}
