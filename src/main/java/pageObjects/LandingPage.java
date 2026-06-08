package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

<<<<<<< HEAD
import base.BasePage;

public class LandingPage extends BasePage {
=======
import abstractComponents.AbstractComponents;

public class LandingPage extends AbstractComponents {
>>>>>>> c18edf0b7cfb0a174d7ddd7c2e5e0027b3cd35f1

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
	
	@FindBy(css="[class*='flyInOut']")
	WebElement loginErrorMessage;

<<<<<<< HEAD
	public ProductCatalogPage loginApplication(String email, String password) {
		userEmail.sendKeys(email);
		userPassword.sendKeys(password);
		submit.click();
		ProductCatalogPage productCatalouge = new ProductCatalogPage(driver);
=======
	public ProductCatalouge loginApplication(String email, String password) {
		userEmail.sendKeys(email);
		userPassword.sendKeys(password);
		submit.click();
		ProductCatalouge productCatalouge = new ProductCatalouge(driver);
>>>>>>> c18edf0b7cfb0a174d7ddd7c2e5e0027b3cd35f1
		return productCatalouge;
	}
	
	public String getErrorMessage() {
		waitForWebElementToAppear(loginErrorMessage);
		return loginErrorMessage.getText();
	}

	public void goTo() {
		driver.get("https://rahulshettyacademy.com/client/");
	}

}
