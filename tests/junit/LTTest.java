package junit;

import pass.LTPass;
import junit.framework.TestCase;

public class LTTest extends TestCase{
    private LTPass lt;

    protected void setUp() throws Exception {
        super.setUp();
        lt = new LTPass();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testLT() {
        this.assertTrue(lt.lessthan(4, 5) == true);
        this.assertTrue(lt.lessthan(6, 7) == true);
        this.assertTrue(lt.lessthan(5, 3) == false);
    }
}
