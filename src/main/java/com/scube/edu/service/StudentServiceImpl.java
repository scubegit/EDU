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
			
			
		 
//		 	baseResponse.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
//			baseResponse.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
//			baseResponse.setRespData(List);
		 
		return List;
		 
	 }
	
}
