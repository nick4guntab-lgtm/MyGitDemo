package config;

public class FrameworkConstants {

	// Prevent instantiation
	private FrameworkConstants() {
	}

	// SECTION 1: SYSTEM & PROJECT PATHS
	private static final String USER_DIR = System.getProperty("user.dir");

	public static final String EXTENT_REPORT_PATH = USER_DIR + "/reports/index.html";

	// Path to your Global Configuration properties file
	public static final String GLOBAL_DATA_PROPERTIES_PATH = USER_DIR
			+ "/src/test/resources/properties/globalData.properties";

	public static final String PURCHASE_ORDER_JSON = USER_DIR + "/src/test/resources/testData/PurchaseOrder.json";

	// Path to Extent Reports output directory
	public static final String EXTENT_REPORT_FOLDER_PATH = USER_DIR + "\\reports\\";
	public static final String EXTENT_REPORT_FILE_NAME = "index.html";

	// SECTION 2: EXPLICIT & IMPLICIT TIMEOUTS
	public static final long SHORT_WAIT = 5;
	public static final long EXPLICIT_WAIT = 10;
	public static final long LONG_WAIT = 20;

	// SECTION 3: APPLICATION CONSTANTS
	public static final String CHROME_BROWSER = "chrome";
	public static final String FIREFOX_BROWSER = "firefox";
	public static final String EDGE_BROWSER = "edge";
	public static final String HEADLESS_MODE = "headless";

}
