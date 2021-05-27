package com.personal.base;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.personal.utilities.ExtentReporter;


public class BaseClass {

	public static WebDriver driver;
	public static Logger logger = LogManager.getLogger(BaseClass.class);
	public static WebDriverWait d;
	
	public static FileInputStream fis;
	public static Properties configuration = new Properties();
	public static Properties objRepo = new Properties();
	public static String baseLoc = System.getProperty("user.dir");
	
	public static ExtentTest test;
	public static ExtentReports extentVar = ExtentReporter.configuration();
	
	
	@BeforeSuite
	public void setUp() throws IOException
	{
		
		
		fis = new FileInputStream(baseLoc + "\\src\\test\\resources\\properties\\Configuration.properties");
		configuration.load(fis);

		fis = new FileInputStream(baseLoc + "\\src\\test\\resources\\properties\\ObjectRepo.properties");
		objRepo.load(fis);
		
		String browser = configuration.getProperty("browser");
		
		if (browser.contains("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					baseLoc + "\\src\\test\\resources\\executables\\chromedriver.exe");
			driver = new ChromeDriver();

			driver.get(configuration.getProperty("AUT"));
			logger.info(configuration.getProperty("browser").toUpperCase() + 
					" BROWSER OPENED "+ configuration.getProperty("AUT") +  " WEBSITE SUCCESSFULLY");
		}
		
		else if (browser.contains("firefox")) {
			System.setProperty("webdriver.gecko.driver",
					baseLoc + "\\src\\test\\resources\\executables\\geckodriver.exe");
			driver = new FirefoxDriver();
			
			
			driver.get(configuration.getProperty("AUT"));
			logger.info(configuration.getProperty("browser").toUpperCase() + 
					" BROWSER OPENED "+ configuration.getProperty("AUT") +  " WEBSITE SUCCESSFULLY");
		}
		
		else {
			System.setProperty("webdriver.ie.driver",
					baseLoc + "\\src\\test\\resources\\executables\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			
			driver.get(configuration.getProperty("AUT"));
			logger.info(configuration.getProperty("browser").toUpperCase() + 
					" BROWSER OPENED "+ configuration.getProperty("AUT") +  " WEBSITE SUCCESSFULLY");
		}
		
		
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(configuration.getProperty("implicitlytimeout")),
				TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(Integer.parseInt(configuration.getProperty("pagetimeout")),
				TimeUnit.SECONDS);
		
		logger.debug("Implicit wait set to - " + configuration.getProperty("implicitlytimeout")
				+ "seconds   &  pageload timeout set to - " + configuration.getProperty("pagetimeout")+"seconds");
		
		driver.manage().window().maximize();
		logger.debug("window maximized");


	}
	
	
	@AfterSuite
	public void tearDown() {
		if (driver != null) {
			
			driver.quit();
			logger.info(configuration.getProperty("browser").toUpperCase() + 
					" BROWSER CLOSED");
			
		}
	}
	
	
	
// ---------------------------REUSEABLE METHODS --------------------------------------------------------------
		
	
	public void click(String key)  // REUSEABLE CLICK METHOD
	{
		
		// can't use switch here. switch can't be used for contains
		if(key.toLowerCase().contains("_css"))
		{
			
			driver.findElement(By.cssSelector(objRepo.getProperty(key))).click();
			
			
		}
		else if (key.toLowerCase().contains("_xpath"))
		{
			driver.findElement(By.xpath(objRepo.getProperty(key))).click();
			
		}
		else if (key.toLowerCase().contains("_id"))
		{
			driver.findElement(By.id(objRepo.getProperty(key))).click();
			
		}
		else if (key.toLowerCase().contains("_link"))
		{
			driver.findElement(By.linkText(objRepo.getProperty(key))).click();
			
		}
		
		else if (key.toLowerCase().contains("_partiallink"))
		{
			driver.findElement(By.partialLinkText(objRepo.getProperty(key))).click();
			
		}
		else if (key.toLowerCase().contains("_name"))
		{
			driver.findElement(By.name(objRepo.getProperty(key))).click();
			
		}
		else if (key.toLowerCase().contains("_class"))
		{
			driver.findElement(By.className(objRepo.getProperty(key))).click();
			
		}
		
		
		logger.debug("locator(" +key+ ") on page [title '" + driver.getTitle()+ "'] clicked successfully");
		test.log(Status.INFO, "locator(" +key+ ") on page [title '" + driver.getTitle()+ "'] clicked successfully");

		
	}
	
	
	public void checkPageTitle(String expectedtitle)  //REUSEABLE METHOD FOR CHECKING TITLE 
	{
		/*
		 * facing a logical problem with commented section code
		 * in happy scenario, logger.info is getting printed. where and how to place logger.error
		 * in unhappy path.
		 * I see no way out, restored to using if-else
		 */

		//Assert.assertEquals(driver.getTitle(), expectedtitle, expectedtitle, "title of Page is different, Actual title is"+driver.getTitle());
		//logger.info( "ASSERTED that title of Page :- '"+driver.getTitle()+ "' is correct");
		
		
		
		
		if (driver.getTitle().equals(expectedtitle)) {

			logger.info( "ASSERT :- '"+driver.getTitle()+ "' is correct title");
			test.log(Status.INFO,"ASSERT :- '"+driver.getTitle()+ "' is correct title");
		}
		else
		{
			
			//SOME OBSERVATION WHEN THINGS FAIL
			// keep Assert.fail as last statement**
			// no need to write test.log(Status.FAIL, "xyzxyzxyz") as Assert.fail + onTestFailure doing same
			
			
			logger.error( "ASSERT :- '"+driver.getTitle()+ "' is INCORRECT TITLE");
			
			
			//test.log(Status.FAIL,"ASSERTED that title of Page :- '"+driver.getTitle()+ "' is INCORRECT");
			Assert.fail("ASSERT :- '"+driver.getTitle()+ "' is INCORRECT TITLE");
		}
			
		
		
	}
	
	
	public void isElementPresent(String key) { // REUSEABLE ELEMENT AVAILABILITY METHOD
		
		try {
				if(key.toLowerCase().contains("_css"))
				{	
					driver.findElement(By.cssSelector(objRepo.getProperty(key)));	// no isDisplayed or isSelected etc etc
				}
				else if (key.toLowerCase().contains("_xpath"))
				{
					driver.findElement(By.xpath(objRepo.getProperty(key)));	
				}
				else if (key.toLowerCase().contains("_id"))
				{
					driver.findElement(By.id(objRepo.getProperty(key)));	
				}
				else if (key.toLowerCase().contains("_link"))
				{
					driver.findElement(By.linkText(objRepo.getProperty(key)));	
				}
				
				else if (key.toLowerCase().contains("_partiallink"))
				{
				  driver.findElement(By.partialLinkText(objRepo.getProperty(key)));	
				}
				else if (key.toLowerCase().contains("_name"))
				{
				driver.findElement(By.name(objRepo.getProperty(key)));	
				}
				else if (key.toLowerCase().contains("_class"))
				{
					driver.findElement(By.className(objRepo.getProperty(key)));
				}
				
				
				
		logger.info("ASSERT :- locator(" +key+ ") is present on page [title '" + driver.getTitle()+"']");
		test.log(Status.INFO, "ASSERT :- locator(" +key+ ") is present on page [title '" + driver.getTitle()+"']");
		
		
		}
		 catch (NoSuchElementException e) { // NoSuchElementException is apt for here
			

				//SOME OBSERVATION WHEN THINGS FAIL
				// keep Assert.fail as last statement**
				// no need to write test.log(Status.FAIL, "xyzxyzxyz") as Assert.fail + onTestFailure doing same
			 
			 
		 logger.error("ASSERT :- locator(" +key+ ") is NOT present on page [title '" + driver.getTitle()+"']");
		 Assert.fail("ASSERT :- locator(" +key+ ") is NOT present on page [title '" + driver.getTitle()+"']");
		 }
	}
	
	
	
	// WORK ON THESSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSEEEEEEEEEEEEEEEE

	
	public boolean doesAlertContains(String message) { // REUSEABLE ALERT TEXT AVAILABILITY METHOD

			try {
				if (driver.switchTo().alert().getText().contains(message)) {
					logger.info("customer added successfuly");
					return true;
				} else {
					logger.debug("customer NOT added as alert text is different");
					return false;
				}

				
				
			} catch (NoAlertPresentException e) { // ****NoAlertPresentException is apt for here
				logger.error("no alert present on screen");
				return false;
			}
			
		}

	
	public WebElement ExplicitlyWaiting(int waitPeriod, String nameOfCondition, By byVariableOfElement) {

			d = new WebDriverWait(driver, waitPeriod);
			//here we wouldn't be needing breaks, rare situations, i think
			
			switch (nameOfCondition) {
			case "elementToBeClickable": {
				WebElement element=d.until(ExpectedConditions.elementToBeClickable(byVariableOfElement));
				logger.debug("gave elementToBeClickable timeout as "+ waitPeriod);
				return element;
			}
			
			case "presenceOfElementLocated": {
				WebElement element=d.until(ExpectedConditions.presenceOfElementLocated(byVariableOfElement));
				logger.debug("gave presenceOfElementLocated timeout as "+ waitPeriod);
				return element;
				
			}
			case "alertIsPresent": {
				d.until(ExpectedConditions.alertIsPresent());	
				logger.debug("gave alertIsPresent timeout as "+ waitPeriod);
				return null;
			}
			default:{
				logger.error(" You provided wrong condition \n In ExplicitlyWaiting(int waitPeriod, String nameOfCondition, By byVariableOfElement) following conditions are used :- \n 1.elementToBeClickable \n 2.presenceOfElementLocated \n 3.alertIsPresent");
				break;}

			}
			return null;
			
			
		}
}

