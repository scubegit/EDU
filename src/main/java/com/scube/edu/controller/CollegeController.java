package com.scube.edu.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scube.edu.model.CollegeMaster;
import com.scube.edu.model.DocumentMaster;
import com.scube.edu.request.CollegeAddRequest;
import com.scube.edu.request.DocumentAddRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.CollegeResponse;
import com.scube.edu.response.StreamResponse;
import com.scube.edu.service.CollegeSevice;
import com.scube.edu.service.MasterService;
import com.scube.edu.util.StringsUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/college")
public class CollegeController {
	

	private static final Logger logger = LoggerFactory.getLogger(CollegeController.class);

	BaseResponse response = null;
	
	@Autowired
	CollegeSevice	collegeServices;
	

	@GetMapping("/getList")
	public ResponseEntity<Object> getCollegeList(HttpServletRequest request) {
		
		response = new BaseResponse();
		
		try {
			
		    	logger.info("---------getCollegeList controller----------------");
			
			
			  List<CollegeResponse> responseData = collegeServices.getCollegeList(request);
				
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
	
	
	//Abhishek Added
	
		@PostMapping("/collegeAdd")
		public ResponseEntity<Object> addCollege(@RequestBody CollegeAddRequest collegeRequest ,  HttpServletRequest request) {
			
			logger.info("********CollegeController addCollege()********");
			response = new BaseResponse();
			
			try {

				String flag = collegeServices.addCollege(collegeRequest);
				
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
	
		
		@PostMapping("/collegeUpdate")
		public ResponseEntity<Object> updateCollege(@RequestBody CollegeMaster collegeMaster ,  HttpServletRequest request) {
			
			logger.info("********CollegeController updateCollege()********");
			response = new BaseResponse();
					
			
			
			try {
				
				String resp = collegeServices.UpdateCollege(collegeMaster);

				
			response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
			response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
			response.setRespData(resp);

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
		
		
		
		@DeleteMapping("/deleteCollegeById/{id}")
		public ResponseEntity<Object> deleteCollegeById(@PathVariable long id,  HttpServletRequest request) {
			
			response = new BaseResponse();
			
			try {

				
				response =collegeServices.deleteClgRequest(id, request); 
				
			/*
			 * response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
			 * response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
			 * response.setRespData(response);
			 */
				
				return ResponseEntity.ok(response);
				
			}catch (Exception e) {
				
				logger.error(e.getMessage());
				
				response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
				response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
				response.setRespData(e.getMessage());
				
				return ResponseEntity.badRequest().body(response);
				
			}
			
			
		}
		
		//Abhishek Added

}
