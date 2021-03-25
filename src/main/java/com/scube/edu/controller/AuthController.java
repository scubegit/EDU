
package com.scube.edu.controller;
import javax.servlet.http.HttpServletRequest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scube.edu.model.UserMasterEntity;
import com.scube.edu.request.LoginRequest;
import com.scube.edu.request.UserAddRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.service.AuthService;
import com.scube.edu.util.StringsUtils;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	BaseResponse response = null;
	
	@Autowired
	AuthService	authService;
	
	
	
	@PostMapping("/studentSignUp")
	public ResponseEntity<Object> addUser(@RequestBody UserAddRequest userRequest ,  HttpServletRequest request) {
		
		logger.info("********UsersControllers addUser()********");
		response = new BaseResponse();
		
		try {

			Boolean flag = authService.addUser(userRequest);
			
			response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
			response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
			response.setRespData(flag);
			
			return ResponseEntity.ok(response);
			
		}
		catch (Exception e) {
			logger.error(e.getMessage());
			
			response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
			response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
			response.setRespData(e.getMessage());
			
			//return ResponseEntity.badRequest().body(response);
			return ResponseEntity.ok(response);
		}
		
   }
	
	
	
	@PostMapping("/signin")
	public ResponseEntity<Object> authenticateUser(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {

		logger.info("********************AuthController authenticateUser******************");
		
		response = new BaseResponse();
		
		try {
			response = authService.authenticateUser(loginRequest,request);
			
			
			return ResponseEntity.ok(response);
				
		}catch (Exception e) {
			
			logger.error(e.getMessage()); //BAD creds message comes from here
			
			response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
			response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
			response.setRespData(e.getMessage());
			
			return ResponseEntity.ok(response);
			
			
		}
		
	}

	
	@PostMapping("/resetPassword/{email}")
	public  ResponseEntity<Object> resetPasswordBySendingMail(@PathVariable String email) {
		logger.info("********UsersControllers addUser()********");
		
		response = new BaseResponse();
		
		try {
			response = authService.resetPasswordBySendingEMail(email);
			
			return ResponseEntity.ok(response);
				
		}catch (Exception e) {
			
			
			logger.error(e.getMessage()); //BAD creds message comes from here
			
			response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
			response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
			response.setRespData(e.getMessage());
			
			return ResponseEntity.ok(response);
			
		}
		
   }
	
	
	@GetMapping("/checkFlagOnClickOfLink/{encodeEmail}")
	public  ResponseEntity<Object> checkFlagOnClickOfLink(@PathVariable String encodeEmail) {
		
		response = new BaseResponse();
		
		    try {
					response = authService.checkFlagOnClickOfLink(encodeEmail);
					
					return ResponseEntity.ok(response);
						
				}catch (Exception e) {
					
					logger.error(e.getMessage()); //BAD creds message comes from here
					
					response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
					response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
					response.setRespData(e.getMessage());
					
					return ResponseEntity.badRequest().body(response);
					
				}
			
   }
	
	@PostMapping("/UpdatePassword")
	public  ResponseEntity<Object> UpdatePassword(@RequestBody UserMasterEntity userentity) {
		
		response = new BaseResponse();
		
		try {
			
			response = authService.UpdatePassword(userentity);
			
			return ResponseEntity.ok(response);
				
		}catch (Exception e) {
			
			logger.error(e.getMessage()); //BAD creds message comes from here
			
			response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
			response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
			response.setRespData(e.getMessage());
			
			return ResponseEntity.ok(response);
			
		}
		
   }
	
	
	
	@PostMapping("/employerSignUp")
	public ResponseEntity<Object> addEmployerUser(@RequestBody UserAddRequest userRequest ,  HttpServletRequest request) {
		
		logger.info("********UsersControllers addUser()********");

		response = new BaseResponse();
		
		try {

			Boolean flag = authService.addUser(userRequest);
			
			response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
			response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
			response.setRespData(flag);
			
			return ResponseEntity.ok(response);
			
		}
		catch (Exception e) {
			logger.error(e.getMessage());
			
			response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
			response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
			response.setRespData(e.getMessage());
			
			return ResponseEntity.ok(response);
			
		}
		
   }
	

	@GetMapping("/verifyEmail/{emailId}")
	public  ResponseEntity<Object>   verifyStudentEmail(@PathVariable String emailId) {
		
		response = new BaseResponse();
		boolean flag;
		
		
		
		 try {
			 
			flag = authService.verifyEmail(emailId);

			response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
			response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
			response.setRespData(flag);
			
			return ResponseEntity.ok(response);
			
			
		} catch (Exception e) {
           logger.error(e.getMessage());
			
			response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
			response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
			response.setRespData(e.getMessage());
			
			return ResponseEntity.ok(response);
		}
		
			
   }


}

