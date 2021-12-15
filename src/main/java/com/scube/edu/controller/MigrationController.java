package com.scube.edu.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scube.edu.request.StudentMigrationRequest;
import com.scube.edu.request.UniversityStudentRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.UniversityStudDocResponse;
import com.scube.edu.service.MigrationService;
import com.scube.edu.util.StringsUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/migration")
public class MigrationController {
	
	private static final Logger logger = LoggerFactory.getLogger(MigrationController.class);

	BaseResponse response = null;
	
	@Autowired
	MigrationService migrationService;
	
	@PostMapping(value = "/saveMigrationRequest")
	public ResponseEntity<BaseResponse> saveMigrationRequest (@RequestBody StudentMigrationRequest stuMigReq) {
		
		System.out.println("*******MigrationController saveMigrationRequest********");
		
		response = new BaseResponse();
		
	    try {
	    	
	    	  boolean List=migrationService.saveMigrationRequest(stuMigReq) ;
				
				response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
				response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
				response.setRespData(List);
				
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
