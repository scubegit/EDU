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

import com.scube.edu.model.DocumentMaster;
import com.scube.edu.model.StreamMaster;
import com.scube.edu.request.DocumentAddRequest;
import com.scube.edu.request.StreamRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.StreamResponse;
import com.scube.edu.service.MasterService;
import com.scube.edu.service.StreamService;
import com.scube.edu.util.StringsUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/stream")
public class StreamController {

	private static final Logger logger = LoggerFactory.getLogger(StreamController.class);

	BaseResponse response = null;
	
	@Autowired
	StreamService	streamServices;
	
	@GetMapping("/getList")
	public ResponseEntity<Object> getStreamList(HttpServletRequest request) {
		
		response = new BaseResponse();
		
		try {
			
			//response = masterServices.getStreamList(request);
			
             List<StreamResponse> responseData = streamServices.getStreamList(request);
			
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
	
	@PostMapping("/streamAdd")
	public ResponseEntity<Object> addStream(@RequestBody StreamRequest streamRequest ,  HttpServletRequest request) {
		
		logger.info("********StreamController addStream()********");
		response = new BaseResponse();
		
		try {

			String flag = streamServices.addStream(streamRequest);
			if(!flag.equals("success"))
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
	
	
	@PostMapping("/streamUpdate")
	public ResponseEntity<Object> updateStream(@RequestBody StreamMaster streamMaster ,  HttpServletRequest request) {
		
		logger.info("********StreamController updateStream()********");
		response = new BaseResponse();
				
		
		
		try {
			
			String resp = streamServices.updateStream(streamMaster);
		
			if(!resp.equals("success"))
			{
				response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);

				response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);

			}
			else
			{
				response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);

				response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);

			}
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
	
	
	

	
	@DeleteMapping("/deleteStreamById/{id}")
	public ResponseEntity<Object> deleteStreamById(@PathVariable long id,  HttpServletRequest request) {
		
		response = new BaseResponse();
		
		try {

			
			response = streamServices.deleteStreamRequest(id, request); 
			
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
