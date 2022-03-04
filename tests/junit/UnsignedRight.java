package junit;

import junit.framework.TestCase;
import pass.UnsignedRight;


public class UnsignedRight extends TestCase{
    private UnsignedRight UnsignedRight;

    protected void setUp() throws Exception {
        super.setUp();
        UnsignedRight = new UnsignedRight();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testDivide() {
        this.assertEquals(UnsignedRight.UnsignedRight(4, 1), 2);
        this.assertEquals(UnsignedRight.UnsignedRight(-6, 2), 1);
        this.assertEquals(UnsignedRight.UnsignedRight(-2, 3), 12);
    }}