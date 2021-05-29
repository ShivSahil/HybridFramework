package com.personal.testcases;

import org.testng.Assert;
import org.testng.annotations.*;

import com.aventstack.extentreports.Status;
import com.personal.base.BaseClass;
import com.personal.listeners.RetryAnalyzer;
import com.personal.utilities.ExcelRead;

public class AddCustomerTest extends BaseClass {

	@Test(description = "clicking on login button and checking availability of buttons on screen")
	public void loginAsmanagerTest() throws InterruptedException {

		click("BankMangLogin_css", 10, "visibilityOfElementLocated");
		checkPageTitle("Protractor practice website - Banking App","softAssert");

		isElementPresent("AddCustomerButton_xpath", "hardAssert");
		click("AddCustomerButton_xpath");

	}
	
	@Test(dataProviderClass = ExcelRead.class, dataProvider = "excelData", dependsOnMethods = "loginAsmanagerTest", description = "adding multiple customers from excel")
	public void addMultipleCustomerTest(String firstName, String lastName, String pincode) throws InterruptedException {
		type("FirstName_css", firstName);
		type("LastName_css", lastName);
		type("PinCode_css", pincode);
		click("AddCustomer_xpath");
		
		
		doesAlertContainsText("1Customer added successfully with customer id :", "softAssert");
		alert("accept", 4);
		
		
	}

}
