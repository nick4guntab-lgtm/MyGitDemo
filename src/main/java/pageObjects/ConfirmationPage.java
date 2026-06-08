package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

<<<<<<< HEAD
import base.BasePage;

public class ConfirmationPage extends BasePage {
=======
import abstractComponents.AbstractComponents;

public class ConfirmationPage extends AbstractComponents {
>>>>>>> c18edf0b7cfb0a174d7ddd7c2e5e0027b3cd35f1
	WebDriver driver;

	public ConfirmationPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = ".hero-primary")
	WebElement confirmationMessage;

	public String getConfirmationMessage() {
		return confirmationMessage.getText();
	}
}
