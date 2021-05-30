package com.personal.listeners;

import java.io.IOException;

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
	@Override
	public void onTestStart(ITestResult result) {
		
		
		test = extentVar.createTest(result.getMethod().getMethodName());
		logger.info("##### EXECUTION OF " + result.getMethod().getMethodName() + " HAS BEGUN");
		logger.info("description of testcase:- "+result.getMethod().getDescription());
		
		test.log(Status.INFO, "description of testcase:- "+result.getMethod().getDescription());
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		
		logger.info("@@@@ " + result.getMethod().getMethodName() + " HAS PASSED");
		test.pass(result.getMethod().getMethodName() + " HAS PASSED");
		extentVar.flush();
	}

	@Override
	public void onTestFailure(ITestResult result) {
		
		//***** keep  getMethodName and getThrowable separate
		logger.error("$$$$$$$ " + result.getMethod().getMethodName() + " HAS FAILED ");
		logger.fatal(result.getThrowable());
		
		try {
			screenshotPath = ScreenshotUtility.screenshot(result.getMethod().getMethodName());
		
		} catch (IOException e) {
			logger.error("unable to take screenshot of "+result.getMethod().getMethodName()+" method. error msg is "+e.getMessage() );
			
		}
		try {
			test.addScreenCaptureFromPath(screenshotPath, result.getMethod().getMethodName());
			logger.debug("Screenshot of "+result.getMethod().getMethodName()+" method successfully attached to report ");
		
		} catch (IOException e) {
			logger.error("unable to attach screenshot to "+ result.getMethod().getMethodName()+ " method. error msg is"+e.getMessage());
			e.printStackTrace();
		}

		
		test.fail(result.getThrowable());
		test.fail( result.getMethod().getMethodName() + " HAS FAILED ");
		

	
		extentVar.flush();
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		
		
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
			logger.error("Invalid email address. reason is "+ e.getMessage());
		} catch (MessagingException e) {
			logger.error("Mail Not sent. reason is "+ e.getMessage());
		}
        
		
		
	}

}
