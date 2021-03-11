package com.scube.edu.service;

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
import org.springframework.web.multipart.MultipartFile;

import com.scube.edu.controller.UserController;
import com.scube.edu.model.RaiseDespute;
import com.scube.edu.model.UserMasterEntity;
import com.scube.edu.model.VerificationRequest;
import com.scube.edu.repository.RaiseDisputeRepository;
import com.scube.edu.repository.UserRepository;
import com.scube.edu.repository.VerificationRequestRepository;
import com.scube.edu.request.DisputeRequest;
import com.scube.edu.request.LoginRequest;
import com.scube.edu.request.StudentDocRequest;
import com.scube.edu.request.UserAddRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.JwtResponse;
import com.scube.edu.security.JwtUtils;
import com.scube.edu.util.StringsUtils;

@Service
public class DisputeServiceImpl implements DisputeService{

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	RaiseDisputeRepository disputeRepo;
	
	@Override
	public String saveDispute(DisputeRequest disputeReq, HttpServletRequest request) {
		
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
		
		return null;
	}
	
}
