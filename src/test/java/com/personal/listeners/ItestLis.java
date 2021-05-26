package com.personal.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.personal.base.BaseClass;

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
		logger.error("$$$$$$$ " + result.getMethod().getMethodName() + " HAS PASSED DUE TO " + result.getThrowable());
		test.fail( result.getMethod().getMethodName() + " HAS PASSED DUE TO " + result.getThrowable());
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
