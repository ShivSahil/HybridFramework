package com.personal.listeners;


import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;
import com.personal.base.BaseClass;

public class RetryAnalyzer extends BaseClass implements IRetryAnalyzer {

private int count = 0;
private static int maxTry = 1;

public boolean retry(ITestResult iTestResult) {

	maxTry=Integer.parseInt(configuration.getProperty("RetryAnalyzerMaxTry"));
	
	if (!iTestResult.isSuccess()) { //Check if test not succeed
		
		if (count < maxTry) { //Check if max try count is reached
		
			count++; //Increase the maxTry count by 1
			iTestResult.setStatus(ITestResult.FAILURE); //Mark test as failed
			
			logger.warn("Re-execution attempt number : '"+ count + "' of method  "+ iTestResult.getMethod().getMethodName());
			test.log(Status.WARNING,"Re-execution attempt number : '"+ count + "' of method  "+ iTestResult.getMethod().getMethodName());
			
			return true; } //Tells TestNG to re-run the test
		
		else {
			iTestResult.setStatus(ITestResult.FAILURE); } //If maxCount reached,test marked as failed
	} 
	else {
		iTestResult.setStatus(ITestResult.SUCCESS);} //If test passes, TestNG marks it as passed
		
	return false;
	}

}
