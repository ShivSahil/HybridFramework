package com.personal.utilities;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import com.personal.base.BaseClass;



public class ExcelRead extends BaseClass{

		
	public static String[][] excelData(String sheetName) throws IOException {
		
		File f= new File(baseLoc+ "\\src\\test\\resources\\excel\\testdata.xlsx"); 

	    // for reading we are using FileInputStream, and writing using FileOutputStream 
	    FileInputStream fi = new FileInputStream(f);
	    XSSFWorkbook workbook = new XSSFWorkbook(fi);  // in writing; we used XSSFWorkbook 
	    
	    XSSFSheet sheet = workbook.getSheet(sheetName);// in writing; we used XSSFSheet 
	    // .getSheetAt(0) mean first sheet 
	    XSSFRow row = sheet.getRow(0);

	    
	    int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
	    int cellCount=row.getLastCellNum()-row.getFirstCellNum();
	    String str[][] = new String[rowCount][cellCount];
	    
  
	    
	    int tempRowStr=0;
	    for (int i = 1; i <= rowCount ; i++) {  
	    	
	    	 int tempCellStr=0;
	    	 for (int j = 0; j <= cellCount -1; j++) {  
	    		 
	    		 // as all the text is String; I used NO switch!!!!
	    		 str[tempRowStr][tempCellStr]=sheet.getRow(i).getCell(j).getStringCellValue();
	    		 tempCellStr++;
	    	 
	    	 }
	    	 
	    	 
	    tempRowStr++;
	   } 

	    // generally this is a part of finally block 
	    fi.close();
	    
	    return str;
					
	}
	
	@DataProvider
	public Object[][] excelData(Method method) throws IOException // returning type is "Object[][]"
	{
		
	Object[][] data =ExcelRead.excelData(method.getName());
		return data;
	}
}
	









