package junit;
import junit.framework.TestCase;
import pass.ExceptionHandling;
public class ThrowTest extends TestCase{
    private ExceptionHandling errorHandling;

    protected void setUp() throws Exception {
        super.setUp();
        errorHandling = new ExceptionHandling();
    }
    public void testCompute() {
    }
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
