package base;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import config.ConfigManager;
import config.FrameworkConstants;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.LandingPage;

public class BaseTest {

	protected WebDriver driver;
	public LandingPage landingPage;

	public WebDriver initializeDriver() throws IOException {

		String browserName = ConfigManager.getBrowserTarget();

		if (browserName.contains(FrameworkConstants.CHROME_BROWSER)) {
			ChromeOptions options = new ChromeOptions();
			WebDriverManager.chromedriver().setup();

			if (browserName.contains(FrameworkConstants.HEADLESS_MODE)) {
				options.addArguments(FrameworkConstants.HEADLESS_MODE);
			}
			driver = new ChromeDriver(options);
			driver.manage().window().setSize(new Dimension(1440, 900)); // Helps headless runs scale accurately

		} else if (browserName.equalsIgnoreCase(FrameworkConstants.FIREFOX_BROWSER)) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();

		} else if (browserName.equalsIgnoreCase(FrameworkConstants.EDGE_BROWSER)) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(FrameworkConstants.EXPLICIT_WAIT));
		driver.manage().window().maximize();
		return driver;
	}

	@BeforeMethod(alwaysRun = true)
	public LandingPage launchApplication() throws IOException {
		driver = initializeDriver();
		landingPage = new LandingPage(driver);

		landingPage.goTo();
		return landingPage;
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		driver.quit();
	}

}
