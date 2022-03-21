package junit;

import junit.framework.TestCase;
import pass.Conditional;
public class ConditionalTest extends TestCase{
    private Conditional conditional;

    protected void setUp() throws Exception {
        super.setUp();
        conditional = new Conditional();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    
    public void testCompute() {
        assertEquals(conditional.ConditionExpressionLimit(10,20), 20);
        assertEquals(conditional.ConditionExpressionLimitp(20,10), 20);
    }
}