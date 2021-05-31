package com.personal.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;
import com.personal.base.BaseClass;

public class RetryAnalyzer extends BaseClass implements IRetryAnalyzer {

private int count = 0;
private static int maxTry = 1;

public boolean retry(ITestResult iTestResult) {


	maxTry=Integer.parseInt(configuration.getProperty("MaxTry"));
	
	if (!iTestResult.isSuccess()) { //Check if test not succeed
		
		if (count < maxTry) { //Check if maxtry count is reached
		
			logger.warn(iTestResult.getMethod().getMethodName()+ " method is being re-run for count ("+(count+1)+").Check exception below :---  ");
			logger.warn( iTestResult.getThrowable());
			test.log(Status.WARNING, iTestResult.getMethod().getMethodName()+ " method is being re-run for count ("+(count+1)+"). Check exception below :---   ");
			test.log(Status.WARNING, iTestResult.getThrowable());
			
			
			
			count++; //Increase the maxTry count by 1
			iTestResult.setStatus(ITestResult.FAILURE); //Mark test as failed
			
			
			try {
				Thread.sleep(1500); // thread.sleep has been placed deliberately by me. if not execution steps were getting missed
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return true; } //Tells TestNG to re-run the test
		
		else {
			iTestResult.setStatus(ITestResult.FAILURE); } //If maxCount reached,test marked as failed
	
	
	} 
	
	
	else {
		iTestResult.setStatus(ITestResult.SUCCESS);} //If test passes, TestNG marks it as passed
		
	return false;
	}

}
