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
import com.scube.edu.model.BranchMasterEntity;
import com.scube.edu.model.CollegeMaster;
import com.scube.edu.model.PassingYearMaster;
import com.scube.edu.model.SemesterEntity;
import com.scube.edu.model.StreamMaster;
import com.scube.edu.model.UniversityStudentDocument;
import com.scube.edu.repository.CollegeRepository;
import com.scube.edu.repository.StreamRepository;
import com.scube.edu.repository.UniversityStudentDocRepository;
import com.scube.edu.repository.YearOfPassingRepository;
import com.scube.edu.request.UniversityStudentRequest;
import com.scube.edu.response.UniversityStudDocResponse;
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
	 
	 @Autowired
		SemesterService semesterService;
		
		@Autowired
		BranchMasterService branchMasterService;
		
	
	private XSSFWorkbook workbook;
	
	
	@Override
	public  HashMap<String,List<UniversityStudDocResponse>> saveStudentInfo(List<UniversityStudDocResponse> list) throws IOException {
		
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
			 CollegeMaster collegeEntities  = collegeRespository.findByCollegeName(Data.getCollegeName());
				StreamMaster stream =streamRespository.findByStreamName(Data.getStream());
				if(stream!=null) {
		 			 strm=stream.getId();
		 			logger.info("strm"+strm);
		 			}
				PassingYearMaster passingyr=yearOfPassingRespository.findByYearOfPassing(Data.getPassingYear());
				 SemesterEntity sem=semesterService.getSemIdByNm(Data.getSemester(),strm);					
				  BranchMasterEntity branch=branchMasterService.getbranchIdByname(Data.getBranch_nm(),strm);
				

			 			String enrollNo=Data.getEnrollmentNo();
			 			String fnm=Data.getFirstName();
			 			String lnm=Data.getLastName();
			 			
			 			if(collegeEntities!=null) {
			 			 clgnm=collegeEntities.getId();
			 			logger.info("clgnm"+clgnm);
			 			}
			 			if(passingyr!=null) {

			 			 passyr=passingyr.getId();
			 			logger.info("passyr"+passyr);
			 			}
			 			if(sem!=null) {

				 			 semId=sem.getId();
				 			logger.info("semId"+semId);
				 			}
			 			if(branch!=null) {

			 				branchId=branch.getId();
				 			logger.info("branchId"+branchId);
				 			}
			 			
			 docEntity=universityStudentDocRepository.findByEnrollmentNoAndFirstNameAndLastNameAndStreamIdAndCollegeIdAndPassingYearIdAndSemIdAndBranchId(enrollNo,fnm,lnm,strm,clgnm,passyr,semId,branchId);
			String reason = null;
			 if(docEntity==null) {	
				
				
				if(collegeEntities!=null && stream!=null && passingyr!=null&&sem!=null &&branch!=null) {
					
				UniversityStudentDocument studentData=new UniversityStudentDocument();
				UniversityStudDocResponse SavestudentData=new UniversityStudDocResponse();

				studentData.setFirstName(Data.getFirstName());
		        studentData.setLastName(Data.getLastName());
		        studentData.setCollegeId(collegeEntities.getId());	
		        studentData.setStreamId(stream.getId());	
		        studentData.setEnrollmentNo(Data.getEnrollmentNo());
		        studentData.setPassingYearId(passingyr.getId());	
		        studentData.setOriginalDOCuploadfilePath(Data.getOriginalDOCuploadfilePath());
		        studentData.setSemId(sem.getId());
		        studentData.setBranchId(branch.getId());
		        studentDataList.add(studentData);    
		        
		        SavestudentData.setFirstName(Data.getFirstName());
		        SavestudentData.setLastName(Data.getLastName());
		        SavestudentData.setCollegeName(Data.getCollegeName());	
		        SavestudentData.setStream(Data.getStream());	
		        SavestudentData.setEnrollmentNo(Data.getEnrollmentNo());
		        SavestudentData.setPassingYear(Data.getPassingYear());	
		        SavestudentData.setOriginalDOCuploadfilePath(Data.getOriginalDOCuploadfilePath());
		        SavestudentData.setReason(reason);
		        SavestudentData.setBranch_nm(Data.getBranch_nm());
		        SavestudentData.setSemester(Data.getSemester());
		        savedStudDataList.add(SavestudentData);	
		        
				
				}
				
					 else
					 {	
						 UniversityStudDocResponse studentData=new UniversityStudDocResponse();
						 if(collegeEntities==null && stream==null && passingyr==null&& sem==null&&branch==null) {
						 reason="Invalid College Name, Stream, Year of Passing, Semester and Branch";
						 }
						 else if(collegeEntities==null && stream==null && passingyr==null&& sem==null) {
							 reason="Invalid College Name, Stream, Year of Passing and Semester";
							 }
						 else if(collegeEntities==null && stream==null && passingyr==null&&branch==null) {
							 reason="Invalid College Name, Stream, Year of Passing and Branch";
							 }
						 else  if(collegeEntities==null && stream==null && sem==null&&branch==null) {
							 reason="Invalid College Name, Stream, Semetser and Branch";
							 }
						 else if(collegeEntities==null&&passingyr==null&& sem==null&&branch==null) {
							 reason="Invalid College Name, Year of Passing,Semester and Branch";
							 }
						 else if(collegeEntities==null && stream==null&&passingyr==null)
						 {
							 reason="Invalid College Name, Stream and Year of Passing";
						 }
						
						 else if(collegeEntities==null && stream==null&&sem==null)
						 {
							 reason="Invalid College Name, Stream and Semester";
						 }
						 else if(collegeEntities==null && stream==null&&branch==null)
						 {
							 reason="Invalid College Name, Stream and Branch";
						 }
						 else if(collegeEntities==null && passingyr==null&&branch==null)
						 {
							 reason="Invalid College Name, Year of Passing and Branch";
						 }
						 else if(collegeEntities==null && passingyr==null&&sem==null)
						 {
							 reason="Invalid College Name, Year of Passing  and Semester";
						 }
						 else if(collegeEntities==null && sem==null&&branch==null)
						 {
							 reason="Invalid College Name, Semester and Branch";
						 }
						 else if(collegeEntities==null && passingyr==null)
						 {
							 reason="Invalid College Name, Year of Passing";
						 }
						 else if(collegeEntities==null && sem==null)
						 {
							 reason="Invalid College Name and Semester";
						 } 
						 else if(collegeEntities==null && branch==null)
						 {
							 reason="Invalid College Name and Branch";
						 }
						 else if(collegeEntities==null && stream==null)
						 {
							 reason="Invalid College Name and Stream";
						 }
						 else if(passingyr==null && sem==null)
						 {
							 reason="Invalid Semester and Year of Passing";
						 }
						 else if(passingyr==null && stream==null)
						 {
							 reason="Invalid Stream and Year of Passing";
						 }
						 else if(passingyr==null && branch==null)
						 {
							 reason="Invalid Branch and Year of Passing";
						 }
						 else if(stream==null && passingyr==null)
						 {
							 reason="Invalid Year of Passing and Stream";
						 }
						 else if(stream==null && sem==null)
						 {
							 reason="Invalid Semester and Stream";
						 }
						 else if(stream==null && branch==null)
						 {
							 reason="Invalid Branch and Stream";
						 }
						 else if(sem==null && branch==null)
						 {
							 reason="Invalid Semester and branch";
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
						 else if(sem==null)
						 {
							 reason="Invalid Semester";
						 }
						 else if(branch==null)
						 {
							 reason="Invalid Branch";
						 }
					    studentData.setFirstName(Data.getFirstName());
				        studentData.setLastName(Data.getLastName());
				        studentData.setCollegeName(Data.getCollegeName());
				        studentData.setBranch_nm(Data.getBranch_nm());
				        studentData.setSemester(Data.getSemester());
				        studentData.setStream(Data.getStream());	
				        studentData.setEnrollmentNo(Data.getEnrollmentNo());
				        studentData.setPassingYear(Data.getPassingYear());	
				        studentData.setOriginalDOCuploadfilePath(Data.getOriginalDOCuploadfilePath());
				        studentData.setReason(reason);
				        rejectedData.add(studentData);				
					 }
			 }
			 else
			 {	 UniversityStudDocResponse studentData=new UniversityStudDocResponse();
			 	reason="Duplicate record";
			     studentData.setFirstName(Data.getFirstName());
		        studentData.setLastName(Data.getLastName());
		        studentData.setCollegeName(Data.getCollegeName());	
		        studentData.setStream(Data.getStream());	
		        studentData.setBranch_nm(Data.getBranch_nm());
		        studentData.setSemester(Data.getSemester());
		        studentData.setEnrollmentNo(Data.getEnrollmentNo());
		        studentData.setPassingYear(Data.getPassingYear());	
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

	@Override
	public List<UniversityStudDocResponse> ReviewStudentData(MultipartFile excelfile, MultipartFile datafile) throws IOException {
		
		logger.info("********AssociateManagerServiceImpl ReviewStudentData********");

		 workbook = new XSSFWorkbook(excelfile.getInputStream());
		 XSSFSheet worksheet = workbook.getSheetAt(0);
		 List<UniversityStudDocResponse> studentDataReviewList = new ArrayList<UniversityStudDocResponse>();
		
		 String fileSubPath = "file/";
		 String filePath = fileStorageService.storeFile(datafile , fileSubPath);
		 for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
			 
			 XSSFRow row = worksheet.getRow(i);		 
			 
			 UniversityStudDocResponse studentData = new UniversityStudDocResponse();	            
		        
		        studentData.setFirstName(row.getCell(0).getStringCellValue());
		        studentData.setLastName(row.getCell(1).getStringCellValue());
		        studentData.setCollegeName(row.getCell(2).getStringCellValue());	
		        studentData.setStream(row.getCell(3).getStringCellValue());	
		        studentData.setBranch_nm(row.getCell(4).getStringCellValue());	
		        studentData.setSemester(row.getCell(5).getStringCellValue());	

		        int cellType=row.getCell(6).getCellType();
		        
		 logger.info("celltype "+cellType);
		 if(cellType==1)
		 {
		        studentData.setEnrollmentNo(row.getCell(6).getStringCellValue());
		 }
		 else
		 {
		        Integer enrollNo=(int) row.getCell(6).getNumericCellValue();		        
		        studentData.setEnrollmentNo(enrollNo.toString());			 
		 }
	        int yearcellType=row.getCell(7).getCellType();

		 if(yearcellType==1)
		 {
		        studentData.setPassingYear(row.getCell(7).getStringCellValue());
		 }
		 else {
		        Integer yearofPassing=(int) row.getCell(7).getNumericCellValue();
		        studentData.setPassingYear(yearofPassing.toString());		        
		 }
		        studentData.setOriginalDOCuploadfilePath(filePath);
		        studentDataReviewList.add(studentData);    
			 }
	
		 return studentDataReviewList;
	}

	@Override
	public List<UniversityStudDocResponse> getStudentData(UniversityStudentRequest universityStudData ) {
		
		logger.info("********AssociateManagerServiceImpl getStudentData********");

		 List<UniversityStudentDocument> studentDataList = new ArrayList<UniversityStudentDocument>();
		 
		List<UniversityStudentDocument> usdr = universityStudentDocRepository.searchByFirstNameLikeAndLastNameLikeAndEnrollmentNoLikeAndCollegeIdLikeAndPassingYearIdLikeAndStreamIdLikeAndSemIdLikeAndBranchIdLike(universityStudData.getFirstName(), 
				universityStudData.getLastName(), universityStudData.getEnrollmentNo(), universityStudData.getCollegeId(), 
				universityStudData.getPassingYearId(), universityStudData.getStreamId(),universityStudData.getSemId(),universityStudData.getBranchId());
		List<UniversityStudDocResponse> studData=new ArrayList<>();
		
		logger.info("********AssociateManagerServiceImpl getStudentData********");
		 
		 for(UniversityStudentDocument studentData:usdr) {
			 
			 UniversityStudDocResponse resp = new UniversityStudDocResponse();
			 
			 Optional<CollegeMaster> cm = collegeRespository.findById(studentData.getCollegeId());
			 CollegeMaster college = cm.get();
			 
			 Optional<StreamMaster> streaminfo = streamRespository.findById(studentData.getStreamId());
			 StreamMaster stream = streaminfo.get();
			 
			 Optional<PassingYearMaster> passingyrinfo = yearOfPassingRespository.findById(studentData.getPassingYearId());
			 PassingYearMaster passingyr = passingyrinfo.get();
			 
			 SemesterEntity sem=semesterService.getSemById(studentData.getSemId());
				
			 BranchMasterEntity branch=branchMasterService.getbranchById(studentData.getBranchId());
				
			 
			 resp.setId(studentData.getId());
			 resp.setFirstName(studentData.getFirstName());
			 resp.setLastName(studentData.getLastName());
			 resp.setCollegeName(college.getCollegeName());
			 resp.setEnrollmentNo(studentData.getEnrollmentNo());
			 resp.setStream(stream.getStreamName());
			 resp.setPassingYear(passingyr.getYearOfPassing());
			 resp.setBranch_nm(branch.getBranchName());
			 resp.setSemester(sem.getSemester());
			 //String Path=
			 resp.setOriginalDOCuploadfilePath("/verifier/getimage/U/"+studentData.getId());
			 studData.add(resp);
			 
		 }
		
//		 String query="";
//		 query="Select data from UniversityStudentDocument data where ";
//		 
//		 if(universityStudData!=null)
//		 {
//			 
//			 String Name=universityStudData.getFirstName();
//			 String lastName=universityStudData.getLastName();
//			 Long Stream=universityStudData.getStreamId();
//			 Long clgName=universityStudData.getCollegeId();
//			 String enrollNo=universityStudData.getEnrollmentNo();
//			 Long YearOfPassing=universityStudData.getPassingYearId(); 
//			 
//			 if(!Name.isEmpty())
//			 {
//				 query=query+" firstName='"+universityStudData.getFirstName()+"'";
//				 if(!lastName.isEmpty()||Stream!=null||clgName!=null||!enrollNo.isEmpty()||YearOfPassing!=null)
//				 {
//				 query=query+" and";
//				 }
//			 }
//				 
//			 if(!universityStudData.getLastName().isEmpty())
//			 {
//				 query=query+" lastName='"+universityStudData.getLastName()+"'";
//				 if(Stream!=null||clgName!=null||!enrollNo.isEmpty()||YearOfPassing!=null)				 {
//					
//				 query=query+" and";
//				 }
//			 }
//			 if(universityStudData.getStreamId()!=null)
//			 {
//				 query=query+"  streamId='"+universityStudData.getStreamId()+"'";
//				 if(clgName!=null||!enrollNo.isEmpty()||YearOfPassing!=null) {
//				  query=query+" and";
//				 }
//			 }
//			 if(universityStudData.getCollegeId()!=null)
//			 {
//				 query=query+"  collegeId='"+universityStudData.getCollegeId()+"'";
//				 if(!enrollNo.isEmpty()||YearOfPassing!=null)				 {
//				 query=query+" and";
//				 }
//			 }
//			 if(!universityStudData.getEnrollmentNo().isEmpty())
//			 {
//				 query=query+"  enrollmentNo='"+universityStudData.getEnrollmentNo()+"'";
//				 if(universityStudData.getPassingYearId()!=null)
//				 {
//				 query=query+" and";
//				 }
//			 }
//			 if(universityStudData.getPassingYearId()!=null)
//			 {
//				 query=query+" passingYearId="+universityStudData.getPassingYearId()+"";
//			 }
//		 }
//		 
//		 logger.info(query);
//		 
//		 EntityManager em = emf.createEntityManager();
//		 studentDataList= em.createQuery(query).getResultList();
		// studentDataList=universityStudentDocRepository.getStudData(query);
//		 List<UniversityStudDocResponse> studData=new ArrayList<>();
//		 for(UniversityStudentDocument setStudentdata:studentDataList)
//		 {
//				Optional<CollegeMaster> college= collegeRespository.findById(setStudentdata.getCollegeId());
//				CollegeMaster collegeEntities=college.get();
//				Optional<StreamMaster> streaminfo =streamRespository.findById(setStudentdata.getStreamId());
//				StreamMaster stream=streaminfo.get();
//				Optional<PassingYearMaster> passingyrinfo=yearOfPassingRespository.findById(setStudentdata.getPassingYearId());
//				PassingYearMaster passingyr=passingyrinfo.get();
//			 UniversityStudDocResponse studDataResponse=new UniversityStudDocResponse();
//			 
//			 studDataResponse.setId(setStudentdata.getId());
//			 studDataResponse.setFirstName(setStudentdata.getFirstName());
//			 studDataResponse.setLastName(setStudentdata.getLastName());
//			 studDataResponse.setCollegeName(collegeEntities.getCollegeName());
//			 studDataResponse.setEnrollmentNo(setStudentdata.getEnrollmentNo());
//			 studDataResponse.setStream(stream.getStreamName());
//			 studDataResponse.setPassingYear(passingyr.getYearOfPassing());
//			 //String Path=
//			 studDataResponse.setOriginalDOCuploadfilePath("/verifier/getimage/U/"+setStudentdata.getId());
//			 studData.add(studDataResponse);
//		 }
//		 logger.info("Data"+studData);
		return studData;
	}

}
