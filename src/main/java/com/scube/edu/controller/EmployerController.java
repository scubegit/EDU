package com.scube.edu.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.EmployerVerificationDocResponse;
import com.scube.edu.response.StudentVerificationDocsResponse;
import com.scube.edu.service.EmployerService;
import com.scube.edu.service.StudentService;
import com.scube.edu.util.StringsUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/employer")
public class EmployerController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployerController.class);
	
	BaseResponse response = null;
	
	@Autowired
	EmployerService	employerService;
	
	@GetMapping("/getVerificationDataByUserid/{userId}/{fromDate}/{toDate}")
	public  ResponseEntity<Object> getVerificationDataByUserid(@PathVariable long userId, @PathVariable String fromDate, @PathVariable String toDate) {
		response = new BaseResponse();
		
		    try {

		    	List<EmployerVerificationDocResponse> list = employerService.getOneMonthVerificationDocsDataByUserid(userId, fromDate, toDate);
					
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
