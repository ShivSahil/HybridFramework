package com.personal.utilities;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.personal.base.BaseClass;


public class ZipUtility extends BaseClass {


	static String path;
	static String Reports;
	
	
	
	public static void zip()
	{
		if(configuration.getProperty("ZipEmailables").contains("true"))
		{
			zipFolder();
		}
	}
	
	private static void zipFolder() {
		try {
			
			
			Date date = new Date();  		    
			SimpleDateFormat simpledate=new SimpleDateFormat("dd_MMMM_hh_a_mm_");
			Reports="Report_"+simpledate.format(date);
			

			path =System.getProperty("user.dir")+"\\src\\test\\resources\\"+Reports+".zip";
			
			
			File inFolder = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\emailables");
			File outFolder = new File(path);
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(outFolder)));
			BufferedInputStream in = null;
			byte[] data = new byte[1000];
			String files[] = inFolder.list();
			for (int i = 0; i < files.length; i++) {
				in = new BufferedInputStream(new FileInputStream(inFolder.getPath() + "/" + files[i]), 1000);
				out.putNextEntry(new ZipEntry(files[i]));
				int count;
				while ((count = in.read(data, 0, 1000)) != -1) {
					out.write(data, 0, count);
				}
				out.closeEntry();
			}
			out.flush();
			out.close();
			logger.info("Report generated with name "+Reports+".zip");

		} catch (Exception e) {
			logger.error("Exception occured for Report generated with name "+Reports+".zip.\n Error msg is:-- "+ e.getMessage());
			
		}
		
		
		
	}
}


