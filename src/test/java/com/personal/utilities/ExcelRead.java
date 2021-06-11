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


// MAKE THIS CODE MORE ROBUST*


public class ExcelRead extends BaseClass{

	
// **** when deleting an existing row from excel use, control + '-'. else this excel utlity is picking up empty rows as data!
// ***  " '1000 " makes 1000 as string
	
	public static String[][] excelData(String sheetName) throws IOException {
		
		File f= new File(baseLoc+ "\\src\\test\\resources\\excel\\testdata.xlsx"); 

	    
	    FileInputStream fi = new FileInputStream(f);
	    XSSFWorkbook workbook = new XSSFWorkbook(fi); 
	    
	    XSSFSheet sheet = workbook.getSheet(sheetName);
	   
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
	









