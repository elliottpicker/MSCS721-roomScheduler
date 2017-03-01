package test.java;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
   JUnitMeeting.class,
   JUnitRoom.class
})
/**
 * A test suite designed to call various test cases (one per class)
 * @author elliott picker
 *
 */
public class TestSuite {   
}  