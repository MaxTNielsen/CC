package junit;
import junit.framework.TestCase;
import pass.UnaryPlus;
public class UnaryPlusTest extends TestCase {

    private UnaryPlus unaryPlus;

    protected void setUp() throws Exception {
        super.setUp();
        unaryPlus = new UnaryPlus();
    }

    public void testCompute() {
        assertEquals(unaryPlus.plusInt(-1), -1);
        assertEquals(unaryPlus.plusInt(1),1);
        //assertEquals(unaryPlus.plusChar('5'),53);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
}