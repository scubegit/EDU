package com.scube.edu.service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

import com.scube.edu.model.StreamMaster;
import com.scube.edu.model.VerificationRequest;
import com.scube.edu.repository.VerificationRequestRepository;
import com.scube.edu.request.UserAddRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.JwtResponse;
import com.scube.edu.response.StreamResponse;
import com.scube.edu.response.StudentDocsResponse;
import com.scube.edu.security.JwtUtils;
import com.scube.edu.util.StringsUtils;

@Service
public class StudentServiceImpl implements StudentService{

private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
	
	Base64.Encoder baseEncoder = Base64.getEncoder();
	
	BaseResponse	baseResponse	= null;  
      
	 BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	 
	 @Autowired
	 VerificationRequestRepository verificationReqRepository;
	 
	 @Override
		public List<StudentDocsResponse> getVerificationDataByUserid(long userId) throws Exception {
		 
		 logger.info("********StudentServiceImpl getVerificationDataByUserid********");
		 
		 	List<StudentDocsResponse> List = new ArrayList<>();
		 	
//		 	
			List<VerificationRequest> verReq = verificationReqRepository.findByUserId(userId);
			
			for(VerificationRequest veriRequest : verReq) {
				
				StudentDocsResponse stuDocResp = new StudentDocsResponse();
				
				stuDocResp.setApplication_id(veriRequest.getApplicationId());
				stuDocResp.setCollege_name_id(veriRequest.getCollegeId());
				stuDocResp.setDoc_name(veriRequest.getDocumentName());
				stuDocResp.setEnroll_no(veriRequest.getEnrollmentNumber());
				stuDocResp.setFirst_name(veriRequest.getFirstName());
				stuDocResp.setLast_name(veriRequest.getLastName());
//				stuDocResp.setRequest_type_id(veriRequest.get);
				stuDocResp.setStream_id(veriRequest.getStreamId());
				stuDocResp.setUni_id(veriRequest.getUniversityId());
				stuDocResp.setUser_id(veriRequest.getUserId());
				stuDocResp.setVer_req_id(veriRequest.getVerRequestId());
				stuDocResp.setYear_of_pass_id(veriRequest.getYearOfPassingId());		
				
				List.add(stuDocResp);
				
			}
		return List;
		 
	 }

	@Override
	public List<StudentDocsResponse> getClosedRequests(long userId) {
		
		logger.info("********StudentServiceImpl getClosedRequests********"+ userId);
		
		List<StudentDocsResponse> List = new ArrayList<>();
		
		List<VerificationRequest> closedReqs = verificationReqRepository.findByStatusAndUserId(userId);
		System.out.println("---------------------"+ closedReqs);
		
		for(VerificationRequest req: closedReqs) {
			
			StudentDocsResponse closedDocResp = new StudentDocsResponse();
			
			closedDocResp.setId(req.getId());
			closedDocResp.setApplication_id(req.getApplicationId());
			closedDocResp.setCollege_name_id(req.getCollegeId());
			closedDocResp.setDoc_name(req.getDocumentName());
			closedDocResp.setEnroll_no(req.getEnrollmentNumber());
			closedDocResp.setFirst_name(req.getFirstName());
			closedDocResp.setLast_name(req.getLastName());
//			closedDocResp.setRequest_type_id(req.get);
			closedDocResp.setStream_id(req.getStreamId());
			closedDocResp.setUni_id(req.getUniversityId());
			closedDocResp.setUser_id(req.getUserId());
			closedDocResp.setVer_req_id(req.getVerRequestId());
			closedDocResp.setYear_of_pass_id(req.getYearOfPassingId());	
			
			List.add(closedDocResp);
			
		}
		
		return List;
	}
	
}
