package junit;
import junit.framework.TestCase;
import pass.RemainderAssign;

public class RemainderAssignTest {
    private RemainderAssign remainderAssign;

    protected void setUp() throws Exception {
        super.setUp();
        rightShiftAssign = new RightShiftAssign();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testRigtSign() {
        this.assertEquals(remainderAssign.modulo(4, 1), 4);
        this.assertEquals(remainderAssign.modulo(6, 2), 0);
        this.assertEquals(remainderAssign.modulo(2, 3), 2);
    }

}
