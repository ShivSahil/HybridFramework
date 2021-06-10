package com.personal.testcases;

import org.testng.annotations.Test;
import com.personal.base.BaseClass;
import com.personal.utilities.ExcelRead;

public class OpenAccountTest extends BaseClass{

	
	
	@Test(dataProviderClass = ExcelRead.class, dataProvider = "excelData", description = "BANK MANAGER LOGIN > OPEN CUSTOMER TAB - opening multiple customers account", groups={"CustomerLoginMethodPrerequisites"}, dependsOnGroups={"openAccountMethodPrerequisites"})
	public void openAccountMethod(String name, String currency) throws InterruptedException
	{

		click("OpenAccountTab_xpath");
		staticDropDown("CustomerDropDown_id",name );  //name
		staticDropDown("CurrencyDropDown_id", currency);
		click("Submit_css");	
		doesAlertContainsText("Account created successfully with account Number :","softAssert");
		alert("accept");
		

	}
	
	
}
