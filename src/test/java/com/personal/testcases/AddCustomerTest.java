package com.personal.testcases;

import org.testng.annotations.Test;

import com.personal.base.BaseClass;
import com.personal.utilities.ExcelRead;

public class AddCustomerTest extends BaseClass {

	
	
	
	@Test(description = "BANK MANAGER LOGIN - login as bank manager and checking availability of ADD CUSTOMER TAB on screen")
	public void loginAsmanagerTest() throws InterruptedException {

		
		click("BankMangLogin_css", 10, "visibilityOfElementLocated");  
		checkPageTitle("111111111111","hardAssert");  //Protractor practice website - Banking App

		isElementPresent("AddCustomerButton_xpath", "hardAssert");  
		click("AddCustomerButton_xpath");  

	}
	
	
	
	@Test(dataProviderClass = ExcelRead.class, dataProvider = "excelData", dependsOnMethods = { "loginAsmanagerTest" }, description = "BANK MANAGER LOGIN > ADD CUSTOMER TAB - adding multiple customers",groups={"openAccountMethodPrerequisites"})
	public void addMultipleCustomerTest(String firstName, String lastName, String pincode) throws InterruptedException {
		
		
		
		type("FirstName_css", firstName,4,"elementToBeClickable");  
		type("LastName_css", lastName);		
		type("PinCode_css", pincode);
		click("AddCustomer_xpath");
		
	
		doesAlertContainsText("Customer added successfully with customer id", "softAssert");  
		alert("accept");  
		
	}

}
