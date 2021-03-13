package com.scube.edu.service;

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
	
	@Override
	public boolean saveDispute(DisputeRequest disputeReq, HttpServletRequest request) {
		
		logger.info("********DisputeServiceImpl saveDispute********");
		
		RaiseDespute rd = new RaiseDespute();
		
		rd.setApplicationId(disputeReq.getApplication_id());
		rd.setContactPersonEmail(disputeReq.getEmail());
		rd.setContactPersonPhone(disputeReq.getPhone_no());
		rd.setReasonForDispute(disputeReq.getReason());
		rd.setCreateby(disputeReq.getCreated_by());
		rd.setComment(disputeReq.getComment());
		rd.setContactPersonName(disputeReq.getContact_person_name()); 
		rd.setStatus(disputeReq.getStatus());
		// dummy values of comment, contact person name and status have been sent through postman right now!
		disputeRepo.save(rd);
		
		return true;
	}
	
}
