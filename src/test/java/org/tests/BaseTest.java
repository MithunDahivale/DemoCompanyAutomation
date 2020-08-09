package org.tests;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.common.ExcelUtility;
import org.common.Messages;
import org.common.Utility;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.PageFactory;
import org.pages.LoginPage;
import org.pages.SignOutPage;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

/**
 * Base Test All Test classes to be extended to this class
 */
public class BaseTest {
	public WebDriver webDriver = null;
	Map<String, String> propertiesMap;
	public Logger log;
	public ExtentReports extent;
	public ExtentTest logger;
	public Method method;
	File file;

	@BeforeSuite
	public void setUp() throws Exception {
		try {
			
			// Setup Logging
			PropertyConfigurator.configure(System.getProperty("user.dir") + "/config/log4j.properties");
			System.setProperty("my.log", System.getProperty("user.dir"));
			log = Logger.getLogger(this.getClass().getSimpleName());
			log.info("SetUp Begins @BeforeSuite");
			// Setup Reporting
			ExtentHtmlReporter reporter = new ExtentHtmlReporter(
					System.getProperty("user.dir") + "/TestLogs/ExtentReport.html");
			extent = new ExtentReports();
			extent.attachReporter(reporter);

			
			// Read data from properties file
			Properties properties = new Properties();
			properties.load(new FileInputStream(System.getProperty("user.dir") + "/config/config.properties"));

			propertiesMap = new HashMap<String, String>();
			for (Entry<Object, Object> property : properties.entrySet()) {
				propertiesMap.put(property.getKey().toString(), property.getValue().toString());
			}

			log.info("Logging and Reporting setup done");
		} catch (Exception exception) {
			log.info("SetUp Failed");
			log.error(exception);
			logger.log(Status.FAIL, "SetUp Failed");
			throw exception;
		}
	}

	@BeforeTest
	public void browserSetUp() {
		try {
			log.info("BrowserSetUp Begins @BeforeTest");

			logger = extent.createTest(this.getClass().getSimpleName());

			// set up Browser
			switch (propertiesMap.get("browser").toUpperCase()) {
			case "FIREFOX":
				log.info("SetUp FireFox Browser");
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + propertiesMap.get("driverPath") + "//geckodriver.exe");
				File pathBinary = new File(propertiesMap.get("firefoxPath") + "//firefox.exe");
				FirefoxBinary firefoxBinary = new FirefoxBinary(pathBinary);
				FirefoxOptions options = new FirefoxOptions();
				options.setBinary(firefoxBinary);
				options.setCapability("marionette", true);
				webDriver = new FirefoxDriver(options);
				break;
			case "CHROME":
				break;
			default:
				break;
			}
			webDriver.get(propertiesMap.get("baseurl"));
			log.info("Browser Launched Successfully");
			webDriver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
			webDriver.manage().window().maximize();
			webDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

			// Create a directory to capture screenshots
			Date date = new Date();
			file = new File(System.getProperty("user.dir") + "/TestLogs/Snapshots_" + date.getTime());
			boolean bool = file.mkdir();
			if (bool == false) {
				log.info("Issue while creating snapshot directory");
			}

			log.info("Browser SetUp Success");
			logger.log(Status.PASS, "Browser SetUp Success");
		} catch (Exception exception) {
			log.info("Browser SetUp Failed");
			log.error(exception);
			logger.log(Status.FAIL, "Browser Setup Failed");
			throw exception;
		}
	}

	/**
	 * Login to application
	 */
	public void logIn() throws Exception {
		try {
			log.info("Login Begins");

			// Login into Xero
			LoginPage loginPage = PageFactory.initElements(webDriver, LoginPage.class);
			loginPage.enterUserName(propertiesMap.get("userName"));
			loginPage.enterPassword(propertiesMap.get("password"));
			loginPage.clickOnLogin();

			// Verify the title
			Utility.waitUntilTitleContains(webDriver, "Dashboard");
			String title = Utility.replaceAll(webDriver.getTitle());
			Assert.assertEquals(title, Messages.DASHBOARD_WINDOW_TITLE);
			Utility.takeSnapShot(webDriver, file + "//login.png");
			log.info("Login Success");
			logger.log(Status.PASS, "Login Success");

		} catch (Exception exception) {
			log.info("Login Failed");
			log.error(exception);
			logger.log(Status.FAIL, "Login Failed");
			Utility.takeSnapShot(webDriver, file + "//login.png");
			throw exception;
		}
	}

	/**
	 * Signout from the application
	 */
	public void signOut() throws Exception {

		try {
			log.info("Signout Begins");
			SignOutPage signoutPage = PageFactory.initElements(webDriver, SignOutPage.class);
			// logout
			signoutPage.clickOnUserAddOn();
			signoutPage.clickOnLogOut();
			Utility.waitUntilTitleContains(webDriver, "Login");
			Utility.takeSnapShot(webDriver, file + "//signout.png");
			log.info("Signout Success");
			logger.log(Status.PASS, "Signout Success");

		} catch (Exception exception) {
			log.info("Signout Failed");
			log.error(exception);
			logger.log(Status.FAIL, "Signout Failed");
			Utility.takeSnapShot(webDriver, file + "//sigout.png");
			throw exception;
		}
	}

	@AfterTest
	public void closebrowser() {
		// close the browser after every @test is executed
		try {
			log.info("Closing Browser");
			if (webDriver != null) {
				webDriver.close();
				log.info("Browser closed successfully");
				logger.log(Status.PASS, "Browser closed successfully");
			}
		} catch (Exception exception) {
			log.info("Closebrowser Failed");
			log.error(exception);
			logger.log(Status.FAIL, "Closing Browser Failed");
			throw exception;
		}
	}

	@AfterSuite
	public void tearDown() {
		try {
			log.info("AfterSuite Begins");

			log.info("Test Report using Extend generated at location: " + System.getProperty("user.dir")
					+ "\\TestLogs\\ExtendReport.html");
			logger.info("Snapshots generated at location: " + System.getProperty("user.dir") + "\\TestLogs");
			log.info("Snapshots generated at location: " + System.getProperty("user.dir") + "\\TestLogs");

			
			extent.flush();
		} catch (Exception exception) {
			log.info("@AfterSuite Failed");
			log.error(exception);
			logger.log(Status.FAIL, "@AfterSuite Failed");
			throw exception;
		}
	}

	/**
	 * Read Test Data
	 * 
	 * @param method
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "readData")
	public Object[][] readExcel(Method method) throws Exception {
		try {
			log.info("Reading Test Data from Excel");
			String className = this.getClass().getSimpleName();
			String methodName = method.getName();

			String excelPath = System.getProperty("user.dir") + "/resources/TestData/" + className + ".xlsx";

			ExcelUtility excelUtility = new ExcelUtility(excelPath, methodName);

			int rowCount = excelUtility.getRowCount();
			int columnCount = excelUtility.getColumnCount();

			Object[][] objArray = new Object[rowCount - 1][columnCount];

			for (int row = 1; row < rowCount; row++) {
				for (int column = 0; column < columnCount; column++) {
					String cellData = excelUtility.getCellData(row, column);
					objArray[row - 1][column] = cellData;
				}
			}
			return objArray;
		} catch (Exception exception) {
			log.info("Reading Test Data from Excel Failed");
			log.error(exception);
			logger.log(Status.FAIL, "Reading Test Data from Excel Failed");
			throw exception;
		}
	}
}
