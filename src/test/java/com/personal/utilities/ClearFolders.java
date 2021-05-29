package com.personal.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import com.personal.base.BaseClass;



public class ClearFolders extends BaseClass{
	

	public static void delete() throws IOException {
		
		fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Configuration.properties");
		configuration.load(fis);
		
		if(configuration.getProperty("ClearEmailablesBeforeRun").contains("true"))
		{
			emailables();
		}
		
		if(configuration.getProperty("ClearScreenshotsBeforeRun").contains("true"))
		{
			screenshots();
		}
		
	}
	
	
	private static void emailables() throws IOException
	{
		
		File path= new File(System.getProperty("user.dir")+"\\src\\test\\resources\\emailables");
		
		FileUtils.cleanDirectory(path); 
		
		logger.debug("src\\test\\resources\\emailables folder, is cleared");
		     
	}
	
	private static void screenshots() throws IOException
	{
		File path= new File(System.getProperty("user.dir")+"\\src\\test\\resources\\screenshots");
		      
		FileUtils.cleanDirectory(path); 

		
		logger.debug("src\\test\\resources\\screenshots folder, is cleared");
		     
	}
	
	
}