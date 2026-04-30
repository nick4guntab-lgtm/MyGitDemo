package testComponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import resources.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener {

	ExtentTest test;
	ExtentReports extent = ExtentReporterNG.getReportObject();
	
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

	@Override
	public void onTestStart(ITestResult result) {
		test = extent.createTest(result.getMethod().getMethodName());
		extentTest.set(test);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		test.log(Status.PASS, "Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// 1. Log the error details in the report
	
				extentTest.get().fail(result.getThrowable());

				// 2. Get the Driver instance from the failed test class
				try {
					driver = (WebDriver) result.getTestClass().getRealClass().getField("driver")
							.get(result.getInstance());
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				// 3. Take Screenshot and attach it to the report
				String filePath = null;
				try {
					filePath = getScreenshot(result.getMethod().getMethodName(), driver);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				// Add the screenshot image to the Extent Report entry
				extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	}

}
