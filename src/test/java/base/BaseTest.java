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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import config.ConfigManager;
import config.FrameworkConstants;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.LandingPage;

public class BaseTest {

	private static ThreadLocal<WebDriver> tldriver = new ThreadLocal<WebDriver>();

	public WebDriver getDriver() {
		return tldriver.get();
	}

	protected WebDriver driver;
	public LandingPage landingPage;
	public Properties prop; // Global properties object

	@SuppressWarnings("deprecation")
	public WebDriver initializeDriver() throws IOException {

		String browserName = ConfigManager.getBrowserTarget().toLowerCase().trim();

		// 1. Check if Docker passed an environment network URL variable
		String containerGridUrl = System.getenv("SELENIUM_GRID_URL");

		// 2. Track whether we should use Remote Grid or Local Drivers
		boolean isRemoteExecution = (containerGridUrl != null);
		String gridUrl = isRemoteExecution ? containerGridUrl : FrameworkConstants.SELENIUM_GRID_URL;

		WebDriver driverInstance = null;

		if (browserName.contains(FrameworkConstants.CHROME_BROWSER)) {
			ChromeOptions options = new ChromeOptions();
			WebDriverManager.chromedriver().setup();

			if (browserName.contains(FrameworkConstants.HEADLESS_MODE)) {
				options.addArguments(FrameworkConstants.NEW_HEADLESS_MODE);
				options.addArguments(FrameworkConstants.HEADLESS_WINDOW_SIZE); // Cleaned typo
				options.addArguments(FrameworkConstants.DISABLE_GPU);
				options.addArguments(FrameworkConstants.NO_SANDBOX);
				options.addArguments(FrameworkConstants.DISABLE_SHM_USAGE);
			}

			// Condition updated using boolean flag - Eclipse warning is fully resolved!
			if (isRemoteExecution) {
				driverInstance = new org.openqa.selenium.remote.RemoteWebDriver(new java.net.URL(gridUrl), options);
			} else {
				driverInstance = new ChromeDriver(options);
			}

			driverInstance.manage().window().setSize(new Dimension(1920, 1080));

		} else if (browserName.equalsIgnoreCase(FrameworkConstants.EDGE_BROWSER)) {
			org.openqa.selenium.edge.EdgeOptions options = new org.openqa.selenium.edge.EdgeOptions();
			WebDriverManager.edgedriver().setup();

			if (isRemoteExecution) {
				driverInstance = new org.openqa.selenium.remote.RemoteWebDriver(new java.net.URL(gridUrl), options);
			} else {
				driverInstance = new EdgeDriver();
			}
		}

		if (driverInstance == null) {
			throw new RuntimeException("ERROR: Browser initialization failed! The browser name received was: '"
					+ browserName + "'. Check your global properties string matching rules.");
		}

		tldriver.set(driverInstance);

		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(FrameworkConstants.EXPLICIT_WAIT));
		getDriver().manage().window().maximize();
		return getDriver();

	}

	@BeforeMethod(alwaysRun = true)
	public LandingPage launchApplication() throws IOException {
		// 1. Forcefully load the properties file manually to ensure it's not null
		prop = new Properties();
		FileInputStream fis = new FileInputStream(FrameworkConstants.GLOBAL_DATA_PROPERTIES_PATH);
		prop.load(fis);
		initializeDriver();
		landingPage = new LandingPage(getDriver());
		String appUrl = prop.getProperty("url");

		if (appUrl == null) {
			throw new RuntimeException(
					"ERROR: The 'url' key was not found in your properties file! Please check the file.");
		}
		landingPage.goTo(appUrl);

		return landingPage;
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		if (getDriver() != null) {
			getDriver().quit();
		}
		tldriver.remove();
	}

}
