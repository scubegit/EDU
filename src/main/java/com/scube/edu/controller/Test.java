package com.scube.edu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scube.edu.response.BaseResponse;
import com.scube.edu.util.StringsUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class Test {

	private static final Logger logger = LoggerFactory.getLogger(Test.class);
	
	BaseResponse response = null;
	
	@GetMapping("/test")
	public  ResponseEntity<Object> add() {
		response = new BaseResponse();
	
		response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
		response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
		response.setRespData("");
		
		return ResponseEntity.badRequest().body(response);
		
		
		//return ResponseEntity.ok(response);
	}
}
