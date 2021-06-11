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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

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
	
	
	Locators loc;
	
	@BeforeTest
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
					" browser opened '"+ configuration.getProperty("AUT") +  "' website successfully");
		}
		
		else if (browser.contains("firefox")) {
			System.setProperty("webdriver.gecko.driver",
					baseLoc + "\\src\\test\\resources\\executables\\geckodriver.exe");
			driver = new FirefoxDriver();
			
			
			driver.get(configuration.getProperty("AUT"));
			logger.info(configuration.getProperty("browser").toUpperCase() + 
					" browser opened '"+ configuration.getProperty("AUT") +  "' website successfully");
		}
		
		else {
			System.setProperty("webdriver.ie.driver",
					baseLoc + "\\src\\test\\resources\\executables\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			
			driver.get(configuration.getProperty("AUT"));
			logger.info(configuration.getProperty("browser").toUpperCase() + 
					" browser opened '"+ configuration.getProperty("AUT") +  "' website successfully");
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
	
	
	@AfterTest
	public void tearDown() {
		if (driver != null) {
			
			driver.quit();
			logger.info(configuration.getProperty("browser").toUpperCase() + 
					" BROWSER CLOSED");
			
			logger.debug("-------------------------------------------------------------------------------------------------");
			
		}
	}
	
	
//-----------------------------------------------------------------------------------------------------------------------	
// ----------------------REUSEABLE METHODS -----------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------------		
	
	public void click(String key)  // REUSEABLE CLICK METHOD
	{
		loc= new Locators();
		driver.findElement(loc.locator(key, "click", null)).click();
		loc.passMsg(key, "click", null, null, 0);
	}

	
	public void type(String key, String data)  // REUSEABLE TYPE METHOD
	{
		
		
		loc= new Locators();
		driver.findElement(loc.locator(key, "type", data)).sendKeys(data);
		loc.passMsg(key, "type", data, null, 0);
	
		
	}

	
	public void staticDropDown(String key, String selectOption)  // REUSEABLE STATIC DROPDOWN METHOD
	{
		
		
		loc= new Locators();
		
		Select s = new Select(driver.findElement(loc.locator(key, "staticDropDown", selectOption)));
		loc.checkDropDown(s, selectOption, key);		// I can't place this checks, in any other method. so it's stand alone
		s.selectByVisibleText(selectOption);

		loc.passMsg(key, "staticDropDown", selectOption, null, 0);
		
	}
	
	
	public void  alert(String actionOnAlert)  // RESUABLE ALERT METHOD
	{
		loc= new Locators();
		try {
			Alert al= driver.switchTo().alert();
			
			if (actionOnAlert.toLowerCase().contains("accept")) {
				al.accept();
				
			}
			else if (actionOnAlert.toLowerCase().contains("dismiss")) {
				al.dismiss();
				
			}
			
			else if(actionOnAlert.toLowerCase().contains("getText"))
			System.out.println(al.getText());
			
			else
			{
				al.accept();    //if I don't give this I get unhandleable exception in next run of dataprovider methods
				logger.error(" user provided wrong action ["+actionOnAlert+"] for alert(String action)."
						+ "\n Following conditions are acceptable :- \n \t 1.accept \n \t 2.dismiss \n \t 3.getText");
				
				Assert.fail(" user provided wrong action["+actionOnAlert+"] for alert(String action)."
						+ "\n Following conditions are acceptable :- \n \t 1.accept \n \t 2.dismiss \n \t 3.getText");
			}
				
			
			
			loc.passMsg(null, "alert", actionOnAlert,  null, 0);
			
			
		}
			catch (NoAlertPresentException e) {
				
				loc.failmsg(null, "alert", actionOnAlert,e.toString());	
				
			}
		}
	

	
//-----------------------------------------------------------------------------------------------------------------------
// ---------------------EXPLCIT WAIT REUSEABLE METHODS-------------------------------------------------------------------	
//-----------------------------------------------------------------------------------------------------------------------	
	
			
	
	
	public void click(String key, int waitPeriod, String nameOfCondition)  // REUSEABLE EXPLICIT WAIT, CLICK METHOD
	{
		
		loc= new Locators();
		By byEle=loc.locator(key, "click", null);
		loc.conditionName("click", null, nameOfCondition,  waitPeriod, byEle, key).click();
		loc.passMsg(key, "click", null, nameOfCondition, waitPeriod);
		
		
		
	}
		
	
	public void type(String key, String data, int waitPeriod, String nameOfCondition)  // REUSEABLE EXPLICIT WAIT, TYPE METHOD
	{
		
		
		loc= new Locators();
		By byEle=loc.locator(key, "type", data);
		loc.conditionName("type", data, nameOfCondition,  waitPeriod, byEle, key).sendKeys(data);
		loc.passMsg(key, "type", data, nameOfCondition, waitPeriod);

	}

	
	public void alert(String action, int waitPeriod)  // REUSEABLE EXPLICIT WAIT, ALERT METHOD
	{
		try {
		
		
		
			
			if (action.toLowerCase().contains("accept")) {
				WebDriverWait d = new WebDriverWait(driver, waitPeriod);
				 d.until(ExpectedConditions.alertIsPresent()).accept();
				
				 logger.debug("alertIsPresent timeout for Alert set to "+ waitPeriod+" seconds. Action("+action+") taken on Alert Successfully");
				 test.log(Status.INFO, "alertIsPresent timeout Alert set to "+ waitPeriod+" seconds. Action("+action+") taken on Alert Successfully");
				
			}
			else if (action.toLowerCase().contains("dismiss")) {
				WebDriverWait d = new WebDriverWait(driver, waitPeriod);
				d.until(ExpectedConditions.alertIsPresent()).dismiss();
			
				 logger.debug("alertIsPresent timeout for Alert set to "+ waitPeriod+" seconds. Action("+action+") taken on Alert Successfully");
				 test.log(Status.INFO, "alertIsPresent timeout Alert set to "+ waitPeriod+" seconds. Action("+action+") taken on Alert Successfully");
				
			}
			
			else if(action.toLowerCase().contains("getText"))
			{
				WebDriverWait d = new WebDriverWait(driver, waitPeriod);
				System.out.println(d.until(ExpectedConditions.alertIsPresent()).getText());
				
				logger.debug("alertIsPresent timeout for Alert set to "+ waitPeriod+" seconds. Action("+action+") taken on Alert Successfully");
				test.log(Status.INFO, "alertIsPresent timeout Alert set to "+ waitPeriod+" seconds. Action("+action+") taken on Alert Successfully");}
			
			else {
				driver.switchTo().alert().accept();  //if I don't give this I get unhandleable exception in next run of dataprovider methods
				logger.error(" user provided wrong action ["+action+"] for alert(String action, int waitPeriod)."
						+ "\n Following action are used :- \n \t 1.dismiss \n \t 2. accept  \n \t 3. getText ");
				
				Assert.fail(" user provided wrong action ["+action+"] for alert(String action, int waitPeriod)."
						+ "\n Following action are used :- \n \t 1.dismiss \n \t 2. accept  \n \t 3. getText ");
				
				}
		}
		
		catch (NoAlertPresentException e) {     
			
			logger.error(" Action("+ action +") can't be successfully taken on Alert, as Alert is either NOT present on page or ("+waitPeriod+" second) waitperiod of conditon(alertIsPresent) timed out "
					+ "\n \n \n Error msg is :-   "+ e.getMessage());
			
			Assert.fail(" Action("+ action +") can't be successfully taken on Alert, as Alert is either NOT present on page or ("+waitPeriod+" second) waitperiod of conditon(alertIsPresent) timed out "
					+ "\n \n \n Error msg is :-   "+ e.getMessage());
					
		}
		
		
	}
	
	

//-------------------------------------------------------------------------------------------------------------------------
// ----------------------VERIFICATION/HARD ASSERT IMPLEMENTATION----------------------------------------------
//--------------------------------------------------------------------------------------------------------------------------

	
	
	/*
	 * code is written such that for verification screenshots not taken screenshots needs to be added then change
	 * test.log(Status.ERROR,"VERIFY :- '"+driver.getTitle()+ "' is INCORRECT TITLE"); 
	 * to following
	 * Assert.fail("VERIFY :- '"+driver.getTitle()+ "' is INCORRECT TITLE"); 
	 * 
	 */
	
	
	
	
	
public void checkPageTitle(String expectedtitle, String assertType)  //REUSEABLE METHOD FOR CHECKING TITLE 
{
	
	
	
	if (driver.getTitle().equals(expectedtitle) && assertType.toLowerCase().contains("hardassert")) {

		logger.info( "ASSERT :- '"+expectedtitle+ "' is correct title");
		test.log(Status.PASS,"ASSERT :- '"+expectedtitle+ "' is correct title");
	}
	else if (!(driver.getTitle().equals(expectedtitle)) && assertType.toLowerCase().contains("hardassert"))
	{
		// **** keep Assert.fail as last statement**
		//**** no need to write test.log(Status.FAIL, "xyzxyzxyz") as Assert.fail + onTestFailure doing same
		
		
		logger.error( "ASSERT :- '"+expectedtitle+ "' is INCORRECT TITLE. Actual Title is :- "+driver.getTitle());
		Assert.fail("ASSERT :- '"+expectedtitle+ "' is INCORRECT TITLE. Actual Title is :- "+driver.getTitle());
		
	}
	
	else if (driver.getTitle().equals(expectedtitle) && assertType.toLowerCase().contains("softassert")) {

		logger.info( "VERIFY :- '"+expectedtitle+ "' is correct title");
		test.log(Status.INFO,"VERIFY :- '"+expectedtitle+ "' is correct title");
		
	}
	else if (!(driver.getTitle().equals(expectedtitle)) && assertType.toLowerCase().contains("softassert"))
	{
		// **** keep Assert.fail as last statement**
		//**** no need to write test.log(Status.FAIL, "xyzxyzxyz") as Assert.fail + onTestFailure doing same
		
		
		logger.error( "VERIFY :- '"+expectedtitle+ "' is INCORRECT TITLE. Actual Title is :- "+driver.getTitle());
		test.log(Status.ERROR,"VERIFY :- '"+expectedtitle+ "' is INCORRECT TITLE. Actual Title is :- "+driver.getTitle());

	}
	
	else
	{
		logger.error("In checkPageTitle(String expectedtitle, String assertType); value of inputed assertType ["+assertType+"] is neither 'hardassert' or 'softassert'");
		Assert.fail("In checkPageTitle(String expectedtitle, String assertType); value of inputed assertType ["+assertType+"] is neither 'hardassert' or 'softassert'");
	}
	
	
}

public void isElementPresent(String key, String assertType) { // REUSEABLE ELEMENT AVAILABILITY METHOD
	
	
	loc = new Locators();
	By bol=loc.locator(key, assertType.toLowerCase(),null);
	
	if(bol!=null) {
	loc.passMsg(key, assertType.toLowerCase(), null, null, 0);  	}
	
			
	}
	 
	

public void doesAlertContainsText(String message, String assertType) { // REUSEABLE ALERT TEXT AVAILABILITY METHOD
	
		try {
			if (driver.switchTo().alert().getText().contains(message) && assertType.toLowerCase().contains("hardassert")) {
				
				
				logger.info("ASSERT :- text(" +message+ ") is present in Alert Box");
				test.log(Status.PASS, "ASSERT :- text(" +message+ ") is present in Alert Box");
	
			} 
			
			else if (!(driver.switchTo().alert().getText().contains(message)) && assertType.toLowerCase().contains("hardassert")) {
				
				
				logger.error("ASSERT :- text(" +message+ ") is NOT present in Alert Box .\n Message in AlertBox is "+driver.switchTo().alert().getText());
				Assert.fail("ASSERT :- text(" +message+ ") is NOT present in Alert Box .\n Message in AlertBox is "+driver.switchTo().alert().getText());
	
			} 
			
			else if(driver.switchTo().alert().getText().contains(message) && assertType.toLowerCase().contains("softassert")) {
				
				logger.info("VERIFY :- text(" +message+ ") is present in Alert Box");
				test.log(Status.INFO, "VERIFY :- text(" +message+ ") is present in Alert Box");	
					
			}	
			else if(!(driver.switchTo().alert().getText().contains(message)) && assertType.toLowerCase().contains("softassert")) 
			{
					
				logger.error("VERIFY :- text(" +message+ ") is NOT present in Alert Box .\n Message in AlertBox is "+driver.switchTo().alert().getText());
				test.log(Status.ERROR,"ASSERT :- text(" +message+ ") is NOT present in Alert Box .\n Message in AlertBox is "+driver.switchTo().alert().getText());
			}
			
			else
			{
				logger.error("In doesAlertContainsText(String message, String assertType); value of inputed assertType ["+assertType+"] is neither 'hardassert' or 'softassert'");
				Assert.fail("In doesAlertContainsText(String message, String assertType); value of inputed assertType ["+assertType+"] is neither 'hardassert' or 'softassert'");
			}
			
		}
			
			
		 catch (Exception e) { // NoAlertPresentException
			
			
				logger.error("NO alert is present on screen.\n \n \n error message is "+e.getMessage());
				Assert.fail("NO alert is present on screen.\n \n \n error message is "+e.getMessage());
			}
		
		
	}


}

