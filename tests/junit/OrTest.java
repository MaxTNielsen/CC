package junit;
import junit.framework.TestCase;
import pass.Or;
public class OrTest extends TestCase{
    private Or or;

    protected void setUp() throws Exception {
        super.setUp();
        or = new Or();
    }

    public void testCompute() {
        assertEquals(or.or(0,40), 40);
        assertEquals(or.or(40,0), 40);
        assertEquals(or.or(25,23), 31);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
}