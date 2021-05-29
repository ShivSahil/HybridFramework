package com.personal.testcases;

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
	
	@Test(dataProviderClass = ExcelRead.class, dataProvider = "excelData", description = "opening multiple customers account from excel")
	public void openAccountMethod(String name, String currency) throws InterruptedException
	{
		staticDropDown("CustomerDropDown_id", name);
		staticDropDown("CurrencyDropDown_id", currency);
		Thread.sleep(2000);
		click("Submit_css");
		
		doesAlertContainsText("Account created successfully with account Number :");
		alert("accept");
		Thread.sleep(2000);
	}
}
