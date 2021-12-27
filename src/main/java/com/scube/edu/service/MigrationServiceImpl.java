package com.scube.edu.service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scube.edu.model.CollegeMaster;
import com.scube.edu.model.DocumentMaster;
import com.scube.edu.model.MigrationRequestEntity;
import com.scube.edu.model.PriceMaster;
import com.scube.edu.model.VerificationRequest;
import com.scube.edu.repository.MigrationRequestRepository;
import com.scube.edu.request.MigrationStatusChangeRequest;
import com.scube.edu.request.StatusChangeRequest;
import com.scube.edu.request.StudentDocVerificationRequest;
import com.scube.edu.request.StudentMigrationRequest;
import com.scube.edu.response.CollegeResponse;
import com.scube.edu.response.DocumentResponse;
import com.scube.edu.response.MigrationRequestResponse;
import com.scube.edu.response.MigrationVerificationResponse;
import com.scube.edu.response.RequestTypeResponse;
import com.scube.edu.util.FileStorageService;

@Service
public class MigrationServiceImpl implements MigrationService {
	
	private static final Logger logger = LoggerFactory.getLogger(MigrationServiceImpl.class);
	
	Base64.Encoder baseEncoder = Base64.getEncoder();
	
	@Autowired
	MigrationRequestRepository migrationRepo;
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	VerificationService verificationService;
	
	@Autowired
	DocumentService documentService;
	
	@Autowired
	PriceService priceService;
	
	@Autowired
	CollegeSevice collegeService;
	
	@Autowired
	RequestTypeService requestTypeService;
	
	@Autowired
	private FileStorageService fileStorageService;
	
	@Autowired
	EmailService emailService;
	
	 @Value("${file.url-dir}")
     private String url;

	@Override
	public boolean saveMigrationRequest(StudentMigrationRequest stuMigReq) throws Exception{
		
		logger.info("***MigrationServiceImpl saveMigrationRequest***");
		
		if(stuMigReq.getId() == null) {
			MigrationRequestEntity mig = new MigrationRequestEntity();
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
			mig.setMigReqStatus("Requested");
			
			HashMap<String, Long> verAmounts = studentService.saveStudentSingleVerificationDoc(verReq);
			
			mig.setVerDocAmt(String.valueOf(verAmounts.get("total_without_gst")));
			mig.setVerDocAmtWithGst(String.valueOf(verAmounts.get("total_with_gst")));
			mig.setVerReqAppId(String.valueOf(verAmounts.get("application_id")));
			mig.setMigVerTotal(stuMigReq.getTotalAmt());
			mig.setMigVerTotalWithGst(stuMigReq.getTotalAmtWithGst());
			
			migrationRepo.save(mig);
			logger.info("mig.getId()"+ mig.getId());
			
	//		Send migration confirmation email
			CollegeResponse col = collegeService.getNameById(Long.valueOf(mig.getLastCollegeName()));
			String encodedId = baseEncoder.encodeToString(String.valueOf(mig.getId()).getBytes(StandardCharsets.UTF_8)) ;
			emailService.sendMigrationConfirmMail(encodedId, col.getCollegeEmail() , url);
		}else {
			Optional<MigrationRequestEntity> migEntt = migrationRepo.findById(Long.valueOf(stuMigReq.getId()));
			MigrationRequestEntity migEnt = migEntt.get();
			
//			VerificationRequest verReq = verificationService.fetchVerificationRequestByApplicationId(Long.valueOf(stuMigReq.getId()));
			
			migEnt.setBranchId(stuMigReq.getBranchId());
			migEnt.setClassGrade(stuMigReq.getClassGrade());
			migEnt.setCreateby(stuMigReq.getUserid());
			migEnt.setDateOfBirth(stuMigReq.getDateOfBirth());
			migEnt.setExamCode(stuMigReq.getExamCode());
			migEnt.setExamFaculty(stuMigReq.getExamFaculty());
			migEnt.setExamHeldDate(stuMigReq.getExamHeldDate());
			migEnt.setExamName(stuMigReq.getExamName());
			migEnt.setFullName(stuMigReq.getFullName());
			migEnt.setGender(stuMigReq.getGender());
			migEnt.setIsdeleted("N");
			migEnt.setLastCollegeName(stuMigReq.getLastCollegeName());
			migEnt.setLeavingYear(stuMigReq.getLeavingYear());
			migEnt.setMobileNumber(stuMigReq.getMobileNumber());
			migEnt.setMonthOfPassing(stuMigReq.getMonthOfPassing());
			migEnt.setMotherName(stuMigReq.getMotherName());
			migEnt.setNationality(stuMigReq.getNationality());
			migEnt.setPermAddr(stuMigReq.getPermAddr());
			migEnt.setPermCountry(stuMigReq.getPermCountry());
			migEnt.setPermDistrict(stuMigReq.getPermDistrict());
			migEnt.setPermPincode(stuMigReq.getPermPincode());
			migEnt.setPermState(stuMigReq.getPermState());
			migEnt.setPermTaluka(stuMigReq.getPermTaluka());
			migEnt.setPostalAddr(stuMigReq.getPostalAddr());
			migEnt.setPostalCountry(stuMigReq.getPostalCountry());
			migEnt.setPostalDistrict(stuMigReq.getPostalDistrict());
			migEnt.setPostalPincode(stuMigReq.getPostalPincode());
			migEnt.setPostalState(stuMigReq.getPostalState());
			migEnt.setPostalTaluka(stuMigReq.getPostalTaluka());
			migEnt.setPrn(stuMigReq.getPrn());
			migEnt.setProfessionalCourse(stuMigReq.getProfessionalCourse());
			migEnt.setReason(stuMigReq.getReason());
			migEnt.setSeatNumber(stuMigReq.getSeatNumber());
			migEnt.setSemesterId(stuMigReq.getSemesterId());
			migEnt.setSpouceName(stuMigReq.getSpouceName());
			migEnt.setStreamId(stuMigReq.getStreamId());
			migEnt.setTcDate(stuMigReq.getTcDate());
			migEnt.setTcNumber(stuMigReq.getTcNumber());
			migEnt.setYearOfPassingId(stuMigReq.getYearOfPassingId());
			migEnt.setDocFilePath(stuMigReq.getDocFilePath());
			migEnt.setTcFilePath(stuMigReq.getTcFilePath());
			
			migrationRepo.save(migEnt);
			
		}
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

	@Override
	public boolean setStatusForMigrationRequest(MigrationStatusChangeRequest migStatusChangeRequest) {
		
		logger.info("***MigrationServiceImpl setStatusForMigrationRequest***"+ migStatusChangeRequest.getId());
		
		Base64.Decoder decoder = Base64.getDecoder();  
		String idd = new String(decoder.decode(String.valueOf(migStatusChangeRequest.getId()))); 
		
		Optional<MigrationRequestEntity> migReqEntt = migrationRepo.findById(Long.valueOf(idd));
		MigrationRequestEntity migReqEnt = migReqEntt.get();
		
		migReqEnt.setMigReqStatus(migStatusChangeRequest.getStatus());
		
		if(migStatusChangeRequest.getRemark() != null) {
			migReqEnt.setRejectReason(migStatusChangeRequest.getRemark());
//			migReqEnt.setReason(migStatusChangeRequest.getRemark());
		}
		
		migrationRepo.save(migReqEnt);
		
		return true;
	}

	@Override
	public MigrationRequestResponse getMigrationRequestByUserid(String userid) {
		
		logger.info("***MigrationServiceImpl getMogrationRequestByUserid***"+String.valueOf(userid));
		
		MigrationRequestEntity migReqEnt = migrationRepo.findByCreateby(userid);
		
		MigrationRequestResponse resp = new MigrationRequestResponse();
		
		resp.setBranchId(migReqEnt.getBranchId());
		resp.setClassGrade(migReqEnt.getClassGrade());
		resp.setDateOfBirth(migReqEnt.getDateOfBirth());
		resp.setDocFilePath(migReqEnt.getDocFilePath());
		resp.setExamCode(migReqEnt.getExamCode());
		resp.setExamFaculty(migReqEnt.getExamFaculty());
		resp.setExamHeldDate(migReqEnt.getExamHeldDate());
		resp.setExamName(migReqEnt.getExamName());
		resp.setFullName(migReqEnt.getFullName());
		resp.setGender(migReqEnt.getGender());
		resp.setId(migReqEnt.getId());
		resp.setLastCollegeName(migReqEnt.getLastCollegeName());
		resp.setLeavingYear(migReqEnt.getLeavingYear());
		resp.setMigAmt(migReqEnt.getMigAmt());
		resp.setMigAmtWithGst(migReqEnt.getMigAmtWithGst());
		resp.setMigReqStatus(migReqEnt.getMigReqStatus());
		resp.setMigSecurCharge(migReqEnt.getMigSecurCharge());
		resp.setMigUniAmount(migReqEnt.getMigUniAmount());
		resp.setMigVerTotal(migReqEnt.getMigVerTotal());
		resp.setMigVerTotalWithGst(migReqEnt.getMigVerTotalWithGst());
		resp.setMobileNumber(migReqEnt.getMobileNumber());
		resp.setMonthOfPassing(migReqEnt.getMonthOfPassing());
		resp.setMotherName(migReqEnt.getMotherName());
		resp.setNationality(migReqEnt.getNationality());
		resp.setPermAddr(migReqEnt.getPermAddr());
		resp.setPermCountry(migReqEnt.getPermCountry());
		resp.setPermDistrict(migReqEnt.getPermDistrict());
		resp.setPermPincode(migReqEnt.getPermPincode());
		resp.setPermState(migReqEnt.getPermState());
		resp.setPermTaluka(migReqEnt.getPermTaluka());
		resp.setPostalAddr(migReqEnt.getPostalAddr());
		resp.setPostalCountry(migReqEnt.getPostalCountry());
		resp.setPostalDistrict(migReqEnt.getPostalDistrict());
		resp.setPostalPincode(migReqEnt.getPostalPincode());
		resp.setPostalState(migReqEnt.getPostalState());
		resp.setPostalTaluka(migReqEnt.getPostalTaluka());
		resp.setPrn(migReqEnt.getPrn());
		resp.setProfessionalCourse(migReqEnt.getProfessionalCourse());
		resp.setReason(migReqEnt.getReason());
		resp.setSeatNumber(migReqEnt.getSeatNumber());
		resp.setSemesterId(migReqEnt.getSemesterId());
		resp.setSpouceName(migReqEnt.getSpouceName());
		resp.setStreamId(migReqEnt.getStreamId());
		resp.setTcDate(migReqEnt.getTcDate());
		resp.setTcFilePath(migReqEnt.getTcFilePath());
		resp.setTcNumber(migReqEnt.getTcNumber());
		resp.setVerDocAmt(migReqEnt.getVerDocAmt());
		resp.setVerDocAmtWithGst(migReqEnt.getVerDocAmtWithGst());
		resp.setVerReqAppId(migReqEnt.getVerReqAppId());
		resp.setYearOfPassingId(migReqEnt.getYearOfPassingId());
		resp.setRejectReason(migReqEnt.getRejectReason());
		
		return resp;
	}
	public List<MigrationVerificationResponse> getAllMigrationRequests() {
		
		logger.info("***MigrationServiceImpl getAllMigrationRequests***");
		
		List<Map<String, String>> migList = migrationRepo.getMigrationRequests();
		
		List<MigrationVerificationResponse> list = new ArrayList<>(); 
		
		final ObjectMapper mapper = new ObjectMapper(); // jackson's object mapper
		
		for(int i = 0; i < migList.size(); i++) {
			final MigrationVerificationResponse pojo = mapper.convertValue(migList.get(i), MigrationVerificationResponse.class);
			
			logger.info(String.valueOf(i) + "----->" + pojo.toString());
			list.add(pojo);
		}
		
		return list;
	}

	@Override
	public boolean resendCollegeMail(String migId, String collegeId) throws MessagingException, Exception {
		
		logger.info("***MigrationServiceImpl resendcollegeMail***"+ migId +"   "+collegeId);
		
		CollegeResponse col = collegeService.getNameById(Long.valueOf(collegeId));
		String encodedId = baseEncoder.encodeToString(String.valueOf(migId).getBytes(StandardCharsets.UTF_8)) ;
		emailService.sendMigrationConfirmMail(encodedId, col.getCollegeEmail() , url);
		
		return true;
	}
	
	@Override
	public boolean updateMigrationEntityStatus(MigrationStatusChangeRequest migStatusChangeRequest) {
		
		logger.info("***MigrationServiceImpl getMigrationReqEnt***"+ migStatusChangeRequest.getId());
		
		Optional<MigrationRequestEntity> entt = migrationRepo.findById(Long.valueOf(migStatusChangeRequest.getId()));
		MigrationRequestEntity ent = entt.get();
		
		ent.setMigReqStatus(migStatusChangeRequest.getStatus());
		migrationRepo.save(ent);
		
		return true;
		
	}

	@Override
	public MigrationRequestResponse getMigrationRequestByPrimarykey(String id) {
		
		logger.info("***MigrationServiceImpl getMigrationRequestByPrimarykey***"+ id);
		
		Optional<MigrationRequestEntity> entt = migrationRepo.findById(Long.valueOf(id));
		MigrationRequestEntity ent = entt.get();
		
		MigrationRequestResponse resp = new MigrationRequestResponse();
		
		resp.setBranchId(ent.getBranchId());
		resp.setClassGrade(ent.getClassGrade());
		resp.setDateOfBirth(ent.getDateOfBirth());
		resp.setDocFilePath(ent.getDocFilePath());
		resp.setExamCode(ent.getExamCode());
		resp.setExamFaculty(ent.getExamFaculty());
		resp.setExamHeldDate(ent.getExamHeldDate());
		resp.setExamName(ent.getExamName());
		resp.setFullName(ent.getFullName());
		resp.setGender(ent.getGender());
		resp.setId(ent.getId());
		resp.setLastCollegeName(ent.getLastCollegeName());
		resp.setLeavingYear(ent.getLeavingYear());
		resp.setMigAmt(ent.getMigAmt());
		resp.setMigAmtWithGst(ent.getMigAmtWithGst());
		resp.setMigReqStatus(ent.getMigReqStatus());
		resp.setMigSecurCharge(ent.getMigSecurCharge());
		resp.setMigUniAmount(ent.getMigUniAmount());
		resp.setMigVerTotal(ent.getMigVerTotal());
		resp.setMigVerTotalWithGst(ent.getMigVerTotalWithGst());
		resp.setMobileNumber(ent.getMobileNumber());
		resp.setMonthOfPassing(ent.getMonthOfPassing());
		resp.setMotherName(ent.getMotherName());
		resp.setNationality(ent.getNationality());
		resp.setPermAddr(ent.getPermAddr());
		resp.setPermCountry(ent.getPermCountry());
		resp.setPermDistrict(ent.getPermDistrict());
		resp.setPermPincode(ent.getPermPincode());
		resp.setPermState(ent.getPermState());
		resp.setPermTaluka(ent.getPermTaluka());
		resp.setPostalAddr(ent.getPostalAddr());
		resp.setPostalCountry(ent.getPostalCountry());
		resp.setPostalDistrict(ent.getPostalDistrict());
		resp.setPostalPincode(ent.getPostalPincode());
		resp.setPostalState(ent.getPostalState());
		resp.setPostalTaluka(ent.getPostalTaluka());
		resp.setPrn(ent.getPrn());
		resp.setProfessionalCourse(ent.getProfessionalCourse());
		resp.setReason(ent.getReason());
		resp.setSeatNumber(ent.getSeatNumber());
		resp.setSemesterId(ent.getSemesterId());
		resp.setSpouceName(ent.getSpouceName());
		resp.setStreamId(ent.getStreamId());
		resp.setTcDate(ent.getTcDate());
		resp.setTcFilePath(ent.getTcFilePath());
		resp.setTcNumber(ent.getTcNumber());
		resp.setVerDocAmt(ent.getVerDocAmt());
		resp.setVerDocAmtWithGst(ent.getVerDocAmtWithGst());
		resp.setVerReqAppId(ent.getVerReqAppId());
		resp.setYearOfPassingId(ent.getYearOfPassingId());
		resp.setRejectReason(ent.getRejectReason());
		
		VerificationRequest verReq = verificationService.fetchVerificationRequestByApplicationId(Long.valueOf(ent.getVerReqAppId()));
		
		resp.setVerDocStatus(verReq.getDocStatus());
		
		
		return resp;
	}

}
