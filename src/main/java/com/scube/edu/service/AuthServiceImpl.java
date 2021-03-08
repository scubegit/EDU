package com.scube.edu.service;

import java.util.HashMap;

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

import com.scube.edu.model.UserMasterEntity;
import com.scube.edu.repository.UserRepository;
import com.scube.edu.request.LoginRequest;
import com.scube.edu.request.UserAddRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.JwtResponse;
import com.scube.edu.security.JwtUtils;
import com.scube.edu.util.StringsUtils;


@Service
public class AuthServiceImpl implements AuthService{
	
	private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
	

	 BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	 
	 @Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	EmailService emailService;
	
	BaseResponse  baseResponse = null;

	@Override
	public BaseResponse authenticateUser(LoginRequest loginRequest, HttpServletRequest request) throws Exception {
	  	
        baseResponse	= new BaseResponse();
		
		logger.info("loginRequest.username "+ loginRequest.getUsername());
		
		if(loginRequest.getUsername().equalsIgnoreCase("")) {
			throw new Exception("Error: Login Id cannot be empty!");
		}
		if(loginRequest.getPassword().equalsIgnoreCase("")) {
			throw new Exception("Error: Password cannot be empty!");
		}
		
		
		logger.info("lKXCVX  "+ userRepository.existsByUsernameAndIsactive(loginRequest.getUsername() ,"Y"));
		
		 if(!userRepository.existsByUsernameAndIsactive(loginRequest.getUsername(),"Y"))
		 {
			 throw new Exception("Error: User unauthorized!");
		}
  
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
	
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();	
	
		baseResponse.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
		baseResponse.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
		baseResponse.setRespData(new JwtResponse(jwt, 
				 userDetails.getId(), 
				 userDetails.getUserid(),
				 userDetails.getUsername(), 
				 userDetails.getEmail()
				 ));
		
		return baseResponse;
       
	
	}

	
	@Override
	public Boolean addUser(UserAddRequest userAddRequest) throws Exception {
		
		
		 UserMasterEntity userMasterEntity  =new  UserMasterEntity();
		 System.out.println("------------"+userMasterEntity);
		 
		 if (userAddRequest.getCompanyname() == null){ 
			 throw new Exception("Error: User name is mandatory!"); 
		 }
		 
		 if (userAddRequest.getPassword() == null) { 
			 throw new Exception("Error: Password is mandatory!"); 
		 }
		 
		
		 userMasterEntity.setCompanyName(userAddRequest.getCompanyname());
		 userMasterEntity.setUsername(userAddRequest.getContactPersonName());
		 userMasterEntity.setEmail(userAddRequest.getEmailid()); 
		 userMasterEntity.setContactPersonPhone(userAddRequest.getMobilenumber());
		 userMasterEntity.setPassword(encoder.encode(userAddRequest.getPassword()));
		 userMasterEntity.setEmailVerificationStatus(userAddRequest.getVerificationStatus());
		 userMasterEntity.setUniversityId(userAddRequest.getUniversityid());
		 userMasterEntity.setUserId(userAddRequest.getUserid());
		 userMasterEntity.setRoleId(userAddRequest.getRoleId());
		 userMasterEntity.setGSTNo(userAddRequest.getGstNo());
		 userMasterEntity.setIsactive("Y");
	
		 
		 userRepository.save(userMasterEntity);
	
		return true;
	}


	@Override
	public BaseResponse resetPasswordByMail(HttpServletRequest request) throws MessagingException {
		 
		baseResponse	= new BaseResponse();
			
		logger.info("----------sending..----------");
			
			
		emailService.sendEmail();
		
		baseResponse.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
		baseResponse.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
		baseResponse.setRespData("success");
		 
		return baseResponse;
	}
	
	
	
	
	
}
