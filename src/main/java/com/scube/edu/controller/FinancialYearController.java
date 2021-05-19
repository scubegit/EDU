package com.scube.edu.controller;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.FinancialYearResponse;
import com.scube.edu.response.UniversityStudDocResponse;
import com.scube.edu.service.FinancialYearService;
import com.scube.edu.util.StringsUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/FinancialYear")
public class FinancialYearController {


	private static final Logger logger = LoggerFactory.getLogger(MasterController.class);

	@Autowired
	FinancialYearService financialYearService;
	BaseResponse response = null;
	@GetMapping(value = "/getList" )
	public ResponseEntity<BaseResponse> getAllFinancialYear () {
		
		System.out.println("*******FinancialYearController getAllFinancialYear********");
		
		response = new BaseResponse();
		
	    try {
	    	
	    	 List<FinancialYearResponse> List = financialYearService.getFinancialYearList();
				
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
