package com.scube.edu.service;

import java.util.Objects;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.scube.edu.model.RaiseDespute;
import com.scube.edu.repository.RaiseDisputeRepository;
import com.scube.edu.request.DisputeRequest;




@Service
public class DisputeServiceImpl implements DisputeService{

	private static final Logger logger = LoggerFactory.getLogger(DisputeServiceImpl.class);
	
	@Autowired
	RaiseDisputeRepository disputeRepo;
	
	@Autowired
	EmailService emailService;
	
	@Override
	public boolean saveDispute(DisputeRequest disputeReq, HttpServletRequest request) throws MessagingException {
		
		logger.info("********DisputeServiceImpl saveDispute********");
		
		RaiseDespute rd = new RaiseDespute();
		
//		RaiseDespute check = disputeRepo.findByApplicationId(disputeReq.getApplication_id());
		RaiseDespute check = disputeRepo.findByVerificationId(disputeReq.getId());
		
		if(Objects.isNull(check)) {
			System.out.println("NULL---");
		
		rd.setVerificationId(disputeReq.getId());
		rd.setApplicationId(disputeReq.getApplication_id());
		rd.setContactPersonEmail(disputeReq.getEmail());
		rd.setContactPersonPhone(disputeReq.getPhone_no());
		rd.setReasonForDispute(disputeReq.getReason());
		rd.setCreateby(disputeReq.getCreated_by());
		rd.setComment(disputeReq.getComment());
		rd.setContactPersonName(disputeReq.getContact_person_name()); 
		rd.setStatus(disputeReq.getStatus());
		// dummy values of comment, contact person name and status have been sent through postman right now!
		RaiseDespute raised = disputeRepo.save(rd);
		logger.info("------" + raised.getId());
		//send email to applicant from here
		emailService.sendDisputeSaveMail(disputeReq.getEmail(), disputeReq.getApplication_id(), raised.getId());
		
		return true;
		}
		return false;
		
	}
	
}
