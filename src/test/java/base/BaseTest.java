package base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.LandingPage;

public class BaseTest {

	protected WebDriver driver;
	public LandingPage landingPage;
	public Properties prop;

	public WebDriver initializeDriver() throws IOException {

		// Properties class configurations
		Properties prop = new Properties();

		// 1. Get the dynamic project root path
		String projectRoot = System.getProperty("user.dir");

		// 2. Combine it with the exact relative path matching your project layout
		String propertiesFilePath = projectRoot + "/src/test/resources/propertiesFiles/globalData.properties";

		// 3. Load the file stream safely
		FileInputStream fis = new FileInputStream(propertiesFilePath);
		prop.load(fis);

		// Checks if a browser system property is sent via Maven command line, otherwise
		// defaults to local global property
		String browserName = System.getProperty("browser") != null ? System.getProperty("browser")
				: prop.getProperty("browser");

		// Chrome Configuration (Including Headless Mode execution logic)
		if (browserName.contains("chrome")) {
			ChromeOptions options = new ChromeOptions();
			WebDriverManager.chromedriver().setup();

			if (browserName.contains("headless")) {
				options.addArguments("headless");
			}
			driver = new ChromeDriver(options);
			driver.manage().window().setSize(new Dimension(1440, 900)); // Helps headless runs scale accurately

		} else if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();

		} else if (browserName.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
	}

	public String productName() {
		String productName = prop.getProperty("productName");
		return productName;
	}

	@BeforeMethod(alwaysRun = true)
	public LandingPage launchApplication() throws IOException {
		WebDriver driver = initializeDriver();
		landingPage = new LandingPage(driver);

		landingPage.goTo();
		return landingPage;
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		driver.quit();
	}

}
