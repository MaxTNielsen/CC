package junit;

import pass.PostfixIncrement;
import junit.framework.TestCase;

public class PostfixIncrementTest extends TestCase{
    private PostfixIncrement postfixIncrement;

    protected void setUp() throws Exception {
        super.setUp();
        postfixIncrement = new PostfixIncrement();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testRigtSign() {
        this.assertEquals(postfixIncrement.doPostfixInc(1, 7), 8);
        this.assertEquals(postfixIncrement.doPostfixInc(6, 7), 13);
        this.assertEquals(postfixIncrement.doPostfixInc(2, 3), 5);
    }
}
