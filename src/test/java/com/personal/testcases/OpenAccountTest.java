package com.personal.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.personal.base.BaseClass;
import com.personal.utilities.ExcelRead;

public class OpenAccountTest extends BaseClass{

	@BeforeClass
	public void openAccountTab()
	{
		click("OpenAccountTab_xpath");
	}
	
	@Test(dataProviderClass = ExcelRead.class, dataProvider = "excelData", description = "BANK MANAGER LOGIN > OPEN CUSTOMER TAB - opening multiple customers account", dependsOnGroups={"openAccountMethodPrerequisites"})
	public void openAccountMethod(String name, String currency) throws InterruptedException
	{
		staticDropDown("CustomerDropDown_id", name);  
		staticDropDown("CurrencyDropDown_id", currency);
		click("Submit_css");	
		doesAlertContainsText("Account created successfully with account Number :","softAssert");
		alert("accept");
	
		
	
	}
	
	
	@AfterClass
	public void HometTab()
	{
		click("Hometab_xpath");
	}
}
