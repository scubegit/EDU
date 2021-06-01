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

import com.scube.edu.request.DocumentAddRequest;
import com.scube.edu.request.UserAddRequest;
import com.scube.edu.request.YearOfPassingRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.StreamResponse;
import com.scube.edu.response.YearOfPassingResponse;
import com.scube.edu.service.MasterService;
import com.scube.edu.service.YearOfPassingService;
import com.scube.edu.util.StringsUtils;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/yearOfPassing")
public class YearOfPassingController {


	private static final Logger logger = LoggerFactory.getLogger(YearOfPassingController.class);

	BaseResponse response = null;
	
	@Autowired
	YearOfPassingService	yearOfPassingservice;;
	
	@GetMapping("/getYearList")
	public ResponseEntity<Object> getStreamMaster(HttpServletRequest request) {
		
		response = new BaseResponse();
		
		try {
			
			//response = masterServices.getStreamList(request);
			
             List<YearOfPassingResponse> responseData = yearOfPassingservice.getYearOfPassingList(request);
			
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
	
	
	@PostMapping("/addYear")
	public ResponseEntity<Object> addDocument(@RequestBody YearOfPassingRequest yearOfPassReq ,  HttpServletRequest request) {
		
		logger.info("********DocumentController addDocument()********");
		response = new BaseResponse();
		
		try {

			boolean flag = yearOfPassingservice.addYear(yearOfPassReq);
			
			if (flag == false)
			{
				response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);

				response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);

			}
			else
			{
				response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
				response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);


			}
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
	
	
	@DeleteMapping("/deleteYearById/{id}")
	public ResponseEntity<Object> deleteYearById(@PathVariable long id,  HttpServletRequest request) {
		
		response = new BaseResponse();
		
		try {

			
			boolean resp =yearOfPassingservice.deleteYear(id); 
			
		
			response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
			response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
			response.setRespData(resp);
		 
			
			return ResponseEntity.ok(response);
			
		}catch (Exception e) {
			
			logger.error(e.getMessage());
			
			response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
			response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
			response.setRespData(e.getMessage());
			
			return ResponseEntity.ok(response);
			
		}
		
		
	}
	
	
	@PostMapping("/updateYearById")
	public ResponseEntity<Object> updateRecordById(@RequestBody YearOfPassingRequest yearOfPassReq,  HttpServletRequest request) {
		logger.info("*******AssociateSupervisorController updateRecordById*******");
		response = new BaseResponse();
		
		try {

			
		  String resp =yearOfPassingservice.updateYearById(yearOfPassReq, request); 
			
		
		  if(!resp.equals("Success"))
			{
				response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);

				response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);

			}
			else
			{
				response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
				response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);


			}
			
		response.setRespData(resp);
		 
			
			return ResponseEntity.ok(response);
			
		}catch (Exception e) {
			
			logger.error(e.getMessage());
			
			response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
			response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
			response.setRespData(e.getMessage());
			
			return ResponseEntity.ok(response);
			
		}
		
		
	}
	
	
	
	
}
