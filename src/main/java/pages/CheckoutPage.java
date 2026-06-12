package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.BasePage;
import utils.BrowserActions;

public class CheckoutPage extends BasePage {

	public CheckoutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = ".action__submit")
	WebElement submit;

	@FindBy(css = "[placeholder='Select Country']")
	WebElement country;

	@FindBy(xpath = "//button[contains(@class,'ta-item')][2]")
	WebElement selectCountry;

	By results = By.cssSelector(".ta-results");

	public void selectCountry(String countryName) {
		country.sendKeys(countryName);
		waitUtils.waitForElementToAppear(results);
		BrowserActions.clickUsingJS(selectCountry);
	}

	public ConfirmationPage submitOrder() {
		actions.scrollIntoElement(submit);
		BrowserActions.clickUsingJS(submit);
		return new ConfirmationPage(driver);
	}

}
