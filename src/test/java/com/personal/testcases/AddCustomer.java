package com.personal.testcases;



import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.annotations.*;

import com.personal.base.BaseClass;
import com.personal.utilities.ExcelRead;



public class AddCustomer extends BaseClass{
	
	
	
	@Test(description="clicking on login button and checkeding availability of buttons on screen")
	public void loginAsmanagerTest() throws InterruptedException
	{
		Thread.sleep(4000);
		click("BankMangLogin_css");
		checkPageTitle("Protractor practice website - Banking App");
		
		isElementPresent("AddCustomerButton_xpath");
		click("AddCustomerButton_xpath");
	}
	
	 
	@Test(dataProviderClass=ExcelRead.class, dataProvider = "excelData", dependsOnMethods ="loginAsmanagerTest", description = "adding multiple customers from excel")  
	public void addMultipleCustomerTest(String firstName, String lastName, String pincode) throws InterruptedException
	{
		type("FirstName_css", firstName );
		type("LastName_css", lastName );
		type("PinCode_css", pincode );
	
		// EXPLCIIT WAITTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT
		Thread.sleep(4000);  
		
		
		click("AddCustomer_xpath");
		doesAlertContainsText("Customer added successfully with customer id :");
		
		alert("accept");
	}
	
	
	
	

	
	

}
