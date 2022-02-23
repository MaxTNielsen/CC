package junit;

import junit.framework.TestCase;
import pass.UnsignedRight;


public class UnsignedRightTest extends TestCase{
    private UnsignedRight unsignedRight;

    protected void setUp() throws Exception {
        super.setUp();
        unsignedRight = new UnsignedRight();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testDivide() {
        this.assertEquals(unsignedRight.unsignedRight(4, 1), 2);
        this.assertEquals(unsignedRight.unsignedRight(-6, 2), 1);
        this.assertEquals(unsignedRight.unsignedRight(-2, 3), 12);
    }
}