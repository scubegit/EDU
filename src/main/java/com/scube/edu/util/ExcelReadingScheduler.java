package com.scube.edu.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Multipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scube.edu.service.AssociateManagerService;

@Service
public class ExcelReadingScheduler {
	
	@Autowired
	AssociateManagerService associateManagerService;
	
	public void readExcelData()
	{
		
		List<String> ExcelFile=new ArrayList<>();	
		
		File  excelFilePath=new File("F:\\asscociateDocs");
		//File  imageFilePath=new File("F:\\response\\QrCode");
		
		

		try{
			File [] listofExcelFiles=excelFilePath.listFiles();
		//	File [] listofJpgFiles=imageFilePath.listFiles();
			
			String filnmPath="";
			String filnm;
			if(listofExcelFiles!=null){
			for(File file:listofExcelFiles){
				if (file.isFile()){
				 filnm=file.getName();
				  filnmPath=file.getAbsolutePath();
				  ExcelFile.add(filnmPath);								
				}
				}						
			}
			

		}
		catch(Exception e)
		{
		System.out.println(e.getMessage());	
		}
	
}
}
 