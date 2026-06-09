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

public class WaitUtils {

	private WebDriver driver;
	private WebDriverWait wait;
	private final int DEFAULT_TIMEOUT = 5;

	public WaitUtils(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
	}

	public WaitUtils(WebDriver driver, int timeoutInSeconds) {
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
	}

	public WebElement waitForElementToAppear(By findBy) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}

	public WebElement waitForElementToAppear(WebElement element) {
		return wait.until(ExpectedConditions.visibilityOf(element));
	}

	public boolean waitForElementToDisappear(WebElement element) {
		return wait.until(ExpectedConditions.invisibilityOf(element));
	}

	public WebElement waitForElementToBeClickable(WebElement element) {
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
