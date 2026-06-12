package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {

	private static Properties prop = new Properties();

	// Static initialization block runs automatically to load the file once
	static {
		try (FileInputStream fis = new FileInputStream(FrameworkConstants.GLOBAL_DATA_PROPERTIES_PATH)) {
			prop.load(fis);
		} catch (IOException e) {
			throw new RuntimeException("CRITICAL: Failed to load globalData.properties configuration file.", e);
		}
	}

	public static String getProductName() {
		return prop.getProperty("productName");
	}

	public static String getURL() {
		return prop.getProperty("URL");
	}

	public static String getBrowserTarget() {
		String cliBrowser = System.getProperty("browser");
		if (cliBrowser != null) {
			return cliBrowser.trim().toLowerCase();
		}
		String propertiesBrowser = prop.getProperty("browser");
		if (propertiesBrowser != null) {
			return propertiesBrowser.trim().toLowerCase();
		}

		throw new RuntimeException(
				"CRITICAL CONFIGURATION ERROR: Browser target parameter is not defined in Maven CLI flags or globalData.properties!");
	}
}