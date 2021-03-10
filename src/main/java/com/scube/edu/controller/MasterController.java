package com.scube.edu.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scube.edu.response.BaseResponse;
import com.scube.edu.service.MasterService;
import com.scube.edu.util.StringsUtils;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class MasterController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(MasterController.class);

	BaseResponse response = null;
	
	@Autowired
	MasterService	masterServices;
	
	@GetMapping("/getStreamMaster")
	public ResponseEntity<Object> getStreamMaster(HttpServletRequest request) {
		
		response = new BaseResponse();
		
		try {
			
			response = masterServices.getStreamList(request);
			
			logger.info("********getStreamMaster*****");
			return ResponseEntity.ok(response);
			
				  
		}catch (Exception e) {
			
			logger.error(e.getMessage()); //BAD creds message comes from here
			
			response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
			response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
			response.setRespData(e.getMessage());
			
			return ResponseEntity.badRequest().body(response);
			
		}
		
	}
	
	@GetMapping("/getDocumentMaster")
	public ResponseEntity<Object> getDocumentMaster(HttpServletRequest request) {
		
		response = new BaseResponse();
		
		try {
			
			response = masterServices.getDocumentList(request);
			
			logger.info("********getStreamMaster*****");
			return ResponseEntity.ok(response);
			
				  
		}catch (Exception e) {
			
			logger.error(e.getMessage()); //BAD creds message comes from here
			
			response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
			response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
			response.setRespData(e.getMessage());
			
			return ResponseEntity.badRequest().body(response);
			
		}
		
	}
	
	@GetMapping("/getCollegeMaster")
	public ResponseEntity<Object> getCollegeMaster(HttpServletRequest request) {
		
		response = new BaseResponse();
		
		try {
			
			response = masterServices.getCollegeList(request);
			
			logger.info("---------------getCollegeMaster--------------------------");
			return ResponseEntity.ok(response);
			
				  
		}catch (Exception e) {
			
			logger.error(e.getMessage()); //BAD creds message comes from here
			
			response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
			response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
			response.setRespData(e.getMessage());
			
			return ResponseEntity.badRequest().body(response);
			
		}
		
	}
	
	
	@GetMapping("/getYearOfPassingMaster")
	public ResponseEntity<Object>getYearOfPassingMaster (HttpServletRequest request) {
		
		response = new BaseResponse();
		
		try {
			
			response = masterServices.getYearOfPassingMasterList(request);
			
			logger.info("********getDocumentMaster*****");
			return ResponseEntity.ok(response);
			
				  
		}catch (Exception e) {
			
			logger.error(e.getMessage()); //BAD creds message comes from here
			
			response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
			response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
			response.setRespData(e.getMessage());
			
			return ResponseEntity.badRequest().body(response);
			
		}
		
	}
	
	
}
