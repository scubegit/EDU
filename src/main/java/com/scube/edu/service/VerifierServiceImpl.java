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
public class VerifierServiceImpl implements VerifierService{
	
	private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
	
	Base64.Encoder baseEncoder = Base64.getEncoder();
	
	BaseResponse	baseResponse	= null;  
      
	 BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	 
	 @Autowired
	 VerificationRequestRepository verificationReqRepository;
	 
	 public List<StudentDocsResponse> getVerifierRequestList() throws Exception {
		 
		 logger.info("********VerifierServiceImpl getVerifierRequestList********");
		 
		 List<StudentDocsResponse> List = new ArrayList<>();
		 
		 List<VerificationRequest> verReq = verificationReqRepository.getVerifierRecords();
		 
		 for(VerificationRequest veriReq: verReq) {
			 
			 StudentDocsResponse resp = new StudentDocsResponse();
			 
			 resp.setId(veriReq.getId());
			 resp.setApplication_id(veriReq.getApplicationId());
			 resp.setCollege_name_id(veriReq.getCollegeId());
			 resp.setDoc_name(veriReq.getDocumentName());
			 resp.setEnroll_no(veriReq.getEnrollmentNumber());
			 resp.setFirst_name(veriReq.getFirstName());
			 resp.setLast_name(veriReq.getLastName());
			 resp.setStream_id(veriReq.getStreamId());
			 resp.setUni_id(veriReq.getUniversityId());
			 resp.setUpload_doc_path(veriReq.getUploadDocumentPath());
			 resp.setUser_id(veriReq.getUserId());
			 resp.setVer_req_id(veriReq.getVerRequestId());
			 resp.setYear_of_pass_id(veriReq.getYearOfPassingId());
			 
			 // run query here which will update 'assigned_to' column with userId value
			 // for now assign any value other than 0 (assign 1)
			 Long a = (long) 1;
			 VerificationRequest ent = verificationReqRepository.getOne(veriReq.getId());
			 ent.setAssignedTo(a);
			 verificationReqRepository.save(ent);
			 
			 // ON logout, change 'assignedTo' field back to 0
			 
			 List.add(resp);
		 }
		 
		 System.out.println("----------"+ verReq);
		 
		 return List;
		 
	 }

}
