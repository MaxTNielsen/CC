package junit;

import junit.framework.TestCase;
import pass.ForLoop;
import pass.ColonForloop;

public class ForLoopTest extends TestCase{
    private ForLoop forloop;
    private ColonForloop colonforloop;

    protected void setUp() throws Exception {
        super.setUp();
        forloop = new ForLoop();
        colonforloop = new ColonForLoop();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    
    public void testCompute() { 
        
        assertEquals(colonforloop.test01(), 3);
        assertEquals(colonforloop.test02(), 6);
        assertEquals(colonforloop.test03(), 3);
        assertEquals(forloop.testForLoop(), 14);

    }
}