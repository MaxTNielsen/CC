package junit;

import junit.framework.TestCase;

import pass.STARA;

public class StarassignTest extends TestCase {
    private STARA stara;

    protected void setUp() throws Exception {
        super.setUp();
        stara = new STARA();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testStarAssign() {
        this.assertEquals(stara.starassign(4, 9), 36);
        
    }

}
