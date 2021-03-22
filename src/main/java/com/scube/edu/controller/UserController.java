package com.scube.edu.controller;

import java.util.List;

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

import com.scube.edu.request.UserAddRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.UserResponse;
import com.scube.edu.service.UserService;
import com.scube.edu.util.StringsUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	BaseResponse response = null;
	
	@Autowired
	UserService userService;
	
	/*
	 * @GetMapping("/test") public ResponseEntity<Object> getalluser() { response =
	 * new BaseResponse();
	 * 
	 * List<UserResponse> userResponses = userService.getUserList();
	 * 
	 * response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
	 * response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
	 * response.setRespData("");
	 * 
	 * return ResponseEntity.badRequest().body(response);
	 * 
	 * 
	 * //return ResponseEntity.ok(response); }
	 */
	
	
	
	@GetMapping("/getAllUser")
	public ResponseEntity<Object> getUserList(HttpServletRequest request) {
		
	
		logger.info("********************UsersControllers getAllUsers******************");
		response = new BaseResponse();
		
		try {

			List<UserResponse> userResponses = userService.getUserList();
			
			response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
			response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
			response.setRespData(userResponses);
			
			return ResponseEntity.ok(response);
			
		}
		catch (Exception e) {
			
			logger.error(e.getMessage());
			
			response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
			response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
			response.setRespData(e.getMessage());
			
			return ResponseEntity.badRequest().body(response);
			
		}
		
   }
	
	
	
	
	@GetMapping("/getloginData/{username}/{password}")
	public Boolean getdata(@PathVariable String username,@PathVariable String password) {
		
	
		logger.info("*********dummydata********"+username);
		response = new BaseResponse();
		boolean flag;
		
		try {

		
			if(username.equalsIgnoreCase("Rupali") && password.equalsIgnoreCase("rupali"))
			{
				flag = true;
			}else {
				
				flag = false;
			}
			
			
		}
		catch (Exception e) {
			
			logger.error(e.getMessage());
			
			return false;
			
		}
		 return flag;
   }
	
	
	@GetMapping("/getUserInfoById/{userId}")
	public ResponseEntity<Object> getUserInfoById(@PathVariable Long userId , HttpServletRequest request) {
		
	
		logger.info("********************UsersControllers getAllUsers******************");
		response = new BaseResponse();
		
		try {

			UserResponse userResponses = userService.getUserInfoById(userId);
			
			response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
			response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
			response.setRespData(userResponses);
			
			return ResponseEntity.ok(response);
			
		}
		catch (Exception e) {
			
			logger.error(e.getMessage());
			
			response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
			response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
			response.setRespData(e.getMessage());
			
			return ResponseEntity.badRequest().body(response);
			
		}
		
   }
	
	
	
	
}
