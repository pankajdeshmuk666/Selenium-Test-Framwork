package com.orangehrm.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.utilities.ExtentManager;

public class BaseClass {

	protected static Properties prop;
//	protected static WebDriver driver;
//	private static ActionDriver actionDriver;

	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	private static ThreadLocal<ActionDriver> actionDriver = new ThreadLocal<>();

	public static final Logger logger = LogManager.getLogger(BaseClass.class);

	// Load the configuration file
	@BeforeSuite
	public void loadConfig() throws IOException {
		prop = new Properties();
		FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
		prop.load(fis);
		logger.info("config.properties file loaded");
		
		//Start the extent report
//		ExtentManager.getReporter();
	}

	@BeforeMethod
	public synchronized void setup() throws IOException {
		System.out.println("Setting up WebDriver for:" + this.getClass().getSimpleName());
		launchBrowser();
		cofigureBrowser();
		staticWait(5);

		logger.info("WebDriver initialized and browser maximized");
		logger.trace("This is a trace message");
		logger.error("This is a error message");
		logger.debug("This is a debug message");
		logger.fatal("This is a fatal message");
		logger.warn("This is a warn message");

		// Initialize ActionDriver only for once for each test
		/*
		 * if (actionDriver == null) { actionDriver = new ActionDriver(driver);
		 * logger.info("ActionDrive instance is created"+
		 * Thread.currentThread().getId()); }
		 */

		// Initialize actionDriver for current thread
		actionDriver.set(new ActionDriver(getDriver()));
		logger.info("ActionDriver initialized for thread: " + Thread.currentThread().getId());

	}

	/*
	 * Initialize the browser base on the browser defined in the config.properties
	 * file
	 */
	private synchronized void launchBrowser() {

		String browser = prop.getProperty("browser");

		if (browser.equalsIgnoreCase("chrome")) {
//			driver = new ChromeDriver();
			driver.set(new ChromeDriver()); //New changes as per the thread
			ExtentManager.registerDriver(getDriver());
			logger.info("ChromeDriver instance is created");
		} else if (browser.equalsIgnoreCase("firefox")) {
//			driver = new FirefoxDriver();
			driver.set(new FirefoxDriver());  //New changes as per the thread
			ExtentManager.registerDriver(getDriver());
			logger.info("FirefoxDriver instance is created");
		} else if (browser.equalsIgnoreCase("edge")) {
//			driver = new EdgeDriver();
			driver.set(new EdgeDriver()); //New changes as per the thread
			ExtentManager.registerDriver(getDriver());
			logger.info("EdgeDriver instance is created");
		} else {
			throw new IllegalArgumentException("Browser not supported" + browser);
		}

	}

	/*
	 * Configure the browser setting such as wait, maximize and browser navigate to
	 * URL
	 */
	private void cofigureBrowser() {
		// Implicit wait
		int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

		// Maximize the browser
		getDriver().manage().window().maximize();

		// Navigate to URL
		try {
			getDriver().get(prop.getProperty("url"));
		} catch (Exception e) {
			logger.error("Failed to navigate to the url:" + e.getMessage());
		}
	}

	/*
	 * Static wait for pause LockSupport used why? 1.Fine-grained control over
	 * polling intervals 2.Cleaner code without checked exceptions 3.No checked
	 * exceptions → cleaner test code 4.Flexible granularity (seconds, millis,
	 * nanos) 5.Easy to reuse across your framework 6.Can be extended for polling
	 * waits (like checking element presence in a loop)
	 */
	public void staticWait(int seconds) {
		LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
	}

	// Get method for prop
	public static Properties getProp() {
		return prop;
	}
	/*
	 * // Driver getter method public WebDriver getDriver() { return driver; }
	 */

	// Getter method for WebDriver
	public static WebDriver getDriver() {
		if (driver.get() == null) {
			System.out.println("Webdriver is not initialized");
			throw new IllegalStateException("WebDriver is not initialized");
		}
		return driver.get();
	}

	// Getter method for WebDriver
	public static ActionDriver getActionDriver() {
		if (actionDriver.get() == null) {
			logger.error("ActionDriver is not initialized");
			throw new IllegalStateException("ActionDriver is not initialized");
		}
		return actionDriver.get();
	}

	// Driver setter method
	public void setDriver(ThreadLocal<WebDriver> driver) { 
		BaseClass.driver= driver; }

	@AfterMethod
	public synchronized void tearDown() {
		if (getDriver() != null) {
			try {
				getDriver().quit();
			} catch (Exception e) {
				logger.error("Unable to quite the browser:" + e.getMessage());
			}
			logger.info("WebDriver instance is closed");
			// driver = null;
			// actionDriver = null;

			driver.remove();
			actionDriver.remove();
//			ExtentManager.endTest();
		}
	}
}
