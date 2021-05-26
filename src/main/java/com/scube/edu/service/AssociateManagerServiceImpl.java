package com.scube.edu.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.PathVariable;
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
		SemesterService semesterService;
		
		@Autowired
		BranchMasterService branchMasterService;
		
	
	private XSSFWorkbook workbook;
	
	
	@Override
	public  HashMap<String,List<UniversityStudDocResponse>> saveStudentInfo(List<UniversityStudDocResponse> list, Long userid) throws IOException {
		
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
				
				
				if(collegeEntities!=null && stream!=null && passingyr!=null&&sem!=null &&branch!=null &&!Data.getFirstName().equals("")&&!Data.getLastName().equals("")&&!Data.getEnrollmentNo().equals("")) {
					
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
		        studentData.setCreatedate(new Date());
		        studentData.setCreateby(userid.toString());
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
						 if(collegeEntities==null || stream==null || passingyr==null|| sem==null || branch==null) {
						 reason="Invalid";
						 }
						 if(collegeEntities==null)
						 {
							 reason=reason+" College Name";
							 if(stream==null||passingyr==null||sem==null||branch==null)
							 {
								 reason=reason+",";

							 }
						 }
						  if(stream==null)
				   		 {
							 reason=reason+" Stream";
							 if(passingyr==null||sem==null||branch==null)
							 {
								 reason=reason+",";

							 }
						 }
						  if(passingyr==null)
						 {
							 reason=reason+" Year of Passing";
							 if(sem==null||branch==null)
							 {
								 reason=reason+",";

							 }

						 }
						  if(sem==null)
						 {
							 reason=reason+" Semester";
							 if(branch==null)
							 {
								 reason=reason+",";

							 }

						 }
						  if(branch==null)
						 {
							 reason=reason+" Branch";
						 }
						
						 if(Data.getFirstName()==null || Data.getFirstName().equals("")||(Data.getLastName()==null || Data.getLastName().equals(""))||(Data.getEnrollmentNo()==null || Data.getEnrollmentNo().equals("") ))
						 {
							 if(reason!=null) {
								 reason=reason+" & Blank";

							 }
							 else {
							 reason="Blank";
							 }
						 }
						 if(Data.getFirstName()==null || Data.getFirstName().equals(""))
								 {
							 reason=reason+" First name";
						if (Data.getLastName() == null || Data.getLastName().equals("") || Data.getLastName() == null
								|| Data.getLastName().equals("") || Data.getEnrollmentNo() == null
								|| Data.getEnrollmentNo().equals("")) {
								 reason=reason+",";
							 }
								 }
						 if(Data.getLastName()==null || Data.getLastName().equals("")  )
						 {
							 reason=reason+" Last name";
							 if(Data.getEnrollmentNo()==null || Data.getEnrollmentNo().equals("")) {
								 reason=reason+",";
								 }
						 }
						 if(Data.getEnrollmentNo()==null || Data.getEnrollmentNo().equals("") )
						 {
							 reason=reason+" Seat No";
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
		int rowcnt=worksheet.getLastRowNum();
		
		int clomncnt=worksheet.getRow(0).getLastCellNum() ;
		
		int noOfColumns = worksheet.getRow(0).getPhysicalNumberOfCells();

		logger.info("noOfColumns="+noOfColumns);

		logger.info("clomncnt="+clomncnt);
		 String fileSubPath = "file/";
		 String flag = "2";
		 String filePath = fileStorageService.storeFileOnAws(datafile , fileSubPath, flag);
		 
		 if(clomncnt==8) {
		 for(int i=1;i<=rowcnt;i++) {
			 
			 XSSFRow row = worksheet.getRow(i);		 
			

			 UniversityStudDocResponse studentData = new UniversityStudDocResponse();	            
		     if(row!=null) {
		    	 int rowcell= row.getPhysicalNumberOfCells();
					logger.info("rowcell="+rowcell);
					
			 if(row.getCell(0)!=null)
			 {
		        studentData.setFirstName(row.getCell(0).getStringCellValue());
			 }
			 else
			 {
			        studentData.setFirstName("");

			 }
			 if(row.getCell(1)!=null){
		        studentData.setLastName(row.getCell(1).getStringCellValue());
			 }
			 else
			 {
			        studentData.setLastName("");

			 }
			 if(row.getCell(2)!=null){
		        studentData.setCollegeName(row.getCell(2).getStringCellValue());	
			 }
			 else
			 {
				 studentData.setCollegeName("");
			 }
			 if(row.getCell(3)!=null){
		    	   studentData.setStream(row.getCell(3).getStringCellValue());	
		       }
		       else {
		    	   studentData.setStream("");
		       }
			 if(row.getCell(4)!=null){

		        studentData.setBranch_nm(row.getCell(4).getStringCellValue());
			 }
			 else {
				 studentData.setBranch_nm("");
			 }
			 if(row.getCell(5)!=null){

		        studentData.setSemester(row.getCell(5).getStringCellValue());	
			 }
			 else {
				 studentData.setSemester(""); 
			 }

			 if(row.getCell(6)!=null){
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
			 }
			 else {
				 studentData.setEnrollmentNo("");
			 }
			 
		 if(row.getCell(7)!=null){
	        int yearcellType=row.getCell(7).getCellType();

		 if(yearcellType==1)
		 {
		        studentData.setPassingYear(row.getCell(7).getStringCellValue());
		 }
		 else {
		        Integer yearofPassing=(int) row.getCell(7).getNumericCellValue();
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
