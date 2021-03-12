package com.scube.edu.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.scube.edu.request.DisputeRequest;

import com.scube.edu.response.BaseResponse;
import com.scube.edu.service.AuthService;
import com.scube.edu.service.DisputeService;
import com.scube.edu.service.VerificationService;
import com.scube.edu.util.StringsUtils;

//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class DisputeController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	BaseResponse response = null;
	
	@Autowired
	DisputeService disputeService;
	
	@PostMapping("/saveDispute") 
	public ResponseEntity<?> saveDispute (@RequestBody DisputeRequest disputeReq, HttpServletRequest request) {
		
		logger.info("********VerificationRequestController********");
		
		response = new BaseResponse();
		
		try {

			String flag = disputeService.saveDispute(disputeReq, request);
			
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
			
			return ResponseEntity.badRequest().body(response);
			
		}
		
	}

}
