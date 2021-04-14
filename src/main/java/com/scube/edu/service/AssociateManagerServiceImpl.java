package com.scube.edu.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.scube.edu.controller.MasterController;
import com.scube.edu.model.UniversityStudentDocument;
import com.scube.edu.repository.UniversityStudentDocRepository;
import com.scube.edu.util.FileStorageService;

@Service
public class AssociateManagerServiceImpl implements AssociateManagerService{

	private static final Logger logger = LoggerFactory.getLogger(MasterController.class);

	@Autowired
	 UniversityStudentDocRepository 	 universityStudentDocRepository ;
	
	 @Autowired
	 private FileStorageService fileStorageService;
	
	private XSSFWorkbook workbook;
	
	
	@Override
	public String saveStudentInfo(MultipartFile excelfile,MultipartFile datafile) throws IOException {
		
		 workbook = new XSSFWorkbook(excelfile.getInputStream());
		 XSSFSheet worksheet = workbook.getSheetAt(0);
		 List<UniversityStudentDocument> studentDataList = new ArrayList<UniversityStudentDocument>();
		 List<UniversityStudentDocument> response = new ArrayList<UniversityStudentDocument>();

		 List<String> rejectedData=new ArrayList<>();
		 for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
			 
			 XSSFRow row = worksheet.getRow(i);
			 Optional<UniversityStudentDocument> docEntity =universityStudentDocRepository.findByEnrollmentNo(row.getCell(4).getStringCellValue());
			 if(docEntity==null) {
			 UniversityStudentDocument studentData = new UniversityStudentDocument();	            
		        
		        studentData.setFirstName(row.getCell(0).getStringCellValue());
		        studentData.setLastName(row.getCell(1).getStringCellValue());
		        studentData.setCollegeName(row.getCell(2).getStringCellValue());	
		        studentData.setStream(row.getCell(3).getStringCellValue());	
		        studentData.setEnrollmentNo(row.getCell(4).getStringCellValue());
		        studentData.setPassingYear((int) row.getCell(5).getNumericCellValue());		        
		        studentDataList.add(studentData);    
			 }
			 else
			 {				
				 rejectedData.add(row.getCell(4).getStringCellValue());
				
			 }
			
		 }
		 logger.info("rejectedData : "+rejectedData);
		 universityStudentDocRepository.saveAll(studentDataList);
		 String fileSubPath = "file/";
		 String filePath = fileStorageService.storeFile(datafile , fileSubPath);
		 return "success";
		 
	}


	

}
