package com.scube.edu.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.StudentVerificationDocsResponse;
import com.scube.edu.service.VerifierService;
import com.scube.edu.util.StringsUtils;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/verifier")
public class VerifierController {
	
	private static final Logger logger = LoggerFactory.getLogger(VerifierController.class);

	BaseResponse response = null;
	
	@Autowired
	VerifierService	verifierService;
	
	@GetMapping("/getVerifierRequestList")
	public  ResponseEntity<Object> getVerifierRequestList() {
		
		response = new BaseResponse();
		
		    try {
		    	List<StudentVerificationDocsResponse> list = verifierService.getVerifierRequestList();
					// this list has FIFO mechanism for getting records for verifier (limit 5)
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
