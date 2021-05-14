package com.scube.edu.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.scube.edu.request.UniversityStudentRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.StudentVerificationDocsResponse;
import com.scube.edu.response.UniversityStudentDocumentResponse;
import com.scube.edu.response.VerificationResponse;
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
	
	@PostMapping("/updateRecordById/{id}")
	public ResponseEntity<Object> updateRecordById(@RequestBody UniversityStudentRequest universityStudentRequest,Long id,  HttpServletRequest request) {
		logger.info("*******AssociateSupervisorController updateRecordById*******");
		response = new BaseResponse();
		
		try {

			
		  boolean resp =associateSupervisorService.updateRecordById(universityStudentRequest,id, request); 
			
		
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
	
	@GetMapping("/getRecordById/{id}")
	public ResponseEntity<Object> getRecordById(@PathVariable long id,  HttpServletRequest request) {
		logger.info("*******AssociateSupervisorController getRecordById*******");
		response = new BaseResponse();
		
		try {

			
			UniversityStudentDocumentResponse resp =associateSupervisorService.getRecordById(id, request); 
			
		
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
	
	@PostMapping(value = "/uploadChangedFile" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<BaseResponse> upload (@RequestParam MultipartFile file) {
		
		System.out.println("*******StudentController uploadVerificationDocumentFile********"+ file);
		
		response = new BaseResponse();
		
	    try {
	    	String path = associateSupervisorService.saveDocument(file);
				
				response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
				response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
				response.setRespData(path);
				
				return ResponseEntity.ok(response);
					
			}catch (Exception e) {
				
				logger.error(e.getMessage()); //BAD creds message comes from here
				
				response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
				response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
				response.setRespData(e.getMessage());
				
				return ResponseEntity.badRequest().body(response);
				
			}
	}
	
	
	@GetMapping("/getverifydocument/{id}")
	public  ResponseEntity<Object> verifydocument(@PathVariable Long id) {
		
		response = new BaseResponse();
		
		logger.info("getVerifyDoc");
		    try {
		    	logger.info("---getVerifyDoc");
		    	VerificationResponse list = associateSupervisorService.verifyDocument(id);

		    	    response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
					response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
					response.setRespData(list);
					
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
