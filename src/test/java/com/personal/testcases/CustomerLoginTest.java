package com.personal.testcases;

import org.testng.annotations.Test;

import com.personal.base.BaseClass;

public class CustomerLoginTest extends BaseClass {

	

	
	@Test(description = "CUSTOMER LOGIN - login as customer", dependsOnGroups={"CustomerLoginMethodPrerequisites"})
	public void customerLoginMethod() throws InterruptedException {

		click("Hometab_xpath");
		
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
