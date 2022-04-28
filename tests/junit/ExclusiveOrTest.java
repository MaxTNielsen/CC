package junit;

import pass.ExclusiveOr;
import junit.framework.TestCase;
public class ExclusiveOrTest extends TestCase{

    private ExclusiveOr exclusiveOr;

    protected void setUp() throws Exception {
        super.setUp();
        exclusiveOr = new ExclusiveOr();
    }

    public void testCompute() {
        assertEquals(exclusiveOr.xor(0,40), 40);
        assertEquals(exclusiveOr.xor(40,0), 40);
        assertEquals(exclusiveOr.xor(25,23), 14);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
}