package com.personal.rough;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

public class RoughWork {

	
	public static void main(String[] args) {

Date date = new Date();  //import using java.util just like collections
System.out.println(date.toString());  // Sat Feb 06 21:41:01 IST 2021 
  
  
SimpleDateFormat simpledate=new SimpleDateFormat("dd_MMMM_hh_a_mm_ss"); //26_May 08:25:49
System.out.println(simpledate.format(date));
		
				
	}

}
