package com.scube.edu.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scube.edu.model.PassingYearMaster;
import com.scube.edu.model.UserMasterEntity;
import com.scube.edu.model.VerificationRequest;
import com.scube.edu.repository.UserRepository;
import com.scube.edu.repository.VerificationRequestRepository;
import com.scube.edu.repository.YearOfPassingRepository;
import com.scube.edu.request.LoginRequest;
import com.scube.edu.request.StudentDocVerificationRequest;
import com.scube.edu.request.UserAddRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.JwtResponse;
import com.scube.edu.response.StudentVerificationDocsResponse;
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
		
		List<StudentVerificationDocsResponse> studentDocList = new ArrayList<>();
			
			System.out.println("-----getdatabyapplicationId---");
			
			
			for(VerificationRequest verEntities : verlistEntity) {
				
				StudentVerificationDocsResponse docResponse = new StudentVerificationDocsResponse();
				
			    id  = verEntities.getUserId();
				
				docResponse.setApplication_id(verEntities.getApplicationId());
				docResponse.setCollege_name_id(verEntities.getCollegeId());
				docResponse.setDoc_name(verEntities.getDocumentId());
				docResponse.setDocAmt(verEntities.getDocAmt());
				docResponse.setDocAmtWithGST(verEntities.getDosAmtWithGst());
				docResponse.setEnroll_no(verEntities.getEnrollmentNumber());
				docResponse.setFirst_name(verEntities.getFirstName());
				docResponse.setLast_name(verEntities.getLastName());
				
				docResponse.setId(verEntities.getId());
				Long yearid = Long.parseLong(verEntities.getYearOfPassingId());
				PassingYearMaster passingYearMaster = yearOfPassingRepository.getOne(yearid);
				docResponse.setYear(passingYearMaster.getYearOfPassing());
				
				studentDocList.add(docResponse);
			
			}
		
			UserMasterEntity userEntity = userRepository.getOne(id);
		
			
			map.put("doclist", studentDocList);
			map.put("userEntity", userEntity);
			
		return map;
		
	}
	
	

}
