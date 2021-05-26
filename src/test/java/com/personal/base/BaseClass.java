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


public class BaseClass {

	public static WebDriver driver;
	public static Logger logger = LogManager.getLogger(BaseClass.class);
	public static WebDriverWait d;
	
	public static FileInputStream fis;
	public static Properties configuration = new Properties();
	public static Properties objRepo = new Properties();
	public static String baseLoc = System.getProperty("user.dir");
	
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
		
	
	public void click(String key)
	{
		
		// can't use switch here. can't be used for contains
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
		
		
		logger.debug("locator(" +key+ ") on page with title '" + driver.getTitle()+ "' clicked successfully");
	}
	
	
	
	public void checkPageTitle(String expectedtitle)
	{
		/*
		 * facing a logical problem with commented section 
		 * in happy scenario, logger.info is getting printed. where to place logger.error to
		 * NOT so happy path.
		 * I see no way out, restored to using if-else
		 */
		
		
		
		//Assert.assertEquals(driver.getTitle(), expectedtitle, expectedtitle, "title of Page is different, Actual title is"+driver.getTitle());
		//logger.info( "ASSERTED that title of Page :- '"+driver.getTitle()+ "' is correct");
		
		
		
		if (driver.getTitle().equals(expectedtitle)) {

			logger.info( "ASSERTED that title of Page :- '"+driver.getTitle()+ "' is correct");
		}
		else
		{
			Assert.fail("title of Page :- '"+driver.getTitle()+ "' is INCORRECT");
			logger.error( "ASSERTED that title of Page :- '"+driver.getTitle()+ "' is INCORRECT");
		}
			
		
		
	}
	
	
	public void isElementPresent(String key) {
		
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
				
				
				
		logger.info("ASSERTED that locator(" +key+ ") is present on page with title '" + driver.getTitle()+"'");
		
		}
		 catch (NoSuchElementException e) { // NoSuchElementException is apt for here
			 
		 logger.error("ASSERTED that locator(" +key+ ") is NOT present on page with title '" + driver.getTitle()+"'");
			 
		 //ItestListeners will catch Assert.fail
		 Assert.fail("locator(" +key+ ") is NOT present on page with title '" + driver.getTitle()+"'");
		}
	}

		public boolean doesAlertContains(String message) {

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

		//use overloading to produce more of this method
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

