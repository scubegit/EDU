package com.scube.edu.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scube.edu.model.BranchMasterEntity;
import com.scube.edu.model.DocumentMaster;
import com.scube.edu.model.PassingYearMaster;
import com.scube.edu.model.PriceMaster;
import com.scube.edu.model.SemesterEntity;
import com.scube.edu.model.StreamMaster;
import com.scube.edu.model.UserMasterEntity;
import com.scube.edu.model.VerificationRequest;
import com.scube.edu.repository.DocumentRepository;
import com.scube.edu.repository.PriceMasterRepository;
import com.scube.edu.repository.UserRepository;
import com.scube.edu.repository.VerificationRequestRepository;
import com.scube.edu.repository.YearOfPassingRepository;
import com.scube.edu.request.LoginRequest;
import com.scube.edu.request.StudentDocVerificationRequest;
import com.scube.edu.request.UserAddRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.JwtResponse;
import com.scube.edu.response.StudentVerificationDocsResponse;
import com.scube.edu.response.VerificationResponse;
import com.scube.edu.security.JwtUtils;
import com.scube.edu.util.StringsUtils;


@Service
public class VerificationServiceImpl implements VerificationService{
	
	private static final Logger logger = LoggerFactory.getLogger(VerificationServiceImpl.class);
	
	@Autowired
	VerificationRequestRepository verificationReqRepo;
	
	@Autowired
	YearOfPassingRepository  yearOfPassingRepository;
	
	@Autowired
	DocumentRepository  docRepo;
	
	 @Autowired
	 PriceMasterRepository priceMasterRepo;
	 
	 @Autowired
	 BranchMasterService branchService;
	 
	 @Autowired
	 SemesterService semService;
	 
	 @Autowired
	 StreamService streamService;

	
	@Autowired
	UserRepository  userRepository;
	
	@Override
	public boolean saveStudentVerificationDoc(List<StudentDocVerificationRequest> studentDocReq, HttpServletRequest request) {
		
		logger.info("********verificationServiceImpl saveStudentDocs********");
	
		List<VerificationRequest> verificationRequests = new ArrayList<VerificationRequest>();

			for(StudentDocVerificationRequest stuReq : studentDocReq) {
				

				Long assign_to = (long) 0;
				VerificationRequest verReq = new VerificationRequest();
				System.out.println("------In Save Req FOR LOOP----");
				
				verReq.setCollegeId(stuReq.getCollegenameid());
				verReq.setYearOfPassingId(String.valueOf(stuReq.getYearofpassid()));
//				verReq.setDocumentName(stuReq.getDocName());
				verReq.setEnrollmentNumber(stuReq.getEnrollno());
				verReq.setApplicationId(stuReq.getApplicationid());
				verReq.setFirstName(stuReq.getFirstname());
				verReq.setLastName(stuReq.getLastname());
				verReq.setStreamId(stuReq.getStreamid());
				verReq.setUniversityId(stuReq.getUniid());
				verReq.setUserId(stuReq.getUserid());
				verReq.setDocStatus("Requested");
				verReq.setUploadDocumentPath(stuReq.getUploaddocpath());
				verReq.setVerRequestId(stuReq.getVerreqid());
				verReq.setAssignedTo(assign_to);
				verReq.setCreateby(stuReq.getCreateby());
				verReq.setEmbassyid(stuReq.getEmbassyid());
				verificationRequests.add(verReq);
			}

			
			verificationReqRepo.saveAll(verificationRequests);
			

			
		return true;
	}

	
	
	@Override
	public HashMap<Object, Object> getdatabyapplicationId(String applicationId) {
		
		HashMap<Object, Object>  map = new HashMap<>();
		
		Long id = (long) 0;
		
		
		
		
		List<VerificationRequest> verlistEntity= verificationReqRepo.findByApplicationId(Long.parseLong(applicationId));
		
		List<VerificationResponse> studentDocList = new ArrayList<>();
			
			System.out.println("-----getdatabyapplicationId---");
			int year = Calendar.getInstance().get(Calendar.YEAR);
			
			for(VerificationRequest verEntities : verlistEntity) {
				
				VerificationResponse docResponse = new VerificationResponse();
				
				long yearOfPassId = Long.parseLong(verEntities.getYearOfPassingId());
				PriceMaster diff =  priceMasterRepo.getPriceByYearDiff(year , yearOfPassId,verEntities.getRequestType(),Long.parseLong(verEntities.getDocumentId()));
				
				BranchMasterEntity bm = branchService.getbranchById(verEntities.getBranchId());
				
				SemesterEntity se = semService.getSemById(verEntities.getSemId());
				
				StreamMaster sm = streamService.getNameById(verEntities.getStreamId());
				
			    id  = verEntities.getUserId();
				
//			    docResponse.setAmount(diff.getAmount());
//			    docResponse.setSecurAmt(diff.getSecurCharge());
			    docResponse.setGst(diff.getGst());
			    docResponse.setAmount(verEntities.getDocUniAmt());
			    docResponse.setSecurAmt(verEntities.getDocSecurCharge());
//			    docResponse.setGst(verEntities.get);
				docResponse.setApplication_id(verEntities.getApplicationId());
				docResponse.setCollege_name_id(verEntities.getCollegeId());
//				docResponse.setDoc_name(verEntities.getDocumentId());
				docResponse.setDocAmt(verEntities.getDocAmt());
				docResponse.setDocAmtWithGST(verEntities.getDosAmtWithGst());
				docResponse.setEnroll_no(verEntities.getEnrollmentNumber());
				docResponse.setFirst_name(verEntities.getFirstName());
				docResponse.setLast_name(verEntities.getLastName());
				docResponse.setBranch_nm(bm.getBranchName());
				docResponse.setSemester(se.getSemester());
				docResponse.setStream_name(sm.getStreamName());
				docResponse.setMonthOfPassing(verEntities.getMonthOfPassing());
				
				docResponse.setId(verEntities.getId());
				
				docResponse.setYear_of_pass_id(Long.valueOf(verEntities.getYearOfPassingId()));
				
				Long yearid = Long.parseLong(verEntities.getYearOfPassingId());
				PassingYearMaster passingYearMaster = yearOfPassingRepository.getOne(yearid);
				Long docid = Long.parseLong(verEntities.getDocumentId());
				DocumentMaster docMaster = docRepo.getOne(docid);
				
				docResponse.setYear(passingYearMaster.getYearOfPassing());
				docResponse.setDoc_name(docMaster.getDocumentName());
				studentDocList.add(docResponse);
			
			}
		
			UserMasterEntity userEntity = userRepository.getOne(id);
		
			
			map.put("doclist", studentDocList);
			map.put("userEntity", userEntity);
			
		return map;
		
	}



	@Override
	public String updateAssignedto() {
		
		logger.info("********verificationServiceImpl updateAssignedto********");

		Integer cnt=verificationReqRepo.updateAssignedto();
		
		return cnt.toString();
	}
	
	

}
