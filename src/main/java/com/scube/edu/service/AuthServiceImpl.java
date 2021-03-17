package com.scube.edu.service;

import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Date;
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
	
	Base64.Encoder baseEncoder = Base64.getEncoder();
      
      
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
		
		logger.info("loginRequest.email "+ loginRequest.getUsername());
<<<<<<< Updated upstream
		
		
=======
>>>>>>> Stashed changes
		
		if(loginRequest.getUsername().equalsIgnoreCase("")) {
			throw new Exception("Error: Login Id cannot be empty!");
		}
		if(loginRequest.getPassword().equalsIgnoreCase("")) {
			throw new Exception("Error: Password cannot be empty!");
		}
		
		
<<<<<<< Updated upstream
		logger.info("---------checking values-----------  "+ userRepository.existsByEmailIdAndIsactive(loginRequest.getUsername() ,"Y"));
=======
		logger.info("---------checking values-----------  "+ userRepository.existsByUsernameAndIsactive(loginRequest.getUsername() ,"Y"));
>>>>>>> Stashed changes
		
		 if(!userRepository.existsByEmailIdAndIsactive(loginRequest.getUsername(),"Y"))
		 {
			 
			 //  msg = "User is not active";
			 throw new Exception("Error: User unauthorized!");
		}
		 
		UserMasterEntity masterEntity =  userRepository.findByEmailId(loginRequest.getUsername());
		
		
		System.out.println("===masterEntity======="+masterEntity.getEmailVerificationStatus());
		 
		 if(!masterEntity.getEmailVerificationStatus().equalsIgnoreCase("Verified"))
		 {
			 
			//  msg = "Email is nor verified";
			 throw new Exception("Email is not Verified!");
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
				 userDetails.getUsername(), 
				 userDetails.getEmail(),
				 userDetails.getRole()
<<<<<<< Updated upstream
				));
=======
				 ));
>>>>>>> Stashed changes
		
		return baseResponse;
       
	
	}

	
	@Override
	public Boolean addUser(UserAddRequest userAddRequest) throws Exception {
		
		
		 UserMasterEntity userMasterEntity  =new  UserMasterEntity();
		 System.out.println("------------"+userMasterEntity);
		 
		
		 if (userAddRequest.getPassword() == null) { 
			 throw new Exception("Error: Password is mandatory!"); 
		 }
		 
		 
		 UserMasterEntity userEntities  = userRepository.findByEmailId(userAddRequest.getEmailId());
		 
			if(userEntities != null) {
				throw new Exception("Error: This email id already exists");
			}
		
		 userMasterEntity.setCompanyName(userAddRequest.getCompanyName());
		 userMasterEntity.setFirstName(userAddRequest.getFirstName());
		 userMasterEntity.setLastName(userAddRequest.getLastName());
		 userMasterEntity.setEmailId(userAddRequest.getEmailId()); 
		 userMasterEntity.setPhoneNo(userAddRequest.getPhoneNumber());
		 userMasterEntity.setPassword(encoder.encode(userAddRequest.getPassword()));
		 userMasterEntity.setEmailVerificationStatus("N");
		 userMasterEntity.setUniversityId(userAddRequest.getUniversityId());
		 userMasterEntity.setUsername(userAddRequest.getEmailId());
	
		 userMasterEntity.setRoleId(userAddRequest.getRoleId());
		 userMasterEntity.setGSTNo(userAddRequest.getGstNo());
		 userMasterEntity.setIsactive("Y");
	
		 
		 userRepository.save(userMasterEntity);
		 

			if(userAddRequest.getRoleId() == 1) {
				
<<<<<<< Updated upstream
				emailService.sendVerificationEmail(userAddRequest.getEmailId());
				
			/*
			 * UserMasterEntity entities = userRepository.getOne(userMasterEntity.getId());
			 * entities.setEmailVerificationStatus("Verified");
			 * 
			 * userRepository.save(entities);
			 */
				
				
=======
>>>>>>> Stashed changes
			}
			
	
		return true;
		
	}


	@Override
	public BaseResponse resetPasswordBySendingEMail(String email) throws Exception {
		 
		baseResponse	= new BaseResponse();
			
		logger.info("----------resetPasswordBySendingEMail---------");
		
		 UserMasterEntity userEntities  = userRepository.findByEmailId(email);
		 
			if(userEntities == null) {
				throw new Exception("Error: Invalid Email");
			}
		
			 UserMasterEntity entities =  userRepository.getOne(userEntities.getId());
			 entities.setForgotPasswordFlag("Y");
			
			 
			 userRepository.save(entities);
			 
			 String encodeEmail = baseEncoder.encodeToString(email.getBytes(StandardCharsets.UTF_8)) ;
			 
			 logger.info("---------encodeEmail-------"+encodeEmail);
			
			//Send Email 
			emailService.sendEmail(email,encodeEmail);
			
			baseResponse.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
			baseResponse.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
			baseResponse.setRespData("success");
			 
			return baseResponse;
	}


	@Override
	public BaseResponse UpdatePassword(UserMasterEntity reqUserentity) throws Exception {
		
		baseResponse	= new BaseResponse();
		
		 logger.info("---------Email-------"+reqUserentity.getEmailId());   
		 logger.info("---------password-------"+reqUserentity.getPassword());
		 
		 UserMasterEntity userEntities  = userRepository.findByEmailId(reqUserentity.getEmailId());
		 
		   if(userEntities == null) {
				throw new Exception("Error: Invalid Email");
			}
		
		
		 UserMasterEntity entities =  userRepository.getOne(userEntities.getId());
		 entities.setPassword(encoder.encode(reqUserentity.getPassword()));
		 entities.setForgotPasswordFlag("N");
		 
		 
		 userRepository.save(entities);
		
		baseResponse.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
		baseResponse.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
		baseResponse.setRespData("success");
		 
		return baseResponse;
	}


	@Override
	public BaseResponse checkFlagOnClickOfLink(String encodeEmail) throws Exception {
		
        baseResponse	= new BaseResponse();
        String flag = "";
		
        Base64.Decoder decoder = Base64.getDecoder();  
        // Decoding string  
        String dStr = new String(decoder.decode(encodeEmail));  
       
        UserMasterEntity userEntities  = userRepository.findByEmailId(dStr);
        
        if(userEntities == null) {
        	
			throw new Exception("Error: User Not Found");
		}
        System.out.println("-----------------ForgotPasswordFlag-------------------"+userEntities.getForgotPasswordFlag());
        
        if(userEntities.getForgotPasswordFlag().equalsIgnoreCase("Y")) {
        	
        	flag ="success";
        	
        }else {
        	
        	flag = "failure";
        }
        
		baseResponse.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
		baseResponse.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
		baseResponse.setRespData(flag);
		 
		return baseResponse;
	}
	
	
	 public static String encode(String input) {

	      return Base64.getEncoder().encodeToString(input.getBytes());

	  }

	  //Decode:
	  public static String decode(String input) {

	      byte[] decodedBytes = Base64.getDecoder().decode(input);
	      return new String(decodedBytes);

	  }


	@Override
	public boolean verifyStudentEmail(String emailId) throws Exception {
		
		  Base64.Decoder decoder = Base64.getDecoder();  
	        // Decoding string  
	      String decodedEmail = new String(decoder.decode(emailId)); 
	        
	        logger.info("---------decodedEmail-------"+decodedEmail);
	        
	        
		 
	       UserMasterEntity userEntities  = userRepository.findByEmailId(decodedEmail);
	       
	       
		   if(userEntities == null) {
				throw new Exception("Error: Invalid Email");
			}
		   
		   
		 int hours = userRepository.gethoursToValidateTheLink(userEntities.getId());
		  logger.info("---------hours-------"+hours);
		  
		  if(hours < 1440) {
			     UserMasterEntity entities =  userRepository.getOne(userEntities.getId());
				 entities.setEmailVerificationStatus("Verified");
				 userRepository.save(entities);
			  
		  }else {
			  
			  throw new Exception("Error: Link is Expired!!!");
			  
		  }

		
		return true;
	}


	
}
