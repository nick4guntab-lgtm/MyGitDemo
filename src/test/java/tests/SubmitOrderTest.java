package tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.CartPage;
import pages.CheckoutPage;
import pages.ConfirmationPage;
import pages.OrderPage;
import pages.ProductCatalogPage;
import utils.JsonReader;

public class SubmitOrderTest extends BaseTest {

	@Test(dataProvider = "getData", groups = { "Purchase" })
	public void submitOrder(HashMap<String, String> input) throws IOException, InterruptedException {

		ProductCatalogPage productCatalouge = landingPage.loginApplication(input.get("email"), input.get("password"));
		productCatalouge.addProductToCart(productName());
		CartPage cartPage = productCatalouge.goToCartPage();

		Boolean match = cartPage.VerifyProductDisplay(productName());
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("india");

		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
	}

	@Test(dependsOnMethods = { "submitOrder" })
	public void OrderHistoryTest() {
		ProductCatalogPage productCatalogue = landingPage.loginApplication("william.nugen@gmail.com", "Rahul@4321");
		OrderPage orderPage = productCatalogue.goToOrderPage();
		Assert.assertTrue(orderPage.VerifyOrderDisplay(productName()));
	}

	@DataProvider
	public Object[][] getData() throws IOException {

//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("email", "william.nugen@gmail.com");
//		map.put("password", "Rahul@4321");
//
//		HashMap<String, String> map1 = new HashMap<String, String>();
//		map1.put("email", "rahulshetty@gecko.com");
//		map1.put("password", "Kaimado@1234");

		List<HashMap<String, String>> data = JsonReader.getJsonDataToMap(
				System.getProperty("user.dir") + "\\SeleniumFrameworkDesign\\src\\test\\resources\\testData\\PurchaseOrder.json");

		return new Object[][] { { data.get(0) }, { data.get(1) } };
	}

	

}
