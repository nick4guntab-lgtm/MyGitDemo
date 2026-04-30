package stepDefinations;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.CartPage;
import pageObjects.CheckoutPage;
import pageObjects.ConfirmationPage;
import pageObjects.LandingPage;
import pageObjects.ProductCatalouge;
import testComponents.BaseTest;

public class StepDefination extends BaseTest {

	public LandingPage landingPage;
	public ProductCatalouge productCatalouge;
	public ConfirmationPage confirmationPage;

	@Given("I landed on the Ecommerce Page")
	public void I_landed_on_the_Ecommerce_Page() throws IOException {
		landingPage = launchApplication();
	}

	@Given("^Logged In with username (.+) and password (.+)$")
	public void Logged_In_with_username_and_password(String username, String password) {

		productCatalouge = landingPage.loginApplication(username, password);
	}

	@When("^I add product (.+) to cart$")
	public void i_add_product_to_cart(String productName) throws InterruptedException {
		List<WebElement> products = productCatalouge.getProductList();
		productCatalouge.addProductToCart(productName);
	}

	@When("^Checkout (.+) and submit the order$")
	public void checkout_submit_the_order(String productName) {
		CartPage cartPage = productCatalouge.goToCartPage();

		Boolean match = cartPage.VerifyProductDisplay(productName);
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("india");

		confirmationPage = checkoutPage.submitOrder();

	}

	@Then("{string} message is displayed on confirmation page")
	public void message_is_displayed_on_confirmation_page(String string) {
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase(string));
		driver.quit();
	}

	@Then("^\"([^\"]*)\" message is displayed$")
	public void message_is_displayed(String strArg1) {
		String actualErrorMessage = landingPage.getErrorMessage();
		Assert.assertEquals(strArg1, actualErrorMessage);
		driver.close();
	}

}
