package com.personal.listeners;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;
import com.personal.base.BaseClass;
import com.personal.utilities.MailUtility;
import com.personal.utilities.ScreenshotUtility;
import com.personal.utilities.ZipUtility;

public class ItestLis extends BaseClass implements ITestListener{
	


	public static String screenshotPath;
	
	
	//****these got reset with new shell
	List<String> failedTC=new ArrayList<String>(); 
	List<String> dependency ;
	
	@Override
	public void onTestStart(ITestResult result) {
		
		
		 test = extentVar.createTest(result.getMethod().getMethodName());
		  logger.info(" \n \n \n << EXECUTION OF " + result.getMethod().getMethodName() +" HAS BEGUN >> ");
		  logger.info("description of testcase:- "+result.getMethod().getDescription());
		  test.log(Status.INFO,"description of testcase:- "+result.getMethod().getDescription());
		  

		  
		  
		  
		

		  // this code is attempt to handle incorrect display of testcases as passed with failed dependency; on extent report
		  
		
		 String[] strGetMethodsDependedUpon= result.getMethod().getMethodsDependedUpon();
		 String[] strGetGroupsDependedUpon= result.getMethod().getGroupsDependedUpon();
		 List<String> dependencyListMethods = Arrays.asList(strGetMethodsDependedUpon);
		 List<String> dependencyListGroups = Arrays.asList(strGetGroupsDependedUpon);
		 
		 dependency = new  ArrayList<String>(dependencyListMethods);
		 dependency.addAll(dependencyListGroups);
		 
		 Set<String> set= new HashSet<String>();
		 set.addAll(failedTC);
		 failedTC.clear();
		 failedTC.addAll(set);
		 
	
		
			
			for (String dep : dependency) {
				for (String fai : failedTC) {
					if(fai.equals(dep))
					{
						onTestSkipped(result);
					}
					
				}
			}
			
	}

	
	
	
	
	@Override
	public void onTestSuccess(ITestResult result) {
		
	
			  logger.info(result.getMethod().getMethodName() + " HAS PASSED");
				test.pass(result.getMethod().getMethodName() + " HAS PASSED");
			  extentVar.flush();
		 
		
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		

		failedTC.add(result.getMethod().getMethodName());
	 String[] temp= result.getMethod().getGroups();
	 List<String> tempList = Arrays.asList(temp);
	 failedTC.addAll(tempList);		
	
	
	
	logger.error(result.getMethod().getMethodName() + " HAS FAILED ");
	
	try {
		screenshotPath = ScreenshotUtility.screenshot(result.getMethod().getMethodName());
	
	} catch (IOException e) {
		logger.error("unable to take screenshot of "+result.getMethod().getMethodName()+" method.\n Error msg is "+e.getMessage() );
		
	}
	
	
	try {
		test.addScreenCaptureFromPath(screenshotPath, result.getMethod().getMethodName());
		logger.debug("Screenshot of "+result.getMethod().getMethodName()+" method successfully attached to report ");
	
	} catch (IOException e) {
		logger.error("Unable to attach screenshot to "+ result.getMethod().getMethodName()+ " method.\n Error msg is"+e.getMessage());
		e.printStackTrace();
	}

	
	test.fail(result.getThrowable());
	test.fail( result.getMethod().getMethodName() + " HAS FAILED ");
	

	extentVar.flush();
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		
		test.skip( result.getMethod().getMethodName() + " HAS SKIPPED BECAUSE IT'S DEPENDENCY '"+dependency+"' HAS FAILED"); 
		logger.error( result.getMethod().getMethodName() + " HAS SKIPPED BECAUSE IT'S DEPENDENCY '"+dependency+"' HAS FAILED"); 
	  
		extentVar.flush();
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		
		
	}

	@Override
	public void onStart(ITestContext context) {
		
		
		 
		
	}

	@Override
	public void onFinish(ITestContext context) {
		ZipUtility.zip();
		
		
		
		try {
        	mail =  new MailUtility();
			mail.mailSend();
		} catch (AddressException e) {
			logger.error("Invalid email address.\n Reason is "+ e.getMessage());
		} catch (MessagingException e) {
			logger.error("Mail Not sent.\n Reason is "+ e.getMessage());
		}
        
		
		
	}

}
