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

import com.scube.edu.request.TalukaRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.BranchResponse;
import com.scube.edu.response.TalukaResponse;
import com.scube.edu.service.BranchMasterService;
import com.scube.edu.service.TalukaService;
import com.scube.edu.util.StringsUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/taluka")
public class TalukaController {
	private static final Logger logger = LoggerFactory.getLogger(TalukaController.class);

	BaseResponse response = null;

	@Autowired 
	TalukaService talukaService;
	
	@GetMapping("/gettalukaList/{id}")
	public ResponseEntity<Object> getTalukaList(@PathVariable Long id,HttpServletRequest request) {
		
		response = new BaseResponse();
		
		try {
			
			
            List<TalukaResponse> responseData = talukaService.getTalukaList(id,request);
			
			response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
			response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
			response.setRespData(responseData);
			
			return ResponseEntity.ok(response);
			
			
				  
		}catch (Exception e) {
			
			logger.error(e.getMessage()); //BAD creds message comes from here
			
			response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
			response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
			response.setRespData(e.getMessage());
			
//			return ResponseEntity.badRequest().body(response);
			return ResponseEntity.ok(response);
			
		}
		
	}
	
		
}

