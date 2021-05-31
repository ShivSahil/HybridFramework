package com.personal.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;

import com.personal.base.BaseClass;

public class ScreenshotUtility extends BaseClass{
	static String name;

	public static String screenshot(String methodName) throws IOException {

		// no space in name, No colans etc etc
		Date date = new Date();  		    
		SimpleDateFormat simpledate=new SimpleDateFormat("dd_MMMM_hh_a_mm_ss_SSS");
		name=simpledate.format(date)+methodName.toUpperCase();
		
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileHandler.copy(src, new File(System.getProperty("user.dir")+"\\src\\test\\resources\\screenshots\\"+ name + ".png"));
		logger.debug("Screenshot("+name+".png) of "+methodName+" method taken successfully");
		
		
		return (System.getProperty("user.dir")+"\\src\\test\\resources\\screenshots\\"+ name + ".png");
	
	}

}
