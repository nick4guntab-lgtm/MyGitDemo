package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.BasePage;
import utils.WaitUtils;

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

	@FindBy(xpath = "//input[@id='login']")
	WebElement submit;

	By productBy = By.cssSelector(".mb-3");
	By loginErrorMessage = By.xpath("//div[text()='*Enter Valid Email']");

	public ProductCatalogPage loginApplication(String email, String password) {
		userEmail.sendKeys(email);
		userPassword.sendKeys(password);
		submit.click();
		ProductCatalogPage productCatalouge = new ProductCatalogPage(driver);

		return productCatalouge;
	}

	public String getErrorMessage() {
		return WaitUtils.waitForElementToPresent(driver, loginErrorMessage).getText();
	}

	public void goTo() {
		driver.get("https://rahulshettyacademy.com/client/");
	}
}
