package base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import pages.CartPage;
import pages.OrderPage;
import utils.BrowserActions;
import utils.WaitUtils;

public class BasePage {

	protected WebDriver driver;
	protected WaitUtils waitUtils;
	protected BrowserActions actions;

	public BasePage(WebDriver driver) {
		this.driver = driver;
		this.waitUtils = new WaitUtils(driver);
		this.actions = new BrowserActions(driver);
		PageFactory.initElements(driver, this);
	}

	private By cartHeader = By.cssSelector("[routerlink*='cart']");
	private By orderHeader = By.cssSelector("[routerlink*='myorders']");

	public CartPage goToCartPage() {
		driver.findElement(cartHeader).click();
		CartPage cartPage = new CartPage(driver);
		return cartPage;
	}

	public OrderPage goToOrderPage() {
		driver.findElement(orderHeader).click();
		OrderPage orderPage = new OrderPage(driver);
		return orderPage;
	}

}
