package junit.tests.framework;

import junit.framework.TestCase;

/**
 * Test class used in SuiteTest
 */
public class OneTestCase extends TestCase {
    public void noTestCase() {
    }

    public void testCase() {
    }

    public void testCase(int arg) {
    }

    // if names are the same, they are counted as one, same test
    public void testCase(int arg, int aa) {
    }
}