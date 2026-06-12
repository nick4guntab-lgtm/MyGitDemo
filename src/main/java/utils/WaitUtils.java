package utils;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import config.FrameworkConstants;

public class WaitUtils {

	private WebDriver driver;
	private static WebDriverWait wait;

	public WaitUtils(WebDriver driver) {
		this.driver = driver;
		new WebDriverWait(driver, Duration.ofSeconds(FrameworkConstants.LONG_WAIT));
	}

//		public WaitUtils(WebDriver driv	er, int timeoutInSeconds) {
//		this.driver = driver;
//		new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
//	}

	public static WebElement waitForElementToPresent(By locator) {
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public static WebElement waitForWebElementToAppear(WebDriver driver, WebElement findBy) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		return wait.until(ExpectedConditions.visibilityOf(findBy));
	}

	public WebElement waitForElementToAppear(By findBy) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}

	public static WebElement waitForElementToAppear(WebDriver driver, By locator) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public boolean waitForElementToDisappear(WebElement element) {
		return wait.until(ExpectedConditions.invisibilityOf(element));
	}

	public static WebElement waitForElementToBeClickable(WebElement element) {
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public WebElement waitForElementToBeClickable(By findBy) {
		return wait.until(ExpectedConditions.elementToBeClickable(findBy));
	}

	public boolean waitForElementToNotBeStale(WebElement element) {
		return wait.until(ExpectedConditions.not(ExpectedConditions.stalenessOf(element)));
	}

	public boolean waitForTextToBePresentInElement(WebElement element, String text) {
		return wait.until(ExpectedConditions.textToBePresentInElement(element, text));
	}

	public WebElement waitForElementWithFluentWait(By findBy, int timeoutSeconds, int pollingMillis) {
		Wait<WebDriver> fluentWait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(timeoutSeconds))
				.pollingEvery(Duration.ofMillis(pollingMillis))
				.ignoreAll(List.of(NoSuchElementException.class, StaleElementReferenceException.class))
				.withMessage("Fluent Wait timed out: Element " + findBy + " was not visible after " + timeoutSeconds
						+ " seconds.");

		return fluentWait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}

}
