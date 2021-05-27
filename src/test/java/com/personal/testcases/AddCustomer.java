package com.personal.testcases;



import org.testng.annotations.*;

import com.personal.base.BaseClass;



public class AddCustomer extends BaseClass{
	
	
	
	@Test(description="clicking on login button and checkeding availability of buttons on screen")
	public void loginAsmanager() throws InterruptedException
	{
		click("BankMangLogin_css");
		checkPageTitle("Protractor practice website - Banking App");
		
		isElementPresent("AddCustomerButton_xpath");
		click("AddCustomerButton_xpath");
	}
	
	@Test
	public void AddMultipleCustomer()
	{
		
	}
	
	

}
