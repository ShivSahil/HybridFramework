package com.personal.base;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

class Locators extends BaseClass{ // keep it default
	
	
	
	WebElement locator(String key, String action, String data) {
		
		
		WebElement ele = null;
		
		try {	
			
			// can't use switch here. switch can't be used for contains
			if(key.toLowerCase().contains("_css") )
			{	
				ele=driver.findElement(By.cssSelector(objRepo.getProperty(key)));
			}
			
			else if (key.toLowerCase().contains("_xpath"))
			{
				ele=driver.findElement(By.xpath(objRepo.getProperty(key)));		
			}
			else if (key.toLowerCase().contains("_id"))
			{
				ele=driver.findElement(By.id(objRepo.getProperty(key)));
			}
			else if (key.toLowerCase().contains("_link"))
			{
				ele=driver.findElement(By.linkText(objRepo.getProperty(key)));
			}
			
			else if (key.toLowerCase().contains("_partiallink"))
			{
				ele=driver.findElement(By.partialLinkText(objRepo.getProperty(key)));
			}
			else if (key.toLowerCase().contains("_name"))
			{
				ele=driver.findElement(By.name(objRepo.getProperty(key)));
			}
			else if (key.toLowerCase().contains("_class"))
			{
				ele=driver.findElement(By.className(objRepo.getProperty(key)));
			}
			else
			{
				logger.error("locator(" +key+ ") does NOT have postfix as name, class, link, partiallink, id, css or xpath");
				Assert.fail("locator(" +key+ ") does NOT have postfix as name, class, link, partiallink, id, css or xpath");
			}
		
			
			}
		

		 catch (Exception e) { 
			 
			failmsg(key, action, data, e.toString());
		
		 }
		return ele;
		
		
	}
	
	
	
	void passMsg(String key, String action, String data)  
	{
		switch (action) {
		
			case "click":
			{
				// **** DON'T WRITE driver.getTitle(), in case there is a alert box. you will get error on driver.getTitle()
				 logger.debug("locator(" +key+ ") clicked successfully");
				 test.log(Status.INFO, "locator(" +key+ ") clicked successfully");
				 break;
			}
			
			case "type":
			{
				logger.debug("locator(" +key+ ") filled with value '"+ data+"' successfully");
				test.log(Status.INFO, "locator(" +key+ ") filled with value '"+ data+"' successfully");
				 break;
			}
			
			case "alert":
			{
				logger.debug(" Action("+ data +") successfully taken on Alert, present on page");
				test.log(Status.INFO, " Action("+ data +") successfully taken on alert, present on page ");
				
				 break;
			}
			
			case "staticDropDown":
			{
				// **** DON'T WRITE driver.getTitle(), in case there is a alert box. you will get error on driver.getTitle()
				
				logger.debug("option(" +data+ ") selected successfully on dropdown("+key+")");
				test.log(Status.INFO, "option(" +data+ ") selected successfully on dropdown("+key+")");
				 break;
			}
	
			default:{
				
				// empty as of now
				break;
				}
		}
	}
	
	
	void failmsg(String key, String action, String data, String exception)  // keep it private
	{
	
		if(action.equals("click") && exception.contains("NoSuchElementException"))
		{

			 logger.error("locator(" +key+ ") can't be clicked as it's NOT present on page."
			 		+ "\n \n Error msg is :-   "+ exception);
			 
			 Assert.fail("locator(" +key+ ") can't be clicked as it's NOT present on page."
			 		+ "\n \n Error msg is :-   "+ exception);
		}
		
		else if(action.equals("type") && (exception.contains("NoSuchElementException") || exception.contains("InvalidSelectorError")))
		{
			logger.error("locator(" +key+ ") can't filled with value '"+ data+"' as locator is NOT present on page."
			 		+ "\n \n Error msg is :-   "+ exception);
			 Assert.fail("locator(" +key+ ") can't filled with value '"+ data+"' as locator is NOT present on page."
			 		+ "\n \n Error msg is :-   "+ exception);
		
		}
		
		else if(action.equals("alert") && exception.contains("NoAlertPresentException"))
		{

			logger.error(" Action("+ data +") can't be successfully taken on Alert, as Alert is NOT present on page."
					+ "\n \n Error msg is :-   "+ exception);
			
			Assert.fail(" Action("+ data +") can't be successfully taken on Alert, as Alert is NOT present on page."
					+ "\n \n Error msg is :-   "+ exception);
		
		}
		
		else if(action.equals("staticDropDown") && exception.contains("NoSuchElementException"))
		{

			logger.error("option(" +data+ ") is NOT selected on dropdown("+key+"). Either dropdown("+key+") or option(" +data+ ") is NOT present on page."
					+ "\n \n \n Error msg is :-   "+ exception);
			
			Assert.fail("option(" +data+ ") is NOT selected on dropdown("+key+"). Either dropdown("+key+") or option(" +data+ ") is NOT present on page."
					+ "\n \n \n Error msg is :-   "+ exception);
		
		}
		
		
	}


	
	
	// i have to keep it separate , cause i can't place it anywhere else

	void checkDropDown(Select s, String selectOption, String key) {
		
		List<WebElement> allOptions = s.getOptions();
		if( ! (allOptions.equals(selectOption)))
				{
					
			logger.error("option(" +selectOption+ ") is NOT selected on dropdown("+key+"). Either dropdown("+key+") or option(" +selectOption+ ") is NOT present on page.");
			Assert.fail("option(" +selectOption+ ") is NOT selected on dropdown("+key+"). Either dropdown("+key+") or option(" +selectOption+ ") is NOT present on page.");
				}
		
		
	}
	
	
	

}
