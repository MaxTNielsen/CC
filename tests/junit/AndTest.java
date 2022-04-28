package junit;

import junit.framework.TestCase;
import pass.And;

public class AndTest extends TestCase{
    private And and;

    protected void setUp() throws Exception {
        super.setUp();
        and = new And();
    }

    public void testCompute() {
        assertEquals(and.and(0,40), 0);
        assertEquals(and.and(40,0), 0);
        assertEquals(and.and(25,23), 17);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
}