package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import config.FrameworkConstants;

public class ExtentReportManager {

	public static ExtentReports getReportObject() {
		String path = FrameworkConstants.EXTENT_REPORT_PATH;
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("Web Automation Framework Results");
		reporter.config().setDocumentTitle("Test Results");

		ExtentReports extentReports = new ExtentReports();
		extentReports.attachReporter(reporter);
		extentReports.setSystemInfo("Automation Tester", "Nikhil");

		return extentReports;

	}

}
