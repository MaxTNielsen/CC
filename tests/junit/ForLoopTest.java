package junit;

import junit.framework.TestCase;
import pass.ForLoop;
public class ForLoopTest extends TestCase{
    private ForLoop forloop;

    protected void setUp() throws Exception {
        super.setUp();
        forloop = new ForLoop();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    
    public void testCompute() { 
        assertEquals(forloop.additionForLoop(1), 100);
        assertEquals(forloop.additionForLoop(-1), 100);
        assertEquals(forloop.additionForLoop(100), 100);
    }
}