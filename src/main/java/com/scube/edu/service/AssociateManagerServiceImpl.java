package com.scube.edu.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.scube.edu.controller.MasterController;
import com.scube.edu.model.CollegeMaster;
import com.scube.edu.model.PassingYearMaster;
import com.scube.edu.model.StreamMaster;
import com.scube.edu.model.UniversityStudentDocument;
import com.scube.edu.repository.CollegeRepository;
import com.scube.edu.repository.StreamRepository;
import com.scube.edu.repository.UniversityStudentDocRepository;
import com.scube.edu.repository.YearOfPassingRepository;
import com.scube.edu.response.UniversityStudentDocumentResponse;
import com.scube.edu.util.FileStorageService;

@Service
public class AssociateManagerServiceImpl implements AssociateManagerService{

	private static final Logger logger = LoggerFactory.getLogger(MasterController.class);

	@Autowired
	 UniversityStudentDocRepository 	 universityStudentDocRepository ;
	
	@PersistenceUnit
	 private EntityManagerFactory emf;
	
	 @Autowired
	 private FileStorageService fileStorageService;
	 
	 @Autowired
	CollegeRepository collegeRespository;
	 
	 @Autowired
		StreamRepository  streamRespository;
	 @Autowired
		YearOfPassingRepository yearOfPassingRespository;
	
	private XSSFWorkbook workbook;
	
	
	@Override
	public  HashMap<String,List<UniversityStudentDocument>> saveStudentInfo(List<UniversityStudentDocument> list) throws IOException {
		
		 List<UniversityStudentDocument> studentDataList = new ArrayList<UniversityStudentDocument>();
		 UniversityStudentDocument docEntity =null;
		 List<UniversityStudentDocument> rejectedData=new ArrayList<UniversityStudentDocument>();
		 HashMap<String,List<UniversityStudentDocument>> datalist=new HashMap<String,List<UniversityStudentDocument>>();
		
		 for(UniversityStudentDocument Data:list) {
			 
			 			String enrollNo=Data.getEnrollmentNo();
			 			String fnm=Data.getFirstName();
			 			String lnm=Data.getLastName();
			 			String strm=Data.getStream();
			 			String clgnm=Data.getCollegeName();
			 			Integer passyr=Data.getPassingYear();
			 docEntity=universityStudentDocRepository.findByEnrollmentNoAndFirstNameAndLastNameAndStreamAndCollegeNameAndPassingYear(enrollNo,fnm,lnm,strm,clgnm,passyr);
			String reason = null;
			 if(docEntity==null) {	
				
				CollegeMaster collegeEntities  = collegeRespository.findByCollegeName(Data.getCollegeName());
				StreamMaster stream =streamRespository.findByStreamName(Data.getStream());
				PassingYearMaster passingyr=yearOfPassingRespository.findByYearOfPassing(Integer.toString(Data.getPassingYear()));
				
				if(collegeEntities!=null && stream!=null && passingyr!=null) {
					
				UniversityStudentDocument studentData=new UniversityStudentDocument();
				studentData.setFirstName(Data.getFirstName());
		        studentData.setLastName(Data.getLastName());
		        studentData.setCollegeName(Data.getCollegeName());	
		        studentData.setStream(Data.getStream());	
		        studentData.setEnrollmentNo(Data.getEnrollmentNo());
		        studentData.setPassingYear(Data.getPassingYear());	
		        studentData.setOriginalDOCuploadfilePath(Data.getOriginalDOCuploadfilePath());
		        studentDataList.add(studentData);    
				}
				
					 else
					 {	
						 UniversityStudentDocument studentData=new UniversityStudentDocument();
						 if(collegeEntities==null && stream==null && passingyr==null) {
						 reason="Invalid College Name, Stream and Year of Passing";
						 }
						 else if(collegeEntities==null && stream==null)
						 {
							 reason="Invalid College Name and Stream";
						 }
						 else if(collegeEntities==null && passingyr==null)
						 {
							 reason="Invalid College Name and Year of Passing";
						 }
						 else if(stream==null && passingyr==null)
						 {
							 reason="Invalid Year of Passing and Stream";
						 }
						 else if(collegeEntities==null)
						 {
							 reason="Invalid College Name";

						 }
						 else if(stream==null)
				   		 {
							 reason="Invalid Stream";

						 }
						 else if(passingyr==null)
						 {
							 reason="Invalid Year of Passing";
						 }
					    studentData.setFirstName(Data.getFirstName());
				        studentData.setLastName(Data.getLastName());
				        studentData.setCollegeName(Data.getCollegeName());	
				        studentData.setStream(Data.getStream());	
				        studentData.setEnrollmentNo(Data.getEnrollmentNo());
				        studentData.setPassingYear(Data.getPassingYear());	
				        studentData.setOriginalDOCuploadfilePath(Data.getOriginalDOCuploadfilePath());
				        studentData.setReason(reason);
				        rejectedData.add(studentData);				
					 }
			 }
			 else
			 {	 UniversityStudentDocument studentData=new UniversityStudentDocument();
			 	reason="Duplicate record";
			     studentData.setFirstName(Data.getFirstName());
		        studentData.setLastName(Data.getLastName());
		        studentData.setCollegeName(Data.getCollegeName());	
		        studentData.setStream(Data.getStream());	
		        studentData.setEnrollmentNo(Data.getEnrollmentNo());
		        studentData.setPassingYear(Data.getPassingYear());	
		        studentData.setOriginalDOCuploadfilePath(Data.getOriginalDOCuploadfilePath());
		        studentData.setReason(reason);

		        rejectedData.add(studentData);				
			 }
			
		 }
		 logger.info("rejectedData : "+rejectedData);
		 universityStudentDocRepository.saveAll(studentDataList);
		 datalist.put("savedStudentData", studentDataList);
		 datalist.put("RejectedData", rejectedData);
		 return datalist;
		 
	}

	@Override
	public List<UniversityStudentDocument> ReviewStudentData(MultipartFile excelfile, MultipartFile datafile) throws IOException {
		
		logger.info("********AssociateManagerServiceImpl ReviewStudentData********");

		 workbook = new XSSFWorkbook(excelfile.getInputStream());
		 XSSFSheet worksheet = workbook.getSheetAt(0);
		 List<UniversityStudentDocument> studentDataReviewList = new ArrayList<UniversityStudentDocument>();
		
		 String fileSubPath = "file/";
		 String filePath = fileStorageService.storeFile(datafile , fileSubPath);
		 for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
			 
			 XSSFRow row = worksheet.getRow(i);		 
			 
				 UniversityStudentDocument studentData = new UniversityStudentDocument();	            
		        
		        studentData.setFirstName(row.getCell(0).getStringCellValue());
		        studentData.setLastName(row.getCell(1).getStringCellValue());
		        studentData.setCollegeName(row.getCell(2).getStringCellValue());	
		        studentData.setStream(row.getCell(3).getStringCellValue());	
		        int cellType=row.getCell(4).getCellType();
		        
		 logger.info("celltype "+cellType);
		 if(cellType==1)
		 {
		        studentData.setEnrollmentNo(row.getCell(4).getStringCellValue());
		 }
		 else
		 {
		        Integer enrollNo=(int) row.getCell(4).getNumericCellValue();		        
		        studentData.setEnrollmentNo(enrollNo.toString());			 
		 }
		        studentData.setPassingYear((int) row.getCell(5).getNumericCellValue());	
		        studentData.setOriginalDOCuploadfilePath(filePath);
		        studentDataReviewList.add(studentData);    
			 }
	
		 return studentDataReviewList;
	}

	@Override
	public List<UniversityStudentDocument> getStudentData(UniversityStudentDocument universityStudData ) {
		
		logger.info("********AssociateManagerServiceImpl getStudentData********");

		 List<UniversityStudentDocument> studentDataList = new ArrayList<UniversityStudentDocument>();
		 String query="";
		 query="Select data from UniversityStudentDocument data where ";
		 
		 if(universityStudData!=null)
		 {
			 String Name=universityStudData.getFirstName();
			 String lastName=universityStudData.getLastName();
			 String Stream=universityStudData.getStream();
			 String clgName=universityStudData.getCollegeName();
			 String enrollNo=universityStudData.getEnrollmentNo();
			 Integer YearOfPassing=universityStudData.getPassingYear(); 
			 
			 if(!Name.isEmpty())
			 {
				 query=query+" firstName='"+universityStudData.getFirstName()+"'";
				 if(!lastName.isEmpty()||!Stream.isEmpty()||!clgName.isEmpty()||!enrollNo.isEmpty()||YearOfPassing!=null)
				 {
				 query=query+" and";
				 }
			 }
				 
			 if(!universityStudData.getLastName().isEmpty())
			 {
				 query=query+" lastName='"+universityStudData.getLastName()+"'";
				 if(!Stream.isEmpty()||!clgName.isEmpty()||!enrollNo.isEmpty()||YearOfPassing!=null)				 {
					
				 query=query+" and";
				 }
			 }
			 if(!universityStudData.getStream().isEmpty())
			 {
				 query=query+"  stream='"+universityStudData.getStream()+"'";
				 if(!clgName.isEmpty()||!enrollNo.isEmpty()||YearOfPassing!=null)				 {
				  query=query+" and";
				 }
			 }
			 if(!universityStudData.getCollegeName().isEmpty())
			 {
				 query=query+"  collegeName='"+universityStudData.getCollegeName()+"'";
				 if(!enrollNo.isEmpty()||YearOfPassing!=null)				 {
				 query=query+" and";
				 }
			 }
			 if(!universityStudData.getEnrollmentNo().isEmpty())
			 {
				 query=query+"  enrollmentNo='"+universityStudData.getEnrollmentNo()+"'";
				 if(universityStudData.getPassingYear()!=null)
				 {
				 query=query+" and";
				 }
			 }
			 if(universityStudData.getPassingYear()!=null)
			 {
				 query=query+" passingYear="+universityStudData.getPassingYear()+"";
			 }
		 }
		 
		 logger.info(query);
		 
		 EntityManager em = emf.createEntityManager();
		 studentDataList= em.createQuery(query).getResultList();
		// studentDataList=universityStudentDocRepository.getStudData(query);
		 List<UniversityStudentDocument> studData=new ArrayList<>();
		 for(UniversityStudentDocument setStudentdata:studentDataList)
		 {
			 UniversityStudentDocument studDataResponse=new UniversityStudentDocument();
			 studDataResponse.setId(setStudentdata.getId());
			 studDataResponse.setFirstName(setStudentdata.getFirstName());
			 studDataResponse.setLastName(setStudentdata.getLastName());
			 studDataResponse.setCollegeName(setStudentdata.getCollegeName());
			 studDataResponse.setEnrollmentNo(setStudentdata.getEnrollmentNo());
			 studDataResponse.setStream(setStudentdata.getStream());
			 studDataResponse.setPassingYear(setStudentdata.getPassingYear());
			 //String Path=
			 studDataResponse.setOriginalDOCuploadfilePath("/verifier/getimage/U/"+setStudentdata.getId());
			 studData.add(studDataResponse);
		 }
		 logger.info("Data"+studData);
		return studData;
	}

}
