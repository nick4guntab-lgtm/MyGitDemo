package testComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryTest implements IRetryAnalyzer {

	private int count = 0;
    private static final int maxTry = 1; // Set how many times you want the test to retry

    @Override
    public boolean retry(ITestResult result) {
        if (!result.isSuccess()) { // If the test fails
            if (count < maxTry) {  // And we haven't hit our max retries
                count++;           // Increment the counter
                return true;       // Tell TestNG to run the test again
            }
        }
        return false; // Tell TestNG to stop retrying
    }

}
