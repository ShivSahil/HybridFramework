package com.personal.testcases;



import org.testng.annotations.*;

import com.personal.base.BaseClass;



public class AddCustomer extends BaseClass{
	
	
	
	@Test
	public void loginAsmanager() throws InterruptedException
	{
		click("BankMangLogin_css");
		checkPageTitle("1Protractor practice website - Banking App");
		
		isElementPresent("AddCustomerButton_xpath");
		click("AddCustomerButton_xpath");
	}
	
	
	

}
