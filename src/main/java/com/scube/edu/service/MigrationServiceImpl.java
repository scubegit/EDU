package com.scube.edu.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.scube.edu.model.DocumentMaster;
import com.scube.edu.model.MigrationRequestEntity;
import com.scube.edu.model.PriceMaster;
import com.scube.edu.repository.MigrationRequestRepository;
import com.scube.edu.request.StudentDocVerificationRequest;
import com.scube.edu.request.StudentMigrationRequest;
import com.scube.edu.response.DocumentResponse;
import com.scube.edu.response.RequestTypeResponse;
import com.scube.edu.util.FileStorageService;

@Service
public class MigrationServiceImpl implements MigrationService {
	
	private static final Logger logger = LoggerFactory.getLogger(MigrationServiceImpl.class);
	
	@Autowired
	MigrationRequestRepository migrationRepo;
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	DocumentService documentService;
	
	@Autowired
	PriceService priceService;
	
	@Autowired
	RequestTypeService requestTypeService;
	
	@Autowired
	private FileStorageService fileStorageService;

	@Override
	public boolean saveMigrationRequest(StudentMigrationRequest stuMigReq) throws Exception{
		
		logger.info("***MigrationServiceImpl saveMigrationRequest***");
		
		MigrationRequestEntity mig = new MigrationRequestEntity();
		
//		Calculation of migration amount is left
//		need to save mig_amt_with and without GST in MigrationRequestEntity
		
//		mig.setBranchId(stuMigReq.getBranchId());
		mig.setClassGrade(stuMigReq.getClassGrade());
		mig.setCreateby(stuMigReq.getUserid());
		mig.setDateOfBirth(stuMigReq.getDateOfBirth());
		mig.setExamCode(stuMigReq.getExamCode());
		mig.setExamFaculty(stuMigReq.getExamFaculty());
		mig.setExamHeldDate(stuMigReq.getExamHeldDate());
		mig.setExamName(stuMigReq.getExamName());
		mig.setFullName(stuMigReq.getFullName());
		mig.setGender(stuMigReq.getGender());
		mig.setIsdeleted("N");
		mig.setLastCollegeName(stuMigReq.getLastCollegeName());
		mig.setLeavingYear(stuMigReq.getLeavingYear());
		mig.setMobileNumber(stuMigReq.getMobileNumber());
		mig.setMonthOfPassing(stuMigReq.getMonthOfPassing());
		mig.setMotherName(stuMigReq.getMotherName());
		mig.setNationality(stuMigReq.getNationality());
		mig.setPermAddr(stuMigReq.getPermAddr());
		mig.setPermCountry(stuMigReq.getPermCountry());
		mig.setPermDistrict(stuMigReq.getPermDistrict());
		mig.setPermPincode(stuMigReq.getPermPincode());
		mig.setPermState(stuMigReq.getPermState());
		mig.setPermTaluka(stuMigReq.getPermTaluka());
		mig.setPostalAddr(stuMigReq.getPostalAddr());
		mig.setPostalCountry(stuMigReq.getPostalCountry());
		mig.setPostalDistrict(stuMigReq.getPostalDistrict());
		mig.setPostalPincode(stuMigReq.getPostalPincode());
		mig.setPostalState(stuMigReq.getPostalState());
		mig.setPostalTaluka(stuMigReq.getPostalTaluka());
		mig.setPrn(stuMigReq.getPrn());
		mig.setProfessionalCourse(stuMigReq.getProfessionalCourse());
		mig.setReason(stuMigReq.getReason());
		mig.setSeatNumber(stuMigReq.getSeatNumber());
		mig.setSemesterId(stuMigReq.getSemesterId());
		mig.setSpouceName(stuMigReq.getSpouceName());
		mig.setStreamId(stuMigReq.getStreamId());
		mig.setTcDate(stuMigReq.getTcDate());
		mig.setTcNumber(stuMigReq.getTcNumber());
		mig.setYearOfPassingId(stuMigReq.getYearOfPassingId());
		mig.setDocFilePath(stuMigReq.getDocFilePath());
		mig.setTcFilePath(stuMigReq.getTcFilePath());
		
//		make studentDocVerificationRequest
		StudentDocVerificationRequest verReq = new StudentDocVerificationRequest();
		
		verReq.setFirstname(stuMigReq.getFullName());
		verReq.setLastname("");
//		verReq.setBranchId(Long.parseLong(stuMigReq.getExamName()));
//		verReq.setBranchId((long)1);
		verReq.setCollegenameid(Long.parseLong(stuMigReq.getLastCollegeName()));
		verReq.setCreateby(stuMigReq.getUserid());
		verReq.setEnrollno(stuMigReq.getSeatNumber()); // ask
		verReq.setFilepath(stuMigReq.getDocFilePath());
		verReq.setLastname("");
		verReq.setMonthOfPassing(stuMigReq.getMonthOfPassing());
		verReq.setSemId((long)0);
		verReq.setStreamid(Long.parseLong(stuMigReq.getExamFaculty()));
		verReq.setUploaddocpath(stuMigReq.getDocFilePath());
		verReq.setUserid(Long.parseLong(stuMigReq.getUserid()));
		verReq.setVerreqid((long) 1);
		verReq.setYearofpassid(Long.parseLong(stuMigReq.getYearOfPassingId()));
		
		verReq.setUniid((long) 1);
		
		RequestTypeResponse reqType = requestTypeService.getIdByName("Verification");
		verReq.setRequesttype(reqType.getId());
		DocumentResponse doc = documentService.getDocumentEntityByName("Marksheet");
		verReq.setDocname(String.valueOf(doc.getId())); 
		
		RequestTypeResponse reqTypeMig = requestTypeService.getIdByName("Migration");
		
//		Might need to search price by request type as well as doc_id 
		PriceMaster price = priceService.getPriceByRequestTypeId(reqTypeMig.getId());
		mig.setMigSecurCharge(String.valueOf(price.getSecurCharge()));
		mig.setMigUniAmount(String.valueOf(price.getAmount()));
		
		long total = price.getTotalAmt();
		long totalWithGST =    ((price.getSecurCharge() * price.getGst()) / 100) + price.getTotalAmt();
		mig.setMigAmt(String.valueOf(total));
		mig.setMigAmtWithGst(String.valueOf(totalWithGST));
		
		HashMap<String, Long> verAmounts = studentService.saveStudentSingleVerificationDoc(verReq);
		
		mig.setVerDocAmt(String.valueOf(verAmounts.get("total_without_gst")));
		mig.setVerDocAmtWithGst(String.valueOf(verAmounts.get("total_with_gst")));
		mig.setVerReqAppId(String.valueOf(verAmounts.get("application_id")));
		mig.setMigVerTotal(stuMigReq.getTotalAmt());
		mig.setMigVerTotalWithGst(stuMigReq.getTotalAmtWithGst());
		
		migrationRepo.save(mig);
		
		return true;
	}
	
	@Value("${file.awsORtest}")
    private String awsORtest;

	@Override
	public String saveMigrationDocument(MultipartFile file) {
		
		logger.info("***MigrationServiceimpl saveMigrationDocument***"+ file.getName());
		
		String fileSubPath = "file/";
		String flag = "3";
		String filePath;
		
		if(awsORtest.equalsIgnoreCase("TEST") || awsORtest.equalsIgnoreCase("LOCAL")) {
			filePath = fileStorageService.storeFile(file , fileSubPath, flag);
		}else {
			filePath = fileStorageService.storeFileOnAws(file, flag);
		}
		System.out.println("****FilePath--->"+ filePath);
		return filePath;
	}

	@Override
	public HashMap<String, Long> calculateMigrationAmount(StudentMigrationRequest stuMigReq) throws Exception {
		
		logger.info("***MigrationServiceImpl calculateMigrationAmount***");
		
		long migTotal = 0;
		long migTotalWithGST = 0;
		
		HashMap<String, Long> map = new HashMap<String, Long>();
		
		RequestTypeResponse reqTypeMig = requestTypeService.getIdByName("Migration");
		PriceMaster diff =  priceService.getPriceByRequestTypeId(reqTypeMig.getId());
		
		if (diff!=null)	{		
			migTotal = diff.getTotalAmt();
			migTotalWithGST =    ((diff.getSecurCharge() * diff.getGst()) / 100) + diff.getTotalAmt();
			
			logger.info(migTotal+ " and  "+ migTotalWithGST);	
			}
		
		List<StudentDocVerificationRequest> listReqs = new ArrayList<>();
		StudentDocVerificationRequest verReq = new StudentDocVerificationRequest();
		
		verReq.setFirstname(stuMigReq.getFullName());
		verReq.setLastname("");
//		verReq.setBranchId(Long.parseLong(stuMigReq.getExamName()));
//		verReq.setBranchId((long)1);
		verReq.setCollegenameid(Long.parseLong(stuMigReq.getLastCollegeName()));
		verReq.setCreateby(stuMigReq.getUserid());
		verReq.setEnrollno(stuMigReq.getSeatNumber()); // ask
		verReq.setFilepath(stuMigReq.getDocFilePath());
		verReq.setLastname("");
		verReq.setMonthOfPassing(stuMigReq.getMonthOfPassing());
		verReq.setSemId((long)0);
		verReq.setStreamid(Long.parseLong(stuMigReq.getExamFaculty()));
		verReq.setUploaddocpath(stuMigReq.getDocFilePath());
		verReq.setUserid(Long.parseLong(stuMigReq.getUserid()));
		verReq.setVerreqid((long) 1);
		verReq.setYearofpassid(Long.parseLong(stuMigReq.getYearOfPassingId()));
		
		verReq.setUniid((long) 1);
		
		RequestTypeResponse reqType = requestTypeService.getIdByName("Verification");
		verReq.setRequesttype(reqType.getId());
		DocumentResponse doc = documentService.getDocumentEntityByName("Marksheet");
		verReq.setDocname(String.valueOf(doc.getId())); 
		
		listReqs.add(verReq);
		
		HashMap<String,List< Long>> verAmounts = studentService.CalculateDocAmount(listReqs);
		long verAmtWithGst = verAmounts.get("total_with_gst").get(0);
		long verAmtWithoutGst = verAmounts.get("total_without_gst").get(0);
		
		System.out.println("---------------"+ verAmounts.get("total_without_gst"));
		
		long totalWithGst = migTotalWithGST + verAmtWithGst;
		long totalWithoutGst = migTotal + verAmtWithoutGst;
		
		map.put("total",totalWithoutGst);
		map.put("total_with_gst", totalWithGst);
		
		return map;
	}

}
