package Unsupported_test.junit;


import Unsupported_test.pass.OrAssign;
import junit.framework.TestCase;

public class OrAssignTest extends TestCase{
    private OrAssign orAssign;

    protected void setUp() throws Exception {
        super.setUp();
        orAssign = new OrAssign();
    }

    public void testOrAssign() {
        this.assertEquals(orAssign.doOrAssign(2,4), 7);
        this.assertEquals(orAssign.doOrAssign(1,1), 1);
        this.assertEquals(orAssign.doOrAssign(2,1), 3);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
}
