package tests;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import base.BaseTest;
import utils.ExtentReportManager;
import utils.ScreenshotUtils;

public class TestListeners extends BaseTest implements ITestListener {

	ExtentTest test;
	ExtentReports extent = ExtentReportManager.getReportObject();

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
			// Retrieve the active thread's driver dynamically
			WebDriver driver = (WebDriver) result.getTestClass().getRealClass().getField("driver")
					.get(result.getInstance());

			// Call your screenshot utility here automatically
			String path = ScreenshotUtils.getScreenshot(result.getMethod().getMethodName(), driver);

			// (Optional) Attach directly to ExtentReports here
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 3. Take Screenshot and attach it to the report
		String filePath = null;
		try {
			filePath = ScreenshotUtils.getScreenshot(result.getMethod().getMethodName(), driver);
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
