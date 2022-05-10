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
    
    public void testPostFixInc() {
        PostfixIncrementTest.assertEquals(postfixIncrement.doPostfixInc(1), 2);
    }
}
