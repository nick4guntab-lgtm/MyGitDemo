package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import base.BasePage;

public class ConfirmationPage extends BasePage {

	public ConfirmationPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	By confirmationMessage = By.xpath("//h1[@class='hero-primary']");

	public String getConfirmationMessage() {
		WebElement confirmationElement = utils.WaitUtils.waitForElementToAppear(driver, confirmationMessage);
		return confirmationElement.getText();
	}
}
