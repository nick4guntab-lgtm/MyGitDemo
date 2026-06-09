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

import config.FrameworkConstants;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.LandingPage;

public class BaseTest {

	protected WebDriver driver;
	public LandingPage landingPage;
	public Properties prop;

	public WebDriver initializeDriver() throws IOException {

		Properties prop = new Properties();
		String propertiesFilePath = FrameworkConstants.GLOBAL_DATA_PROPERTIES_PATH;
		FileInputStream fis = new FileInputStream(propertiesFilePath);
		prop.load(fis);

		String browserName = System.getProperty("browser") != null ? System.getProperty("browser")
				: prop.getProperty("browser");

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
