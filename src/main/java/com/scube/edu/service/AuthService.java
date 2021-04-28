package com.scube.edu.service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import com.scube.edu.model.UserMasterEntity;
import com.scube.edu.request.LoginRequest;
import com.scube.edu.request.UserAddRequest;
import com.scube.edu.response.BaseResponse;

public interface AuthService {
	
	public BaseResponse authenticateUser(LoginRequest loginRequest , HttpServletRequest request) throws Exception;
	
	
	public Boolean addUser(UserAddRequest userRequest) throws Exception;


	public BaseResponse resetPasswordBySendingEMail(String email) throws MessagingException, Exception;


	public BaseResponse UpdatePassword(UserMasterEntity userentity) throws Exception;


	public BaseResponse checkFlagOnClickOfLink(String encodeEmail) throws Exception;


	public boolean verifyEmail(String emailId) throws Exception;

	public String logout (long userid,String UserRole) throws Exception;


}
