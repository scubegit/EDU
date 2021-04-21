package com.scube.edu.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scube.edu.model.DocumentMaster;
import com.scube.edu.model.PassingYearMaster;
import com.scube.edu.model.RaiseDespute;
import com.scube.edu.model.StreamMaster;
import com.scube.edu.model.UserMasterEntity;
import com.scube.edu.model.VerificationRequest;
import com.scube.edu.repository.RaiseDisputeRepository;
import com.scube.edu.repository.UserRepository;
import com.scube.edu.repository.VerificationRequestRepository;
import com.scube.edu.request.StatusChangeRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.DisputeResponse;
import com.scube.edu.response.EmployerVerificationDocResponse;
import com.scube.edu.response.RequestTypeResponse;
import com.scube.edu.response.StudentVerificationDocsResponse;
import com.scube.edu.response.UserResponse;
import com.scube.edu.response.VerificationResponse;

@Service
public class SuperVerifierServiceImpl implements SuperVerifierService {
	
private static final Logger logger = LoggerFactory.getLogger(EmployerServiceImpl.class);
	
	BaseResponse	baseResponse	= null;  
	
	@Autowired
	VerificationRequestRepository verificationReqRepository;
	
	@Autowired 
	StreamService streamService;
	
	@Autowired
	RaiseDisputeRepository disputeRepo;
	 
	@Autowired 
	RequestTypeService reqTypeService;
	
	@Autowired
	YearOfPassingService yearOfPassService;
	
	@Autowired
	DocumentService	documentService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	UserService userService;

	@Override
	public List<VerificationResponse> getVerificationDocList(String fromDate, String toDate) {
		
		logger.info("*******SuperVerifierServiceImpl getVerificationDocList*******");
		
		List<VerificationResponse> responseList = new ArrayList<>();
		
		List<VerificationRequest> list = verificationReqRepository.findByStatus(fromDate, toDate);
		
		for(VerificationRequest req: list) {
			
			VerificationResponse resp = new VerificationResponse();
			
			PassingYearMaster year = yearOfPassService.getYearById(req.getYearOfPassingId());
			
			DocumentMaster doc = documentService.getNameById(req.getDocumentId());
			
			StreamMaster stream = streamService.getNameById(req.getStreamId());
			
			Optional<UserMasterEntity> user = userRepository.findById(req.getVerifiedBy());
			UserMasterEntity userr = user.get();
			
			if(req.getRequestType() != null) {
			RequestTypeResponse request = reqTypeService.getNameById(req.getRequestType());
			resp.setRequest_type_id(request.getRequestType());
			}
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
			String strDate= formatter.format(req.getCreatedate());
			
			resp.setDoc_status(req.getDocStatus());
			resp.setId(req.getId());
			resp.setApplication_id(req.getApplicationId());
//			closedDocResp.setCollege_name_id(req.getCollegeId());
			resp.setDoc_name(doc.getDocumentName()); //
			resp.setEnroll_no(req.getEnrollmentNumber());
			resp.setFirst_name(req.getFirstName());
			resp.setLast_name(req.getLastName());
			
////			resp.setRequest_type_id(req.get);
//			resp.setStream_id(req.getStreamId());
//			resp.setUni_id(req.getUniversityId());
			resp.setUser_id(req.getUserId());
			resp.setVer_req_id(req.getVerRequestId());
			resp.setYear(year.getYearOfPassing());	
			resp.setUpload_doc_path(req.getUploadDocumentPath());
			resp.setStream_name(stream.getStreamName());
			resp.setReq_date(strDate);
			resp.setVerifier_name(userr.getFirstName() + " " + userr.getLastName());
			
			responseList.add(resp);
			
		}
		
		return responseList;
	}

	@Override
	public List<StudentVerificationDocsResponse> setStatusForSuperVerifierDocument(StatusChangeRequest statusChangeRequest) throws Exception {
		
		logger.info("*******SuperVerifierServiceImpl setStatusForSuperVerifierDocument*******");
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
	    Date date = new Date();  
	    String currentDate = formatter.format(date);  
		
		VerificationRequest veriReq =  verificationReqRepository.findById(statusChangeRequest.getId());
//		VerificationRequest veriReq = vr.get();
		
		System.out.println(veriReq.getDocStatus() + veriReq.getApplicationId());
		
		veriReq.setDocStatus(statusChangeRequest.getStatus());
		veriReq.setRemark("SVR_"+currentDate+"-"+statusChangeRequest.getRemark());
		
		verificationReqRepository.save(veriReq);
		
		if(statusChangeRequest.getStatus().equalsIgnoreCase("Approved") || 
				statusChangeRequest.getStatus().equalsIgnoreCase("SV_Approved") || 
				statusChangeRequest.getStatus().equalsIgnoreCase("Rejected") || 
				statusChangeRequest.getStatus().equalsIgnoreCase("SV_Rejected")) {
			
		UserResponse ume = userService.getUserInfoById(veriReq.getUserId());
		emailService.sendStatusMail(ume.getEmail(), veriReq.getId(), statusChangeRequest.getStatus());
		
		}
		

		
		return null;
	}

	@Override
	public List<DisputeResponse> getDisputeList() {
		
		logger.info("*******superVerifierServiceImpl getDisputeList*******");
		
		List<RaiseDespute> rd = disputeRepo.findAll();
		
		List<DisputeResponse> responses = new ArrayList<>();
		
		for(RaiseDespute res: rd) {
			
			DisputeResponse resp = new DisputeResponse();
			
			resp.setId(res.getId());
			resp.setCreated_by(res.getCreateby());
			resp.setEmailid(res.getContactPersonEmail());
			resp.setApplication_id(res.getApplicationId());
			resp.setPhone_no(res.getContactPersonPhone());
			resp.setReason(res.getReasonForDispute());
			resp.setVerification_id(res.getVerificationId());
			
			responses.add(resp);
			
			
		}
		
		return responses;
	}
	
	

}
