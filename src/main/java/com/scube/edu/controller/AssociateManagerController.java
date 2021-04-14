package com.scube.edu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.scube.edu.response.BaseResponse;
import com.scube.edu.service.AssociateManagerService;
import com.scube.edu.util.StringsUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/AssociateManager")
public class AssociateManagerController {
	
	private static final Logger logger = LoggerFactory.getLogger(MasterController.class);

	BaseResponse response = null;
	
	 @Autowired
	 AssociateManagerService associateManagerService;
	 
	
	@PostMapping(value = "/uploadStudentInfoFromFile" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<BaseResponse> saveStudentData (@RequestParam MultipartFile excelfile,@RequestParam MultipartFile datafile) {
		
		System.out.println("*******AssociateManagerController saveStudentData********"+ datafile);
		
		response = new BaseResponse();
		
	    try {
	    	
	    	String path = associateManagerService.saveStudentInfo(excelfile,datafile);
				
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
}
