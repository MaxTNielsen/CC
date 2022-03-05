package junit;

import pass.LogicalOr;
import junit.framework.TestCase;

public class LogicalOrTest extends TestCase{
    private LogicalOr logicalOr;

    protected void setUp() throws Exception {
        super.setUp();
        logicalOr = new LogicalOr();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testRigtSign() {
        this.assertTrue(logicalOr.doLogicalOr(4, 5) == false);
        this.assertTrue(logicalOr.doLogicalOr(6, 7) == true);
        this.assertTrue(logicalOr.doLogicalOr(2, 3) == true);
    }
}
