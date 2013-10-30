package junit;

import org.hamcrest.core.Is;
import org.junit.*;

/**
 * User: Nathanchen Date: 30/10/13 Time: 9:47 AM Description:
 */
public class CoreJUnit4SampleTest
{
    @BeforeClass
    public static void beforeClass ()
    {
        System.out.println("beforeClass() method executed.");
        System.out.println();
    }

    @BeforeClass
    public static void beforeClass2 ()
    {
        System.out.println("beforeClass2() method executed.");
        System.out.println();
    }

    @AfterClass
    public static void afterClass ()
    {
        System.out.println("afterClass() method executed.");
        System.out.println();
    }

    @Before
    public void before ()
    {
        System.out.println("before() method executed.");
    }

    @After
    public void after ()
    {
        System.out.println("after() method executed");
    }

    @Test
    public void testSucceeded ()
    {
        System.out.println("testSucceeded() method executed.");
    }

    @Test
    @Ignore
    public void testIgnore ()
    {
        System.out.println("testIgnore() method executed.");
    }

    @Test
    public void testFailed ()
    {
        System.out.println("testFailed() method executed.");
        throw new RuntimeException("Throw delibrately");
    }

    @Test
    public void testAssumptionFailed ()
    {
        System.out.println("testAssumptionFailed() method executed.");
        Assume.assumeThat(0, Is.is(1));
    }

    @Test
    public void testFilteredOut ()
    {
        System.out.println("testFilteredOut() method executed.");
    }
}
