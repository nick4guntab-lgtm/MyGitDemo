package testcomponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import utils.ExtentReportManager;
import utils.ScreenshotUtils;

public class TestListeners implements ITestListener {

	private ExtentReports extent = ExtentReportManager.getReportObject();
	private ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

	@Override
	public void onTestStart(ITestResult result) {
		ExtentTest test = extent.createTest(result.getMethod().getMethodName());
		extentTest.set(test);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		extentTest.get().log(Status.PASS, "Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// 1. Log the exception details into the thread-safe Extent Report entry
		extentTest.get().fail(result.getThrowable());

		// 2. Isolate the driver locally to guarantee thread safety during parallel
		// execution
		WebDriver threadDriver = null;
		try {
			java.lang.reflect.Field field = result.getTestClass().getRealClass().getSuperclass()
					.getDeclaredField("driver");

			field.setAccessible(true);

			threadDriver = (WebDriver) field.get(result.getInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 3. Capture and attach the snapshot using the validated thread-specific driver
		// instance
		if (threadDriver != null) {
			String filePath = null;
			try {
				filePath = ScreenshotUtils.getScreenshot(result.getMethod().getMethodName(), threadDriver);
				extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	}
}