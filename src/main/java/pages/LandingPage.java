package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.BasePage;

public class LandingPage extends BasePage {
	WebDriver driver;

	public LandingPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// PageFactory
	@FindBy(id = "userEmail")
	WebElement userEmail;

	@FindBy(id = "userPassword")
	WebElement userPassword;

	@FindBy(id = "login")
	WebElement submit;

	@FindBy(xpath = "//div[contains(text(), 'Incorrect email or password')]")
	WebElement loginErrorMessage;

	public ProductCatalogPage loginApplication(String email, String password) {
		userEmail.sendKeys(email);
		userPassword.sendKeys(password);
		submit.click();
		ProductCatalogPage productCatalouge = new ProductCatalogPage(driver);

		return productCatalouge;
	}

	public String getErrorMessage() {
		waitUtils.waitForElementToAppear(loginErrorMessage);
		return loginErrorMessage.getText();
	}

	public void goTo() {
		driver.get("https://rahulshettyacademy.com/client/");
	}
}
