package com.personal.utilities;


import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.personal.base.BaseClass;

public class ExtentReporter extends BaseClass{

	
    public static ExtentReports configuration() 
    {
    	
    	Date date = new Date();  		    
		SimpleDateFormat simpledate=new SimpleDateFormat("dd_MMMM_hh_a_mm_ss");
		String name=simpledate.format(date);
		

    String path =System.getProperty("user.dir")+"\\src\\test\\resources\\emailables\\"+name+"_extentReport.html";
    ExtentSparkReporter reporter = new ExtentSparkReporter(path);
    
    reporter.config().setReportName("Name of Report");
    reporter.config().setDocumentTitle("Title of page");
    
   
    
    ExtentReports extent =new ExtentReports();
    extent.attachReporter(reporter);
    extent.setSystemInfo("Automation Tester", "Shiv Sahil Guleri");
    extent.setSystemInfo("Environment", "QA");
 
    return extent;
    
    }

}
