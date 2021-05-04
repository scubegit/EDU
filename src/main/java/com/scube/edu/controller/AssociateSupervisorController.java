package com.scube.edu.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scube.edu.request.UniversityStudentRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.service.AssociateSupervisorService;
import com.scube.edu.util.StringsUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/AssociateSupervisor")
public class AssociateSupervisorController {
	
	private static final Logger logger = LoggerFactory.getLogger(AssociateSupervisorController.class);
	
	BaseResponse response = null;
	
	@Autowired
	AssociateSupervisorService associateSupervisorService;
	
	@DeleteMapping("/deleteRecordById/{id}")
	public ResponseEntity<Object> deleteRecordById(@PathVariable long id,  HttpServletRequest request) {
		logger.info("*******AssociateSupervisorController deleteRecordById*******");
		response = new BaseResponse();
		
		try {

			
		  boolean resp =associateSupervisorService.deleteRecordById(id, request); 
			
		
		  response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
		  response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
		  response.setRespData(resp);
		 
			
			return ResponseEntity.ok(response);
			
		}catch (Exception e) {
			
			logger.error(e.getMessage());
			
			response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
			response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
			response.setRespData(e.getMessage());
			
			return ResponseEntity.badRequest().body(response);
			
		}
		
		
	}
	
	@PostMapping("/updateRecordById")
	public ResponseEntity<Object> updateRecordById(@RequestBody UniversityStudentRequest universityStudentRequest,  HttpServletRequest request) {
		logger.info("*******AssociateSupervisorController updateRecordById*******");
		response = new BaseResponse();
		
		try {

			
		  boolean resp =associateSupervisorService.updateRecordById(universityStudentRequest, request); 
			
		
		  response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
		  response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
		  response.setRespData(resp);
		 
			
			return ResponseEntity.ok(response);
			
		}catch (Exception e) {
			
			logger.error(e.getMessage());
			
			response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
			response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
			response.setRespData(e.getMessage());
			
			return ResponseEntity.badRequest().body(response);
			
		}
		
		
	}

}
