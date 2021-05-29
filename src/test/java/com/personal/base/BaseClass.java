package com.personal.base;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.personal.utilities.ExtentReporter;
import com.personal.utilities.MailUtility;



public class BaseClass {

	public static WebDriver driver;
	public static Logger logger = LogManager.getLogger(BaseClass.class);
	public static FileInputStream fis;
	public static Properties configuration = new Properties();
	public static Properties objRepo = new Properties();
	public static String baseLoc = System.getProperty("user.dir");
	public static ExtentTest test;
	public static ExtentReports extentVar = ExtentReporter.configuration();
	public static MailUtility mail;
	
	
	@BeforeSuite
	public void setUp() throws IOException
	{
		
		
		// java is unable to delete the log4j file as "it is being used by another process"
		/*
		 * try { ClearFolders.delete(); } catch (IOException e) { e.printStackTrace();
		 * logger.error("unable to delete the contents of folder due to "+
		 * e.getMessage()); }
		 */ 
		
		
		
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
			
			logger.info("-------------------------------------------------------------------------------------------------");
			
		}
	}
	
	
	
// ---------------------------REUSEABLE METHODS --------------------------------------------------------------
		
	
	public void click(String key)  // REUSEABLE CLICK METHOD
	{
		
		// can't use switch here. switch can't be used for contains
		if(key.toLowerCase().contains("_css") )
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
		
		// **** DON'T WRITE driver.getTitle(), in case there is a alert box. you will get error on driver.getTitle()
		
		logger.debug("locator(" +key+ ") clicked successfully");
		test.log(Status.INFO, "locator(" +key+ ") clicked successfully");

	}

	public void type(String key, String data)  // REUSEABLE TYPE METHOD
	{
		
		// can't use switch here. switch can't be used for contains
		if(key.toLowerCase().contains("_css"))
		{
			
			driver.findElement(By.cssSelector(objRepo.getProperty(key))).sendKeys(data);
			
			
		}
		else if (key.toLowerCase().contains("_xpath"))
		{
			driver.findElement(By.xpath(objRepo.getProperty(key))).sendKeys(data);
			
		}
		else if (key.toLowerCase().contains("_id"))
		{
			driver.findElement(By.id(objRepo.getProperty(key))).sendKeys(data);
			
		}
		else if (key.toLowerCase().contains("_link"))
		{
			driver.findElement(By.linkText(objRepo.getProperty(key))).sendKeys(data);
			
		}
		
		else if (key.toLowerCase().contains("_partiallink"))
		{
			driver.findElement(By.partialLinkText(objRepo.getProperty(key))).sendKeys(data);
			
		}
		else if (key.toLowerCase().contains("_name"))
		{
			driver.findElement(By.name(objRepo.getProperty(key))).sendKeys(data);
			
		}
		else if (key.toLowerCase().contains("_class"))
		{
			driver.findElement(By.className(objRepo.getProperty(key))).sendKeys(data);
			
		}
		
		
		logger.debug("locator(" +key+ ") filled with value '"+ data+"' successfully");
		test.log(Status.INFO, "locator(" +key+ ") filled with value '"+ data+"' successfully");

		
	}

	public void  alert(String action)  // RESUABLE ACTION METHOD
	{
		Alert al= driver.switchTo().alert();
		
		if (action.toLowerCase().contains("accept")) {
			al.accept();
			
		}
		else if (action.toLowerCase().contains("dismiss")) {
			al.dismiss();
			
		}
		
		else if (action.toLowerCase().contains("sendKeys")) {
			al.sendKeys(action);
			
		}
		else if(action.toLowerCase().contains("getText"))
		System.out.println(al.getText());
		
		
		logger.debug(" Action("+ action +") successfully taken on alert, present on page");
		test.log(Status.INFO, (" Action("+ action +") successfully taken on alert, present on page "));
	}
	
	public void staticDropDown(String key, String selectOption)  // REUSEABLE CLICK METHOD
	{
		
		Select s;
		
		// can't use switch here. switch can't be used for contains
		if(key.toLowerCase().contains("_css") )
		{
			s = new Select(driver.findElement(By.cssSelector(objRepo.getProperty(key))));
			s.selectByVisibleText(selectOption);

		}

		else if (key.toLowerCase().contains("_xpath"))
		{
			s = new Select(driver.findElement(By.xpath(objRepo.getProperty(key))));
			s.selectByVisibleText(selectOption);	
		}
		else if (key.toLowerCase().contains("_id"))
		{
			s = new Select(driver.findElement(By.id(objRepo.getProperty(key))));
			s.selectByVisibleText(selectOption);
		}
		else if (key.toLowerCase().contains("_link"))
		{
			s = new Select(driver.findElement(By.linkText(objRepo.getProperty(key))));
			s.selectByVisibleText(selectOption);
		}
		
		else if (key.toLowerCase().contains("_partiallink"))
		{
			s = new Select(driver.findElement(By.partialLinkText(objRepo.getProperty(key))));
			s.selectByVisibleText(selectOption);
		}
		else if (key.toLowerCase().contains("_name"))
		{
			s = new Select(driver.findElement(By.name(objRepo.getProperty(key))));
			s.selectByVisibleText(selectOption);
		}
		else if (key.toLowerCase().contains("_class"))
		{
			s = new Select(driver.findElement(By.className(objRepo.getProperty(key))));
			s.selectByVisibleText(selectOption);
		}
		
		// **** DON'T WRITE driver.getTitle(), in case there is a alert box. you will get error on driver.getTitle()
		
		logger.debug("option(" +selectOption+ ") selected successfully on dropdown("+key+")");
		test.log(Status.INFO, "option(" +selectOption+ ") selected successfully on dropdown("+key+")");

	}
	
	
	

	
// -------------------------------EXPLCIT WAIT REUSEABLE METHODS---------------------------	
	
	
			

	public void click(String key, int waitPeriod, String nameOfCondition)  // REUSEABLE EXPLICIT WAIT CLICK METHOD
	{
		
		WebDriverWait d = new WebDriverWait(driver, waitPeriod);
		By byVar=null;
		
		
		if(key.toLowerCase().contains("_css"))
		{
			
			byVar=By.cssSelector(objRepo.getProperty(key));
			
		}
		else if (key.toLowerCase().contains("_xpath"))
		{
			byVar=By.xpath(objRepo.getProperty(key));
		}
		else if (key.toLowerCase().contains("_id"))
		{
			byVar=By.id(objRepo.getProperty(key));
		}
		else if (key.toLowerCase().contains("_link"))
		{
			byVar=By.linkText(objRepo.getProperty(key));
		}
		
		else if (key.toLowerCase().contains("_partiallink"))
		{
			byVar=By.partialLinkText(objRepo.getProperty(key));
		}
		else if (key.toLowerCase().contains("_name"))
		{
			byVar=By.name(objRepo.getProperty(key));
		}
		else if (key.toLowerCase().contains("_class"))
		{
			byVar=By.className(objRepo.getProperty(key));
		}
		
		
		
		switch (nameOfCondition) {
		case "elementToBeClickable": {
			 d.until(ExpectedConditions.elementToBeClickable(byVar)).click();
			logger.debug("elementToBeClickable timeout for locator(" +key+ ") set to "+ waitPeriod+" seconds. locator(" +key+ ") clicked successfully");
			test.log(Status.INFO, "elementToBeClickable timeout for locator(" +key+ ") set to "+ waitPeriod+" seconds. locator(" +key+ ") clicked successfully");
			break;
			
		}
		
		case "visibilityOfElementLocated": {
			
			
			d.until(ExpectedConditions.visibilityOfElementLocated(byVar)).click();		
			logger.debug("visibilityOfElementLocated timeout for locator(" +key+ ") set to "+ waitPeriod+" seconds. locator(" +key+ ") clicked successfully");
			test.log(Status.INFO, "visibilityOfElementLocated timeout for locator(" +key+ ") set to "+ waitPeriod+" seconds. locator(" +key+ ") clicked successfully");
			
			break;
		}
		
		case "presenceOfElementLocated": {
			 d.until(ExpectedConditions.presenceOfElementLocated(byVar)).click();
			 
			logger.debug("presenceOfElementLocated timeout for locator(" +key+ ") set to "+ waitPeriod+" seconds. locator(" +key+ ") clicked successfully");
			test.log(Status.INFO, "presenceOfElementLocated timeout for locator(" +key+ ") set to "+ waitPeriod+" seconds. locator(" +key+ ") clicked successfully");
			
			break;
		}
		
		
		default:{
			logger.error(" user provided wrong condition \n for click(String key, int waitPeriod, String nameOfCondition). Following conditions are used :- \n 1.elementToBeClickable \n 2.presenceOfElementLocated \n 3.visibilityOfElementLocated");
			Assert.fail(" user provided wrong condition \n for click(String key, int waitPeriod, String nameOfCondition). Following conditions are used :- \n 1.elementToBeClickable \n 2.presenceOfElementLocated \n 3.visibilityOfElementLocated");
			
			break;}

		}
		
		

	}
			
	public void type(String key, String data, int waitPeriod, String nameOfCondition)  // REUSEABLE EXPLICIT WAIT TYPE METHOD
	{
		
		WebDriverWait d = new WebDriverWait(driver, waitPeriod);
		By byVar=null;
		
		
		if(key.toLowerCase().contains("_css"))
		{
			
			byVar=By.cssSelector(objRepo.getProperty(key));
			
		}
		else if (key.toLowerCase().contains("_xpath"))
		{
			byVar=By.xpath(objRepo.getProperty(key));
		}
		else if (key.toLowerCase().contains("_id"))
		{
			byVar=By.id(objRepo.getProperty(key));
		}
		else if (key.toLowerCase().contains("_link"))
		{
			byVar=By.linkText(objRepo.getProperty(key));
		}
		
		else if (key.toLowerCase().contains("_partiallink"))
		{
			byVar=By.partialLinkText(objRepo.getProperty(key));
		}
		else if (key.toLowerCase().contains("_name"))
		{
			byVar=By.name(objRepo.getProperty(key));
		}
		else if (key.toLowerCase().contains("_class"))
		{
			byVar=By.className(objRepo.getProperty(key));
		}
		
		
		
		switch (nameOfCondition) {
		case "elementToBeClickable": {
			 d.until(ExpectedConditions.elementToBeClickable(byVar)).sendKeys(data);
			
			
			logger.debug("elementToBeClickable timeout for locator(" +key+ ") set to "+ waitPeriod+" seconds. locator(" +key+ ") filled with value '"+ data+"' successfully");
			test.log(Status.INFO,"elementToBeClickable timeout for locator(" +key+ ") set to "+ waitPeriod+" seconds. locator(" +key+ ") filled with value '"+ data+"' successfully");
			
			break;
			
		}
		
		case "visibilityOfElementLocated": {
			
			
			d.until(ExpectedConditions.visibilityOfElementLocated(byVar)).sendKeys(data);
			
			
			logger.debug("visibilityOfElementLocated timeout for locator(" +key+ ") set to "+ waitPeriod+" seconds. locator(" +key+ ") filled with value '"+ data+"' successfully");
			test.log(Status.INFO,"visibilityOfElementLocated timeout for locator(" +key+ ") set to "+ waitPeriod+" seconds. locator(" +key+ ") filled with value '"+ data+"' successfully");
			
			break;
		}
		
		case "presenceOfElementLocated": {
			 d.until(ExpectedConditions.presenceOfElementLocated(byVar)).sendKeys(data);

			 logger.debug("presenceOfElementLocated timeout for locator(" +key+ ") set to "+ waitPeriod+" seconds. locator(" +key+ ") filled with value '"+ data+"' successfully");
			 test.log(Status.INFO,"presenceOfElementLocated timeout for locator(" +key+ ") set to "+ waitPeriod+" seconds. locator(" +key+ ") filled with value '"+ data+"' successfully");
				
			break;
		}
		
		
		default:{
			logger.error(" user provided wrong condition \n for type(String key, String data, int waitPeriod, String nameOfCondition). Following conditions are used :- \n 1.elementToBeClickable \n 2.presenceOfElementLocated \n 3.visibilityOfElementLocated");
			Assert.fail(" user provided wrong condition \n for type(String key, String data, int waitPeriod, String nameOfCondition). Following conditions are used :- \n 1.elementToBeClickable \n 2.presenceOfElementLocated \n 3.visibilityOfElementLocated");
			
			break;}

		}

	}

	public void alert(String action, int waitPeriod)  // REUSEABLE EXPLICIT WAIT ALERT METHOD
	{
		
		WebDriverWait d = new WebDriverWait(driver, waitPeriod);

		
		
		if (action.toLowerCase().contains("accept")) {
			 d.until(ExpectedConditions.alertIsPresent()).accept();
			 
			 logger.debug("alertIsPresent timeout for Alert set to "+ waitPeriod+" seconds. Action("+action+") taken on Alert Successfully");
			 test.log(Status.INFO, "alertIsPresent timeout Alert set to "+ waitPeriod+" seconds. Action("+action+") taken on Alert Successfully");
			
		}
		else if (action.toLowerCase().contains("dismiss")) {
			d.until(ExpectedConditions.alertIsPresent()).dismiss();
			 logger.debug("alertIsPresent timeout for Alert set to "+ waitPeriod+" seconds. Action("+action+") taken on Alert Successfully");
			 test.log(Status.INFO, "alertIsPresent timeout Alert set to "+ waitPeriod+" seconds. Action("+action+") taken on Alert Successfully");
			
		}
		
		else if (action.toLowerCase().contains("sendKeys")) {
			d.until(ExpectedConditions.alertIsPresent()).sendKeys(action);
			 logger.debug("alertIsPresent timeout for Alert set to "+ waitPeriod+" seconds. Action("+action+") taken on Alert Successfully");
			 test.log(Status.INFO, "alertIsPresent timeout Alert set to "+ waitPeriod+" seconds. Action("+action+") taken on Alert Successfully");
			
		}
		else if(action.toLowerCase().contains("getText"))
		{
			System.out.println(d.until(ExpectedConditions.alertIsPresent()).getText());
			logger.debug("alertIsPresent timeout for Alert set to "+ waitPeriod+" seconds. Action("+action+") taken on Alert Successfully");
			test.log(Status.INFO, "alertIsPresent timeout Alert set to "+ waitPeriod+" seconds. Action("+action+") taken on Alert Successfully");}
		
		else {
			logger.error(" user provided wrong action \n for alert(String action, int waitPeriod). Following action are used :- \n 1.dismiss \n 2. accept  \n 3. sendKeys \n 4. getText ");
			Assert.fail(" user provided wrong action \n for alert(String action, int waitPeriod). Following action are used :- \n 1.dismiss \n 2. accept  \n 3. sendKeys \n 4. getText ");
			
		}
		

	}
	
	


// -----------------------------------SOFT/HARD ASSERT IMPLEMENTATION


public void checkPageTitle(String expectedtitle, String assertType)  //REUSEABLE METHOD FOR CHECKING TITLE 
{
	
	
	
	if (driver.getTitle().equals(expectedtitle) && assertType.toLowerCase().contains("hardassert")) {

		logger.info( "ASSERT :- '"+driver.getTitle()+ "' is correct title");
		test.log(Status.PASS,"ASSERT :- '"+driver.getTitle()+ "' is correct title");
	}
	else if (!(driver.getTitle().equals(expectedtitle)) && assertType.toLowerCase().contains("hardassert"))
	{
		// **** keep Assert.fail as last statement**
		//**** no need to write test.log(Status.FAIL, "xyzxyzxyz") as Assert.fail + onTestFailure doing same
		
		
		logger.error( "ASSERT :- '"+driver.getTitle()+ "' is INCORRECT TITLE");
		
		//test.log(Status.FAIL,"ASSERTED that title of Page :- '"+driver.getTitle()+ "' is INCORRECT");
		Assert.fail("ASSERT :- '"+driver.getTitle()+ "' is INCORRECT TITLE");
	}
	
	else if (driver.getTitle().equals(expectedtitle) && assertType.toLowerCase().contains("softassert")) {

		logger.info( "VERIFY :- '"+driver.getTitle()+ "' is correct title");
		test.log(Status.PASS,"VERIFY :- '"+driver.getTitle()+ "' is correct title");
	}
	else if (!(driver.getTitle().equals(expectedtitle)) && assertType.toLowerCase().contains("softassert"))
	{
		// **** keep Assert.fail as last statement**
		//**** no need to write test.log(Status.FAIL, "xyzxyzxyz") as Assert.fail + onTestFailure doing same
		
		
		logger.error( "VERIFY :- '"+driver.getTitle()+ "' is INCORRECT TITLE");
		test.log(Status.ERROR,"VERIFY :- '"+driver.getTitle()+ "' is INCORRECT TITLE");
		
	}
	
		
	
	
}

public void isElementPresent(String key, String assertType) { // REUSEABLE ELEMENT AVAILABILITY METHOD
	
	
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
			
			
			if(assertType.toLowerCase().contains("hardassert"))
			{	
				logger.info("ASSERT :- locator(" +key+ ") is present on page");
				test.log(Status.PASS, "ASSERT :- locator(" +key+ ") is present on page");
			}
			else if(assertType.toLowerCase().contains("softassert"))
			 {
				
				logger.info("VERIFY :- locator(" +key+ ") is present on page");
				test.log(Status.PASS, "VERIFY :- locator(" +key+ ") is present on page");
			 }
	
	
	}
	 catch (NoSuchElementException e) { // NoSuchElementException is apt for here
		

		
	// **** keep Assert.fail as last statement**
	// **** no need to write test.log(Status.FAIL, "xyzxyzxyz") as Assert.fail + onTestFailure doing same
		 if(assertType.toLowerCase().contains("hardassert"))
			{
			 
			 logger.error("ASSERT :- locator(" +key+ ") is NOT present on page. error msg is "+ e.getMessage());
			 Assert.fail("ASSERT :- locator(" +key+ ") is NOT present on page. error msg is "+ e.getMessage());
			
			}
		 
		 else if(assertType.toLowerCase().contains("softassert"))
		 {
			 logger.error("VERIFY :- locator(" +key+ ") is NOT present on page. error msg is "+ e.getMessage());
			 test.log(Status.ERROR,"VERIFY :- locator(" +key+ ") is NOT present on page. error msg is "+ e.getMessage());
			 
		 }
   
	 
	 }
	

	
	
}

public void doesAlertContainsText(String message, String assertType) { // REUSEABLE ALERT TEXT AVAILABILITY METHOD
	
		try {
			if (driver.switchTo().alert().getText().contains(message) && assertType.toLowerCase().contains("hardassert")) {
				
				logger.info("ASSERT :- text(" +message+ ") is present in Alert Box");
				test.log(Status.PASS, "ASSERT :- text(" +message+ ") is present in Alert Box");
	
			} 
			
			else if (!(driver.switchTo().alert().getText().contains(message)) && assertType.toLowerCase().contains("hardassert")) {
				
				logger.error("ASSERT :- text(" +message+ ") is NOT present in Alert Box ");
				Assert.fail("ASSERT :- text(" +message+ ") is NOT present in Alert Box ");
	
			} 
			
			else if(driver.switchTo().alert().getText().contains(message) && assertType.toLowerCase().contains("softassert")) {
				
				logger.info("VERIFY :- text(" +message+ ") is present in Alert Box");
				test.log(Status.PASS, "VERIFY :- text(" +message+ ") is present in Alert Box");	
					
			}	
			else if(!(driver.switchTo().alert().getText().contains(message)) && assertType.toLowerCase().contains("softassert")) 
			{
					
						logger.error("VERIFY :- text(" +message+ ") is NOT present in Alert Box ");
						test.log(Status.ERROR, "VERIFY :- text(" +message+ ") is NOT present in Alert Box ");
				}
			
		}
			
			
		 catch (NoAlertPresentException e) { // ****NoAlertPresentException is apt for here
			
			
				logger.error("NO alert is present on screen. error message is "+e.getMessage());
				Assert.fail("NO alert is present on screen. error message is "+e.getMessage());
			}
		
		
	}
}

