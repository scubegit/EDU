package com.scube.edu.controller;

import java.util.List;

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
import com.scube.edu.response.CollegeResponse;
import com.scube.edu.response.DocumentResponse;
import com.scube.edu.response.StreamResponse;
import com.scube.edu.response.UserResponse;
import com.scube.edu.response.YearOfPassingResponse;
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
			
			//response = masterServices.getStreamList(request);
			
			
             List<StreamResponse> responseData = masterServices.getStreamList(request);
			
			response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
			response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
			response.setRespData(responseData);
			
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
			
          List<DocumentResponse> responseData = masterServices.getDocumentList(request);
			
			response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
			response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
			response.setRespData(responseData);
			
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
			
			  List<CollegeResponse> responseData = masterServices.getCollegeList(request);
				
				response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
				response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
				response.setRespData(responseData);		
				
				
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
			
		  List<YearOfPassingResponse> responseData = masterServices.getYearOfPassingMasterList(request);
		  
		    response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
			response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
			response.setRespData(responseData);
			
			logger.info("------------getYearOfPassingMaster------------------");
			
			
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
