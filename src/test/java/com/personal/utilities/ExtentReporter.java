package com.personal.utilities;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporter {

	
	
	
	
    public static ExtentReports configuration() 
    {

    String path =System.getProperty("user.dir")+"\\src\\test\\resources\\emailables\\extentReport.html";
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
