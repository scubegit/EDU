package com.scube.edu.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.scube.edu.controller.MasterController;
import com.scube.edu.model.BranchMasterEntity;
import com.scube.edu.model.CollegeMaster;
import com.scube.edu.model.MonthOfPassing;
import com.scube.edu.model.PassingYearMaster;
import com.scube.edu.model.SemesterEntity;
import com.scube.edu.model.StreamMaster;
import com.scube.edu.model.UniversityStudentDocument;
import com.scube.edu.repository.CollegeRepository;
import com.scube.edu.repository.MonthOfPassingRepository;
import com.scube.edu.repository.SemesterRepository;
import com.scube.edu.repository.StreamRepository;
import com.scube.edu.repository.UniversityStudentDocRepository;
import com.scube.edu.repository.YearOfPassingRepository;
import com.scube.edu.request.UniversityStudentRequest;
import com.scube.edu.response.UniversityStudDocResponse;
import com.scube.edu.response.UniversityStudentDocumentResponse;
import com.scube.edu.util.FileStorageService;

@Service
public class AssociateManagerServiceImpl implements AssociateManagerService{

	private static final Logger logger = LoggerFactory.getLogger(AssociateManagerServiceImpl.class);

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
	 @Autowired
	 MonthOfPassingRepository monthOfPassingRepository;
	 
	 @Autowired
		SemesterService semesterService;
		
	 @Autowired
	 SemesterRepository semesterRepository;
		@Autowired
		BranchMasterService branchMasterService;
		
	
	private XSSFWorkbook workbook;
	
	
	@Override
	public  HashMap<String,List<UniversityStudDocResponse>> saveStudentInfo(List<UniversityStudDocResponse> list, Long userid) throws IOException {
		
		List<UniversityStudentDocument> studentDataList = new ArrayList<UniversityStudentDocument>();
		 List<UniversityStudDocResponse> savedStudDataList = new ArrayList<UniversityStudDocResponse>();

		 Set<UniversityStudentDocument> savedset = new HashSet<UniversityStudentDocument>();
		// Set<UniversityStudentDocument> set1 = new HashSet<UniversityStudentDocument>();

		 UniversityStudentDocument docEntity =null;
		 List<UniversityStudDocResponse> rejectedData=new ArrayList<UniversityStudDocResponse>();
		 HashMap<String,List<UniversityStudDocResponse>> datalist=new HashMap<String,List<UniversityStudDocResponse>>();
		
		 int c=1;
		 for(UniversityStudDocResponse Data:list) {
			 
			    Long clgnm = null;
				Long passyr = null;
				Long strm = null;
				Long semId = null;
				Long branchId = null;
				String monthnm=null;
			
				MonthOfPassing month=monthOfPassingRepository.findByMonthOfPassing(Data.getMonthOfPassing());
				 StreamMaster stream =streamRespository.findByStreamNameAndIsdeleted(Data.getStream(),"N");
				
				 if(month!=null) {
					 monthnm=month.getMonthOfPassing();
				 }
				 if(stream!=null) 
				  { 
					  strm=stream.getId(); 
				  logger.info("strm"+strm);
				  }
				  PassingYearMaster passingyr=yearOfPassingRespository.findByYearOfPassingAndIsdeleted(Data.getPassingYear(),"N");
				 
					 SemesterEntity sem=semesterRepository.findBySemesterAndStreamIdAndIsdeleted(Data.getSemester(),strm,"N");				
				//  BranchMasterEntity branch=branchMasterService.getbranchIdByname(Data.getBranch_nm(),strm);
				

			 			String enrollNo=Data.getEnrollmentNo();
			 			
			   if(passingyr!=null) {
			 
			  passyr=passingyr.getId();
			  logger.info("passyr"+passyr); }
			 
			 			if(sem!=null) {

				 			 semId=sem.getId();
				 			logger.info("semId"+semId);
				 			}
		
			 			
			 docEntity=universityStudentDocRepository.findByEnrollmentNoAndStreamIdAndPassingYearIdAndSemIdAndMonthOfPassing(enrollNo,strm,passyr,semId,monthnm);
			String reason = null;
			 if(docEntity==null) {	
				
				
				if(passingyr!=null&&sem!=null && !Data.getEnrollmentNo().equals("")&&month!=null&&!Data.getOriginalDOCuploadfilePath().equals("ImageNotAvailable")) {
					//if(Data.getOriginalDOCuploadfilePath().equals("FileNotAvailable")) {
				UniversityStudDocResponse SavestudentData=new UniversityStudDocResponse();
				UniversityStudentDocument studentData1=new UniversityStudentDocument();

				
				//studentData.setId((long)1);
				studentData1.setStreamId(stream.getId());	
				studentData1.setEnrollmentNo(Data.getEnrollmentNo());
				studentData1.setPassingYearId(passingyr.getId());	
				studentData1.setOriginalDOCuploadfilePath(Data.getOriginalDOCuploadfilePath());
				studentData1.setSemId(sem.getId());
		       // studentData.setBranchId(branch.getId());
		     //   studentData.setCreatedate(new Date());
				studentData1.setCreateby(userid.toString());
				studentData1.setIsdeleted("N");
				studentData1.setMonthOfPassing(monthnm);
		        
		      if(!studentDataList.contains(studentData1)) {
		        studentDataList.add(studentData1);
		        SavestudentData.setStream(Data.getStream());	
		        SavestudentData.setEnrollmentNo(Data.getEnrollmentNo());
		        SavestudentData.setPassingYear(Data.getPassingYear());	
		        SavestudentData.setOriginalDOCuploadfilePath(Data.getOriginalDOCuploadfilePath());
		        SavestudentData.setSemester(Data.getSemester());
		        SavestudentData.setMonthOfPassing(monthnm);

		        savedStudDataList.add(SavestudentData);	
		        
		      }
		      else {
		    	  UniversityStudDocResponse studentData=new UniversityStudDocResponse();
				 	reason="Duplicate Record";
				   	
			        studentData.setStream(Data.getStream());	
			        studentData.setSemester(Data.getSemester());
			        studentData.setEnrollmentNo(Data.getEnrollmentNo());
			        studentData.setPassingYear(Data.getPassingYear());	
			        studentData.setMonthOfPassing(Data.getMonthOfPassing());	
			        studentData.setOriginalDOCuploadfilePath(Data.getOriginalDOCuploadfilePath());
			        studentData.setReason(reason);

			        rejectedData.add(studentData);				
		      }
		      
		      
				
				}
				
					 else
					 {	
						 UniversityStudDocResponse studentData=new UniversityStudDocResponse();
						 if(passingyr==null|| sem==null||stream==null||month==null) {
						 reason="Invalid";
						  if(stream==null) { 
					    	   
					    	   reason=reason+" Stream";
						      if(passingyr==null||sem==null||month==null) { 
						    	  
							  reason=reason+",";
						  
						  } }
						 
							  if(passingyr==null)
							 {
								 reason=reason+" Year of Passing";
								 if(sem==null||month==null)
								 {
									 reason=reason+",";

								 }

							 }
							  if(sem==null)
							 {
								 reason=reason+" Semester";
							
								 if(month==null)
								 {
									 reason=reason+",";

								 }
							 }
							  if(month==null) {
									
									 reason=reason+" Month Of Passing";

								 }
						 }
				
				     
						 if( Data.getOriginalDOCuploadfilePath().equals("ImageNotAvailable")) {
							
							 if(reason!=null) {
								 reason=reason+", ImageNotAvailable";

							 }
							 else {
								 reason=" ImageNotAvailable";

							 }
						 }
						
						 if(Data.getEnrollmentNo()==null || Data.getEnrollmentNo().equals("") )
						 {
							 if(reason!=null) {
								 reason=reason+" & Blank";

							 }
							 else {
							 reason="Blank";
							 }
						 }
						 
					
						 if(Data.getEnrollmentNo()==null || Data.getEnrollmentNo().equals("") )
						 {
							 reason=reason+" Seat No";
							
						 }
						
					 
				        studentData.setSemester(Data.getSemester());
				        studentData.setStream(Data.getStream());	
				        studentData.setEnrollmentNo(Data.getEnrollmentNo());
				        studentData.setPassingYear(Data.getPassingYear());	
				        studentData.setOriginalDOCuploadfilePath(Data.getOriginalDOCuploadfilePath());
				        studentData.setMonthOfPassing(Data.getMonthOfPassing());
				        studentData.setReason(reason);
				        rejectedData.add(studentData);				
					 }
			 }
			 else
			 {	 UniversityStudDocResponse studentData=new UniversityStudDocResponse();
			 	reason="Duplicate record";
			   	
		        studentData.setStream(Data.getStream());	
		       // studentData.setBranch_nm(Data.getBranch_nm());
		        studentData.setSemester(Data.getSemester());
		        studentData.setEnrollmentNo(Data.getEnrollmentNo());
		        studentData.setPassingYear(Data.getPassingYear());	
		        studentData.setMonthOfPassing(Data.getMonthOfPassing());	
		        studentData.setOriginalDOCuploadfilePath(Data.getOriginalDOCuploadfilePath());
		        studentData.setReason(reason);

		        rejectedData.add(studentData);				
			 }
			
		 }
		 logger.info("rejectedData : "+rejectedData);
		// logger.info("set1 : "+set1.toString());
		 logger.info("savedset : "+savedset.toString());
	
		 List<UniversityStudentDocument> unique = studentDataList.stream().distinct().collect(Collectors.toList());
		 
		 universityStudentDocRepository.saveAll(studentDataList);
		
		 datalist.put("savedStudentData", savedStudDataList);
		 datalist.put("RejectedData", rejectedData);
		 return datalist;
		 
	}

	@Value("${file.awsORtest}")
    private String awsORtest;
	
	@Override
	public List<UniversityStudDocResponse> ReviewStudentData(MultipartFile excelfile, MultipartFile datafile) throws IOException {
		
		logger.info("********AssociateManagerServiceImpl ReviewStudentData********");

		 workbook = new XSSFWorkbook(excelfile.getInputStream());
		 XSSFSheet worksheet = workbook.getSheetAt(0);
		 List<UniversityStudDocResponse> studentDataReviewList = new ArrayList<UniversityStudDocResponse>();
		int rowcnt=worksheet.getLastRowNum();
		
		int clomncnt=worksheet.getRow(0).getLastCellNum() ;
		
		int noOfColumns = worksheet.getRow(0).getPhysicalNumberOfCells();

		logger.info("noOfColumns="+noOfColumns);

		logger.info("clomncnt="+clomncnt);
		 String fileSubPath = "file/";
		 String filePath;
		 
		 String flag = "2";
		 if(awsORtest.equalsIgnoreCase("TEST") || awsORtest.equalsIgnoreCase("LOCAL")) {
			 
			 filePath = fileStorageService.storeFile(datafile, fileSubPath, flag);
			 
		 }
		 else if(awsORtest.equalsIgnoreCase("InHouse") ) {
			 filePath = fileStorageService.storeFileOnFtp(datafile , flag);		 }
		 else {
			 filePath = fileStorageService.storeFileOnAws(datafile , flag);   // For AWS
			 
		 }
		 if(clomncnt==5) {
		 for(int i=1;i<=rowcnt;i++) {
			 
			 XSSFRow row = worksheet.getRow(i);		 
			

			 UniversityStudDocResponse studentData = new UniversityStudDocResponse();	            
		     if(row!=null) {
		    	 int rowcell= row.getPhysicalNumberOfCells();
					logger.info("rowcell="+rowcell);
					
		     
		     if(row.getCell(0)!=null){
					 
					 studentData.setStream(row.getCell(0).getStringCellValue()); 
					 }
					 else {
					 studentData.setStream(""); }
					 
			 if(row.getCell(1)!=null){

		        studentData.setSemester(row.getCell(1).getStringCellValue());	
			 }
			 else {
				 studentData.setSemester(""); 
			 }

			 if(row.getCell(2)!=null){
		        int cellType=row.getCell(2).getCellType();
		        
		 logger.info("celltype "+cellType);
		 if(cellType==1)
		 {
		        studentData.setEnrollmentNo(row.getCell(2).getStringCellValue());
		 }
		 else
		 {
		        Integer enrollNo=(int) row.getCell(2).getNumericCellValue();		        
		        studentData.setEnrollmentNo(enrollNo.toString());			 
		 }
			 }
			 else {
				 studentData.setEnrollmentNo("");
			 }
			 
			 
			 if(row.getCell(3)!=null){

		        studentData.setMonthOfPassing(row.getCell(3).getStringCellValue());	
			 }
			 else {
				 studentData.setMonthOfPassing(""); 
			 }

		 if(row.getCell(4)!=null){
	        int yearcellType=row.getCell(4).getCellType();

		 if(yearcellType==1)
		 {
		        studentData.setPassingYear(row.getCell(4).getStringCellValue());
		 }
		 else {
		        Integer yearofPassing=(int) row.getCell(4).getNumericCellValue();
		        studentData.setPassingYear(yearofPassing.toString());		        
		 }
			 }
			 else {
				 studentData.setPassingYear("");
			 }
		        studentData.setOriginalDOCuploadfilePath(filePath);
		        studentDataReviewList.add(studentData);    
			 }
		 }
		 }
		 
		 return studentDataReviewList;
	}

	@Override
	public List<UniversityStudDocResponse> getStudentData1(UniversityStudentRequest universityStudData ) {
		
		logger.info("********AssociateManagerServiceImpl getStudentData********");

		 List<UniversityStudentDocument> studentDataList = new ArrayList<UniversityStudentDocument>();
		 
		/*
		 * List<UniversityStudentDocument> usdr = universityStudentDocRepository.
		 * searchByEnrollmentNoLikeAndPassingYearIdLikeAndStreamIdLikeAndSemIdLikeAndMonthOfPassingLike(
		 * universityStudData.getEnrollmentNo(), universityStudData.getPassingYearId(),
		 * universityStudData.getStreamId(),universityStudData.getSemId(),
		 * universityStudData.getMonthOfPassing());
		 * 
		 */
		
		List<UniversityStudDocResponse> studData=new ArrayList<>();
		
		String enrollementNo=null;
		 Long passingyrid=null;
		 Long strmid=null;
		 Long semid=null;
		 String monthofPass=null;
//		 String prno=null;
		 
		 if(!universityStudData.getEnrollmentNo().equalsIgnoreCase("")) {
			 logger.info("HERE");
		 }
		 if(!universityStudData.getEnrollmentNo().equalsIgnoreCase("")) {
		   enrollementNo= universityStudData.getEnrollmentNo(); 
		 }
		 if(!universityStudData.getPassingYearId().equals("")) {

			 System.out.println("PssingyrData"+universityStudData.getPassingYearId());
			 passingyrid=Long.valueOf(universityStudData.getPassingYearId()); 
		 }
		 if(!universityStudData.getStreamId().equals("")) {

			 strmid=Long.valueOf(universityStudData.getStreamId()); 
		 }
		 if(!universityStudData.getSemId().equals("")) {

			 semid=Long.valueOf(universityStudData.getSemId());
		 }
		 if(!universityStudData.getMonthOfPassing().equals("")) {

			 monthofPass=universityStudData.getMonthOfPassing();
		 }
//		 if(!universityStudData.getPrnNo().equals("")) {
//
//			 prno=universityStudData.getPrnNo();
//		 }
		 
		
		List<UniversityStudentDocument> usdr=getUniversityStudData(enrollementNo,  passingyrid, strmid, semid, monthofPass);
		
		  
		 int i=0;
		 if(usdr!=null) {
			  System.out.println("sixe"+usdr.size());

		 for(UniversityStudentDocument studentData:usdr) {
			 
			  System.out.println("i=="+i);

			 UniversityStudDocResponse resp = new UniversityStudDocResponse();
			/*
			 * Optional<CollegeMaster> cm =
			 * collegeRespository.findById(studentData.getCollegeId()); CollegeMaster
			 * college = cm.get();
			 */
			 
			 Optional<StreamMaster> streaminfo = streamRespository.findById(studentData.getStreamId());
			 StreamMaster stream = streaminfo.get();
			 
			 Optional<PassingYearMaster> passingyrinfo = yearOfPassingRespository.findById(studentData.getPassingYearId());
			 PassingYearMaster passingyr = passingyrinfo.get();
			 
			 SemesterEntity sem=semesterService.getSemById(studentData.getSemId());
				
			// BranchMasterEntity branch=branchMasterService.getbranchById(studentData.getBranchId());
				
			 
			 resp.setId(studentData.getId());
			// resp.setFirstName(studentData.getFirstName());
			// resp.setLastName(studentData.getLastName());
			// resp.setCollegeName(college.getCollegeName());
			 resp.setEnrollmentNo(studentData.getEnrollmentNo());
			 resp.setStream(stream.getStreamName());
			 resp.setPassingYear(passingyr.getYearOfPassing());
			// resp.setBranch_nm(branch.getBranchName());
			 resp.setSemester(sem.getSemester());
			 resp.setMonthOfPassing(studentData.getMonthOfPassing());
			 //String Path=
			 resp.setOriginalDOCuploadfilePath("/verifier/getimage/U/"+studentData.getId());
			 studData.add(resp);
			 i++;
		 }
		 }
		return studData;
	}
	@Override
	public String saveassociatefilepath(MultipartFile datafile) {
		 String fileSubPath = "file/";
		 String filePath;
		 
		 String flag = "2";
		 if(awsORtest.equalsIgnoreCase("TEST") || awsORtest.equalsIgnoreCase("LOCAL")) {
			 
			 filePath = fileStorageService.storeFile(datafile, fileSubPath, flag);
		 }
		 else if(awsORtest.equalsIgnoreCase("InHouse") ) {
			 filePath = fileStorageService.storeFileOnFtp(datafile , flag);		
			 }
		 else {
	
			 filePath = fileStorageService.storeFileOnAws(datafile , flag);   // For AWS
		 }				 
		 return filePath;
	}
	
	
	@Override
	public  HashMap<String,List<UniversityStudDocResponse>> AutosaveStudentInfo(List<UniversityStudDocResponse> list, Long userid) throws IOException {
		
		 List<UniversityStudentDocument> studentDataList = new ArrayList<UniversityStudentDocument>();
		 List<UniversityStudDocResponse> savedStudDataList = new ArrayList<UniversityStudDocResponse>();

		 UniversityStudentDocument docEntity =null;
		 List<UniversityStudDocResponse> rejectedData=new ArrayList<UniversityStudDocResponse>();
		 HashMap<String,List<UniversityStudDocResponse>> datalist=new HashMap<String,List<UniversityStudDocResponse>>();
		
		 for(UniversityStudDocResponse Data:list) {
			 
			    Long clgnm = null;
				Long passyr = null;
				Long strm = null;
				Long semId = null;
				Long branchId = null;
				String monthnm=null;
			/*
			 * CollegeMaster collegeEntities =
			 * collegeRespository.findByCollegeName(Data.getCollegeName()); */
				MonthOfPassing month=null;
				if(Data.getMonthOfPassing()!=null) {
				month=monthOfPassingRepository.findByMonthOfPassing(Data.getMonthOfPassing());
				if(month!=null) {
					 monthnm=month.getMonthOfPassing();
				 }
				}
				StreamMaster stream=null;
				if(Data.getStream()!=null) {
				 stream =streamRespository.findByStreamNameAndIsdeleted(Data.getStream(),"N");
				 if(stream!=null) 
				  { 
					  strm=stream.getId(); 
				  logger.info("strm"+strm);
				  }
				}
				
				 
				PassingYearMaster passingyr=null;
				 if(Data.getPassingYear()!=null) {
					 passingyr=yearOfPassingRespository.findByYearOfPassingAndIsdeleted(Data.getPassingYear(),"N");
				  if(passingyr!=null) {
						 
					  passyr=passingyr.getId();
					  logger.info("passyr"+passyr); }
				 }
				 SemesterEntity sem=null;
				 if(Data.getSemester()!=null) {
					sem=semesterRepository.findBySemesterAndStreamIdAndIsdeleted(Data.getSemester(),strm,"N");	
					 if(sem!=null) {

			 			 semId=sem.getId();
			 			logger.info("semId"+semId);
			 			}
				 }
				//  BranchMasterEntity branch=branchMasterService.getbranchIdByname(Data.getBranch_nm(),strm);
				 String enrollNo=null;
				 if(Data.getEnrollmentNo()!=null&& !Data.getEnrollmentNo().equals("")) {
					 enrollNo=Data.getEnrollmentNo();
				 }
			 			//String fnm=Data.getFirstName();
			 			//String lnm=Data.getLastName();
			 			
			/*
			 * if(collegeEntities!=null) { clgnm=collegeEntities.getId();
			 * logger.info("clgnm"+clgnm); }*/
			  
			 String imgPath=null;
			 			
			
			  if(Data.getOriginalDOCuploadfilePath()!=null) {
			  
				  imgPath=  Data.getOriginalDOCuploadfilePath(); 
			  logger.info("imgPath"+imgPath); }
			 
			 			
			 			
			 docEntity=universityStudentDocRepository.findByEnrollmentNoAndStreamIdAndPassingYearIdAndSemIdAndMonthOfPassing(enrollNo,strm,passyr,semId,monthnm);
			String reason = null;
			 if(docEntity==null) {	
				
				
				if(passyr!=null&&semId!=null &&strm!=null&& enrollNo!=null&&month!=null&& (imgPath!=null&& !imgPath.equals("ImageNotAvailable"))) {
					//if(Data.getOriginalDOCuploadfilePath().equals("FileNotAvailable")) {
				UniversityStudentDocument studentData=new UniversityStudentDocument();
				UniversityStudDocResponse SavestudentData=new UniversityStudDocResponse();

			//	studentData.setFirstName(Data.getFirstName());
		      //  studentData.setLastName(Data.getLastName());
		       // studentData.setCollegeId(collegeEntities.getId());	
		        studentData.setStreamId(stream.getId());	
		        studentData.setEnrollmentNo(Data.getEnrollmentNo());
		        studentData.setPassingYearId(passingyr.getId());	
		        studentData.setOriginalDOCuploadfilePath(Data.getOriginalDOCuploadfilePath());
		        studentData.setSemId(sem.getId());
		       // studentData.setBranchId(branch.getId());
		        studentData.setCreatedate(new Date());
		        studentData.setCreateby(userid.toString());
		        studentData.setMonthOfPassing(monthnm);
		        if(!studentDataList.contains(studentData)) {
		        studentDataList.add(studentData);    
		        
		        //SavestudentData.setFirstName(Data.getFirstName());
		       // SavestudentData.setLastName(Data.getLastName());
		      //  SavestudentData.setCollegeName(Data.getCollegeName());	
		        SavestudentData.setStream(Data.getStream());	
		        SavestudentData.setEnrollmentNo(Data.getEnrollmentNo());
		        SavestudentData.setPassingYear(Data.getPassingYear());	
		        SavestudentData.setOriginalDOCuploadfilePath(Data.getOriginalDOCuploadfilePath());
		      //  SavestudentData.setReason(reason);
		        //SavestudentData.setBranch_nm(Data.getBranch_nm());
		        SavestudentData.setSemester(Data.getSemester());
		        SavestudentData.setMonthOfPassing(monthnm);

		        savedStudDataList.add(SavestudentData);	
				}
		        
		        else {
		        	UniversityStudDocResponse rejstudentData=new UniversityStudDocResponse();
				 	reason="Duplicate Entry";
				    
				 	rejstudentData.setStream(Data.getStream());	
				 	rejstudentData.setSemester(Data.getSemester());
				 	rejstudentData.setEnrollmentNo(Data.getEnrollmentNo());
				 	rejstudentData.setPassingYear(Data.getPassingYear());	
				 	rejstudentData.setMonthOfPassing(Data.getMonthOfPassing());	
				 	rejstudentData.setOriginalDOCuploadfilePath(Data.getOriginalDOCuploadfilePath());
				 	rejstudentData.setReason(reason);

			        rejectedData.add(rejstudentData);	
		        }
				}
				
					 else
					 {	
						 UniversityStudDocResponse studentData=new UniversityStudDocResponse();
						 if(passingyr==null|| sem==null||stream==null||month==null) {
						 reason="Invalid";
						  if(stream==null) { 
					    	   
					    	   reason=reason+" Stream";
						      if(passingyr==null||sem==null||month==null) { 
						    	  
							  reason=reason+",";
						  
						  } }
						 
							  if(passingyr==null)
							 {
								 reason=reason+" Year of Passing";
								 if(sem==null||month==null)
								 {
									 reason=reason+",";

								 }

							 }
							  if(sem==null)
							 {
								 reason=reason+" Semester";
							/*
							 * if(branch==null) { reason=reason+",";
							 * 
							 * }
							 * 
							 * } if(branch==null) { reason=reason+" Branch";
							 * 
							 */
								 if(month==null)
								 {
									 reason=reason+",";

								 }
							 }
							  if(month==null) {
									
									 reason=reason+" Month Of Passing";

								 }
						 }
					/*
					 * if(collegeEntities==null) { reason=reason+" College Name";
					  if(stream==null||passingyr==null||sem==null||branch==null) {
					  reason=reason+",";
					 * 
					  }
					  } */
				     
						 if( imgPath!=null && imgPath.equals("ImageNotAvailable")) {
							
							 if(reason!=null) {
								 reason=reason+", ImageNotAvailable";

							 }
							 else {
								 reason=" ImageNotAvailable";

							 }
						 }else {
							 if(reason!=null) {
								 reason=reason+", BankImageName";

							 }
							 else {
								 reason=" ImageNotAvailable";

							 }
						 }
						
						 if(enrollNo==null)
						 {
							 if(reason!=null) {
								 reason=reason+" & Blank";

							 }
							 else {
							 reason="Blank";
							 }
						 }
						 
						 
					/*
					 * if(Data.getFirstName()==null || Data.getFirstName().equals("")) {
					 * reason=reason+" First name"; if (Data.getLastName() == null ||
					 * Data.getLastName().equals("") || Data.getLastName() == null ||
					 * Data.getLastName().equals("") || Data.getEnrollmentNo() == null ||
					 * Data.getEnrollmentNo().equals("")) { reason=reason+","; } }
					 * if(Data.getLastName()==null || Data.getLastName().equals("") ) {
					 * reason=reason+" Last name"; if(Data.getEnrollmentNo()==null ||
					 * Data.getEnrollmentNo().equals("")) { reason=reason+","; } }
					 */
						 if(enrollNo==null )
						 {
							 reason=reason+" Seat No";
							
						 }
						
					   // studentData.setFirstName(Data.getFirstName());
				       // studentData.setLastName(Data.getLastName());
				       // studentData.setCollegeName(Data.getCollegeName());
				       // studentData.setBranch_nm(Data.getBranch_nm());
						 if(Data.getSemester()!=null) {
				        studentData.setSemester(Data.getSemester());
						 }
						 else {
						        studentData.setSemester("");

						 }
						 if(Data.getStream()!=null) {
				        studentData.setStream(Data.getStream());
						 }
						 else {
						        studentData.setStream("");
 
						 } if(Data.getEnrollmentNo()!=null) {
				        studentData.setEnrollmentNo(Data.getEnrollmentNo());
						 }else {
						        studentData.setEnrollmentNo("");

						 } if(Data.getPassingYear()!=null) {
				        studentData.setPassingYear(Data.getPassingYear());	
						 }else {
						        studentData.setPassingYear("");

						 } if(Data.getOriginalDOCuploadfilePath()!=null) {
				        studentData.setOriginalDOCuploadfilePath(Data.getOriginalDOCuploadfilePath());
						 }else {
						        studentData.setOriginalDOCuploadfilePath("");

						 } if(Data.getMonthOfPassing()!=null) {
				        studentData.setMonthOfPassing(Data.getMonthOfPassing());
						 }else {
						        studentData.setMonthOfPassing("");

						 }
				        studentData.setReason(reason);
				        rejectedData.add(studentData);				
					 }
			 }
			 else
			 {	 UniversityStudDocResponse studentData=new UniversityStudDocResponse();
			 	reason="Duplicate record";
			    //studentData.setFirstName(Data.getFirstName());
		        //studentData.setLastName(Data.getLastName());
		       // studentData.setCollegeName(Data.getCollegeName());	
		        studentData.setStream(Data.getStream());	
		       // studentData.setBranch_nm(Data.getBranch_nm());
		        studentData.setSemester(Data.getSemester());
		        studentData.setEnrollmentNo(Data.getEnrollmentNo());
		        studentData.setPassingYear(Data.getPassingYear());	
		        studentData.setMonthOfPassing(Data.getMonthOfPassing());	
		        studentData.setOriginalDOCuploadfilePath(Data.getOriginalDOCuploadfilePath());
		        studentData.setReason(reason);

		        rejectedData.add(studentData);				
			 }
			
		 }
		 logger.info("rejectedData : "+rejectedData);
		 universityStudentDocRepository.saveAll(studentDataList);
		 datalist.put("savedStudentData", savedStudDataList);
		 datalist.put("RejectedData", rejectedData);
		 return datalist;
		 
	}
	
public List<UniversityStudentDocument> getUniversityStudData(String enrollmentno, Long yearofpassid, Long streamid,Long semId,String monthOfPassing){
		
		//EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PERSISTENCE");

		 EntityManager em = emf.createEntityManager();
       CriteriaBuilder cb = em.getCriteriaBuilder();
       
       List<UniversityStudentDocument> resp = null;
       
       
       CriteriaQuery<UniversityStudentDocument> cq = cb.createQuery(UniversityStudentDocument.class);
       Root<UniversityStudentDocument> book = cq.from(UniversityStudentDocument.class);
       List<Predicate> predicates = new ArrayList<>();
       
     
       if(enrollmentno != null ) {
       	predicates.add(cb.equal(book.get("enrollmentNo"), enrollmentno));
       }
//       if(prnNo != null ) {
//            predicates.add(cb.equal(book.get("prnNo"), prnNo));
//           }
       if(streamid != null) {
       	predicates.add( cb.equal(book.get("streamId"), streamid));
           }
       if(semId != null) {
       	predicates.add(cb.equal(book.get("semId"), semId));
           }
       if(yearofpassid != null) {
       	predicates.add( cb.equal(book.get("passingYearId"), yearofpassid));
           }
       if(monthOfPassing != null ) {
       	predicates.add( cb.equal(book.get("monthOfPassing"), monthOfPassing));
       	
           }
		
       cq.where(predicates.toArray(new Predicate[0]));
       logger.info("cq-----"+cq);
       if(!predicates.isEmpty()) {
       resp=em.createQuery(cq).getResultList();
      }
       
       em.close();
//       emf.close();
       return resp;
	
	}

}
