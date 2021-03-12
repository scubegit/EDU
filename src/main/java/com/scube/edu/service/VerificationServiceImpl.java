package com.scube.edu.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scube.edu.model.VerificationRequest;
import com.scube.edu.repository.VerificationRequestRepository;

import com.scube.edu.request.LoginRequest;
import com.scube.edu.request.StudentDocVerificationRequest;
import com.scube.edu.request.UserAddRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.JwtResponse;
import com.scube.edu.security.JwtUtils;
import com.scube.edu.util.StringsUtils;


@Service
public class VerificationServiceImpl implements VerificationService{
	
	private static final Logger logger = LoggerFactory.getLogger(VerificationServiceImpl.class);
	
	@Autowired
	VerificationRequestRepository verificationReqRepo;

	@Override
	public boolean saveStudentVerificationDoc(List<StudentDocVerificationRequest> studentDocReq, HttpServletRequest request) {
		
		logger.info("********verificationServiceImpl saveStudentDocs********");
	
		List<VerificationRequest> verificationRequests = new ArrayList<VerificationRequest>();

			for(StudentDocVerificationRequest stuReq : studentDocReq) {
				

				Long assign_to = (long) 0;
				VerificationRequest verReq = new VerificationRequest();
				System.out.println("------In Save Req FOR LOOP----");
				
				verReq.setCollegeId(stuReq.getCollegeNameId());
				verReq.setYearOfPassingId(String.valueOf(stuReq.getYearOfPassId()));
				verReq.setDocumentName(stuReq.getDocName());
				verReq.setEnrollmentNumber(stuReq.getEnrollNo());
				verReq.setApplicationId(stuReq.getApplicationId());
				verReq.setFirstName(stuReq.getFirstName());
				verReq.setLastName(stuReq.getLastName());
				verReq.setStreamId(stuReq.getStreamId());
				verReq.setUniversityId(stuReq.getUniId());
				verReq.setUserId(stuReq.getUserId());
				verReq.setDocStatus("Requested");
				verReq.setUploadDocumentPath(stuReq.getUploadDocPath());
				verReq.setVerRequestId(stuReq.getVerReqId());
				verReq.setAssignedTo(assign_to);
				
				verificationRequests.add(verReq);
			}

			
			verificationReqRepo.saveAll(verificationRequests);
			

			
		return true;
	}
	
	

}
