package seleniumFrameworkDesign;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.CartPage;
import pageObjects.ProductCatalouge;
import testComponents.BaseTest;
import testComponents.RetryTest;

public class ErrorValidationTest extends BaseTest {

	@Test(groups = { "ErrorHandling" }, retryAnalyzer = RetryTest.class)
	public void loginErrorValidation() throws IOException, InterruptedException {

		landingPage.loginApplication("william.nugen@gmail.com", "Rahul@54321");
		Assert.assertEquals(landingPage.getErrorMessage(), "Incorrect email or password.");
	}

	@Test
	public void productErrorValidation() throws InterruptedException {

		ProductCatalouge productCatalogue = landingPage.loginApplication("william.nugen@gmail.com", "Rahul@4321");
		productCatalogue.addProductToCart(productName());
		CartPage cartPage = productCatalogue.goToCartPage();

		Boolean match = cartPage.VerifyProductDisplay("ZARA COAT 4");
		Assert.assertFalse(match);
	}

}
