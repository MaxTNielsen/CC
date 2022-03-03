package junit;
import junit.framework.TestCase;
import pass.Ucomp;
public class UcompTest extends TestCase{
    private Ucomp ucomp;

    protected void setUp() throws Exception {
        super.setUp();
        ucomp = new Ucomp();
    }

    public void testCompute() {
        assertEquals(ucomp.flip(-1), 0);
        assertEquals(ucomp.flip(10), -11);
        assertEquals(ucomp.flip(0), -1);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
}