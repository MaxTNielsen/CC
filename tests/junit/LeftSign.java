package junit;

import junit.framework.TestCase;
import pass.LeftSign;


public class LeftSign extends TestCase{
    private LeftSign ls;

    protected void setUp() throws Exception {
        super.setUp();
        ls = new LeftSign();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testDivide() {
        this.assertEquals(ls.LeftSign(4, 1), 8);
        this.assertEquals(ls.LeftSign(6, 2), 24);
        this.assertEquals(ls.LeftSign(2, 3), 16);
    }
}