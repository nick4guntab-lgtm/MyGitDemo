package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class BrowserActions {

	private JavascriptExecutor javascriptExecutor;
	private Actions actions;

	public BrowserActions(WebDriver driver) {

		if (driver == null) {
			throw new IllegalArgumentException("WebDriver instance cannot be null");
		}

		this.javascriptExecutor = (JavascriptExecutor) driver;
		this.actions = new Actions(driver);
	}
	
	//SECTION 1: JAVASCRIPT EXECUTOR UTILITIES
	
	
	
	

}
