package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

	// SECTION 1: JAVASCRIPT EXECUTOR UTILITIES

	public void scrollIntoElement(WebElement element) {
		javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public void refreshPageViaDOM() {
		javascriptExecutor.executeScript("history.go(0);");
	}

	public static void scrollToBottom(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
	}

	// SECTION 2: ADVANCED USER ACTIONS (MOUSE/KEYBOARD)

	public void hoverOverElement(WebElement element) {
		actions.moveToElement(element).build().perform();
	}

	public void dragAndDrop(WebElement source, WebElement target) {
		actions.dragAndDrop(source, target).build().perform();
	}

	public void rightClick(WebElement element) {
        actions.contextClick(element).build().perform();
	}
	
	

}
