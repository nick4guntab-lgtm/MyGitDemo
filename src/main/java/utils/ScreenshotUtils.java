package utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtils {
	public static String getScreenshot(String testCaseName, WebDriver driver) throws IOException {

		String path = System.getProperty("user.dir") + "/reports/" + testCaseName + "_" + System.currentTimeMillis()
				+ ".png";
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(source, new File(path));
		
		return path;
	}

}
