// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package junit;

import java.io.File;
import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;
import pass.*;

/**
 * JUnit test suite for running the j-- programs in tests/pass.
 */

public class JMinusMinusTestRunner {

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(HelloWorldTest.class);
        suite.addTestSuite(FactorialTest.class);
        suite.addTestSuite(GCDTest.class);
        suite.addTestSuite(SeriesTest.class);
        suite.addTestSuite(ClassesTest.class);
        suite.addTestSuite(DivisionTest.class);
        suite.addTestSuite(RemainderTest.class);
        suite.addTestSuite(ShiftLeftTest.class);
        suite.addTestSuite(ShiftRightTest.class);
        suite.addTestSuite(ShiftUnsignedRightTest.class);
        suite.addTestSuite(OrTest.class);
        suite.addTestSuite(AndTest.class);
        suite.addTestSuite(ExclusiveOrTest.class);
        suite.addTestSuite(UcompTest.class);
        suite.addTestSuite(UnaryPlusTest.class);
        //suite.addTestSuite(RightShiftAssignTest.class);
        suite.addTestSuite(DivisionAssignTest.class);
        suite.addTestSuite(OrAssignTest.class);
        suite.addTestSuite(LogicalOrTest.class);
        //suite.addTestSuite(LeftSignTest.class);
        suite.addTestSuite(PostfixIncrementTest.class);
        suite.addTestSuite(MinusAssignTest.class);
        suite.addTestSuite(StarAssignTest.class);
        suite.addTestSuite(PrefixDecrementTest.class);
        suite.addTestSuite(ForLoopTest.class);
        suite.addTestSuite(ConditionalTest.class);
        suite.addTestSuite(LTTest.class);
        suite.addTestSuite(DoubleTest.class);
        return suite;
    }

    /**
     * Runs the test suite using the textual runner.
     */

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

}
