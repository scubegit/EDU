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
import com.scube.edu.model.UserMasterEntity;
import com.scube.edu.request.DocumentAddRequest;
import com.scube.edu.request.UserAddRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.DocumentResponse;
import com.scube.edu.response.StreamResponse;
import com.scube.edu.service.DocumentService;

import com.scube.edu.util.StringsUtils;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/document")
public class DocumentController {

	private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);

	BaseResponse response = null;
	
	@Autowired
	DocumentService	documentServices;
	
	@GetMapping("/getList")
	public ResponseEntity<Object> getList(HttpServletRequest request) {
		
		response = new BaseResponse();
		
		try {
			
			//response = masterServices.getStreamList(request);
			
			logger.info("---DocumentController -------- getList------");
            List<DocumentResponse> responseData = documentServices.getDocumentList(request);
			
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
	
	@PostMapping("/documentUpload")
	public ResponseEntity<Object> addDocument(@RequestBody DocumentAddRequest documentRequest ,  HttpServletRequest request) {
		
		logger.info("********DocumentController addDocument()********");
		response = new BaseResponse();
		
		try {

			String flag = documentServices.addDocument(documentRequest);
			
			if (!flag.equals("Success"))
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
	
	
	
	@PostMapping("/documnetUpdate")
	public ResponseEntity<Object> updateDocument(@RequestBody DocumentMaster documentMaster ,  HttpServletRequest request) {
		
		logger.info("********DocumentController updateDocument()********");
		response = new BaseResponse();
				
		
		
		try {
			
			String resp = documentServices.UpdateDocument(documentMaster);

			
			if (!resp.equals("Success"))
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
	
	
	
	
	@DeleteMapping("/deleteDocumentById/{id}")
	public ResponseEntity<Object> deleteDocumentById(@PathVariable long id,  HttpServletRequest request) {
		
		response = new BaseResponse();
		
		try {

			
			response =documentServices.deleteDocument(id, request); 
			
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
