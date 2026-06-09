package tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.CartPage;
import pages.ProductCatalogPage;

public class ErrorValidationTest extends BaseTest {

	@Test(groups = { "ErrorHandling" }, retryAnalyzer = RetryAnalyzer.class)
	public void loginErrorValidation() throws IOException, InterruptedException {

		landingPage.loginApplication("william.nugen@gmail.com", "Rahul@4321");
		Assert.assertEquals(landingPage.getErrorMessage(), "Incorrect email or password.");
	}

	@Test
	public void productErrorValidation() throws InterruptedException {

		ProductCatalogPage productCatalogue = landingPage.loginApplication("william.nugen@gmail.com", "Rahul@4321");
		productCatalogue.addProductToCart(productName());
		CartPage cartPage = productCatalogue.goToCartPage();

		Boolean match = cartPage.VerifyProductDisplay("ZARA COAT 4");
		Assert.assertFalse(match);
	}

}
