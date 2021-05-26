package com.personal.listeners;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.personal.base.BaseClass;
import com.personal.utilities.ScreenshotUtility;

public class ItestLis extends BaseClass implements ITestListener{
	
	public static String screenshotPath;
	@Override
	public void onTestStart(ITestResult result) {
		
		test = extent.createTest(result.getMethod().getMethodName());
		logger.info("##### EXECUTION OF " + result.getMethod().getMethodName() + " HAS BEGUN");
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		
		logger.info("@@@@ " + result.getMethod().getMethodName() + " HAS PASSED");
		test.pass(result.getMethod().getMethodName() + " HAS PASSED");
		extent.flush();
	}

	@Override
	public void onTestFailure(ITestResult result) {
		
		// Note:- keep these getMethodName and getThrowable separate
		logger.error("$$$$$$$ " + result.getMethod().getMethodName() + " HAS FAILED " );
		logger.error(result.getThrowable());
		
		try {
			screenshotPath = ScreenshotUtility.screenshot(driver,result.getMethod().getMethodName());
		
		} catch (IOException e) {
			logger.error("unable to take screenshot of "+result.getMethod().getMethodName()+" method" );
			e.printStackTrace();
		}
		try {
			test.addScreenCaptureFromPath(screenshotPath, result.getMethod().getMethodName());
			logger.debug("Screenshot of "+result.getMethod().getMethodName()+" method successfully attached to report ");
		
		} catch (IOException e) {
			logger.error("unable to attach screenshot to "+ result.getMethod().getMethodName()+ " method");
			e.printStackTrace();
		}

		
		
		test.fail( result.getMethod().getMethodName() + " HAS FAILED ");
		test.fail(result.getThrowable());
		extent.flush();
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

}
