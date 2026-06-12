package config;

public class FrameworkConstants {

	// Prevent instantiation
	private FrameworkConstants() {
	}

	// SECTION 1: SYSTEM & PROJECT PATHS
	private static final String USER_DIR = System.getProperty("user.dir");

	public static final String EXTENT_REPORT_PATH = USER_DIR + "/target/surefire-reports/index.html";
	// Path to your Global Configuration properties file
	public static final String GLOBAL_DATA_PROPERTIES_PATH = USER_DIR
			+ "/src/test/resources/properties/globalData.properties";

	public static final String PURCHASE_ORDER_JSON = USER_DIR + "/src/test/resources/testData/PurchaseOrder.json";

	// Path to Extent Reports output directory
	public static final String EXTENT_REPORT_FOLDER_PATH = USER_DIR + "/reports/";
	public static final String EXTENT_REPORT_FILE_NAME = "index.html";

	// SECTION 2: EXPLICIT & IMPLICIT TIMEOUTS
	public static final long SHORT_WAIT = 5;
	public static final long EXPLICIT_WAIT = 10;
	public static final long LONG_WAIT = 15;

	// SECTION 3: APPLICATION CONSTANTS
	public static final String CHROME_BROWSER = "chrome";
	public static final String FIREFOX_BROWSER = "firefox";
	public static final String EDGE_BROWSER = "edge";
	public static final String HEADLESS_MODE = "headless-chrome";
	public static final String HEADLESS_WINDOW_SIZE = "--window-size=1920,1080";
	public static final String NEW_HEADLESS_MODE = "--headless=new";
	public static final String NO_SANDBOX = "--no-sandbox";
	public static final String DISABLE_GPU = "--disable-gpu";
	public static final String DISABLE_SHM_USAGE = "--disable-dev-shm-usage";
	public static final String DOCKER__WINDOW_SIZE = "--window-size=1440,900";
	public static final String SELENIUM_GRID_URL = "http://localhost:4444/wd/hub";
			

}
