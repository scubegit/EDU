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

import com.scube.edu.request.StudentDocVerificationRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.service.AuthService;
import com.scube.edu.service.VerificationService;
import com.scube.edu.util.StringsUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/verify")
public class VerificationRequestController {

	private static final Logger logger = LoggerFactory.getLogger(VerificationRequestController.class);
	
	BaseResponse response = null;
	
	@Autowired
	VerificationService	verificationService;
	
	@PostMapping("/saveStudentVerificationDoc") // value = "/saveStudentDoc", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = {MediaType.APPLICATION_JSON_VALUE}

	public ResponseEntity<?> saveStudentVerificationDoc (@RequestBody List<StudentDocVerificationRequest> studentDocReq, HttpServletRequest request) {
		
		logger.info("********VerificationRequestController********");
		
		response = new BaseResponse();
		
		try {

			boolean flag = verificationService.saveStudentVerificationDoc(studentDocReq, request);
			
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
