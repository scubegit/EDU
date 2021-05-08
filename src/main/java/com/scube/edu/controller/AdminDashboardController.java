package com.scube.edu.controller;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scube.edu.response.BaseResponse;
import com.scube.edu.service.AdminDashboardService;
import com.scube.edu.util.StringsUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/AdminDashboard")
public class AdminDashboardController {
	
	private static final Logger logger = LoggerFactory.getLogger(MasterController.class);

	BaseResponse response = null;

	@Autowired
	AdminDashboardService adminDashboardService;
	@PostMapping(value = "/getRequestStatitics" )
	public ResponseEntity<BaseResponse> getRequestStatiticsByYearWeekMonth () {
		
		System.out.println("*******AdminDashboardController saveStudentData********");
		
		response = new BaseResponse();
		
	    try {
	    	
	    	HashMap<String,HashMap<String,Integer>> List = adminDashboardService.getRequestStatByStatus();
				
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


