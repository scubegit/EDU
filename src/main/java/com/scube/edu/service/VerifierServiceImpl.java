package com.scube.edu.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

import com.scube.edu.model.DocumentMaster;
import com.scube.edu.model.PassingYearMaster;
import com.scube.edu.model.UserMasterEntity;
import com.scube.edu.model.VerificationRequest;
import com.scube.edu.repository.UserRepository;
import com.scube.edu.repository.VerificationRequestRepository;
import com.scube.edu.response.BaseResponse;

import com.scube.edu.response.JwtResponse;
import com.scube.edu.response.StreamResponse;
import com.scube.edu.response.StudentVerificationDocsResponse;
import com.scube.edu.security.JwtUtils;
import com.scube.edu.util.StringsUtils;


@Service
public class VerifierServiceImpl implements VerifierService{
	
	private static final Logger logger = LoggerFactory.getLogger(VerifierServiceImpl.class);
	
	Base64.Encoder baseEncoder = Base64.getEncoder();
	
	BaseResponse	baseResponse	= null;  
      
	 BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	 
	 @Autowired
	 VerificationRequestRepository verificationReqRepository;
	 
	 @Autowired
	 YearOfPassingService yearOfPassService;
	 
	 @Autowired
	 DocumentService	documentService;
	 
	 @Autowired
	 UserRepository userRepository;
	 
	 public List<StudentVerificationDocsResponse> getVerifierRequestList() throws Exception {
		 
		 logger.info("********VerifierServiceImpl getVerifierRequestList********");
		 
		 List<StudentVerificationDocsResponse> List = new ArrayList<>();
		 
		 List<VerificationRequest> verReq = verificationReqRepository.getVerifierRecords();
		 
		 for(VerificationRequest veriReq: verReq) {
			 
			 StudentVerificationDocsResponse resp = new StudentVerificationDocsResponse();
			 
			 PassingYearMaster year = yearOfPassService.getYearById(veriReq.getYearOfPassingId());
			 
			 System.out.println(veriReq.getDocumentId());
			 
			 DocumentMaster doc = documentService.getNameById(veriReq.getDocumentId());
			 
			 Optional<UserMasterEntity> user = userRepository.findById(veriReq.getUserId());
			 UserMasterEntity userr = user.get();
			 
			 resp.setId(veriReq.getId());
			 resp.setApplication_id(veriReq.getApplicationId());
			 resp.setCollege_name_id(veriReq.getCollegeId());
			 resp.setDoc_name(doc.getDocumentName());
			 resp.setEnroll_no(veriReq.getEnrollmentNumber());
			 resp.setFirst_name(veriReq.getFirstName());
			 resp.setLast_name(veriReq.getLastName());
			 resp.setStream_id(veriReq.getStreamId());
			 resp.setUni_id(veriReq.getUniversityId());
			 resp.setUpload_doc_path(veriReq.getUploadDocumentPath());
			 resp.setUser_id(veriReq.getUserId());
			 resp.setVer_req_id(veriReq.getVerRequestId());
			 resp.setYear(year.getYearOfPassing());
			 resp.setCompany_name(userr.getCompanyName());
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
