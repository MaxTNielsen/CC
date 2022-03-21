package junit;

import junit.framework.TestCase;
import pass.StarAssign;

public class StarAssignTest extends TestCase {
    private StarAssign starAssign;

    protected void setUp() throws Exception {
        super.setUp();
        starAssign = new StarAssign();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testStarAssign() {
        this.assertEquals(starAssign.doStarAssign(4, 9), 36);
    }

}
