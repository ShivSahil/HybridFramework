package com.personal.testcases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.personal.base.BaseClass;
import com.personal.utilities.ExcelRead;

public class CustomerLoginTest extends BaseClass {

	
	@Test(description = "CUSTOMER LOGIN - login as customer")
	public void CustomerLoginMethod() throws InterruptedException {

		
		click("CustomerLogin_css");  
		
		staticDropDown("YourNameDropDown_name", "Shiv Sahil");
		click("LoginBtn_xpath", 1, "elementToBeClickable");
		isElementPresent("Transaction_css", "hardAssert");
		click("LogoutBtn_css");
	
		
		staticDropDown("YourNameDropDown_name", "No Cust");
		click("LoginBtn_xpath", 1, "elementToBeClickable");
		click("LogoutBtn_css");

	}
	
	
	
}
