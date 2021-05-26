package com.personal.testcases;


import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;

import com.personal.base.BaseClass;



public class AddCustomer extends BaseClass{
	
	
	
	@Test
	public void loginAsmanager() throws InterruptedException
	{
		click("BankMangLogin_css");
		checkPageTitle("Protractor practice website - Banking App");
		
		isElementPresent("AddCustomerButton_xpath");
		click("AddCustomerButton_xpath");
	}
	
	
	

}
