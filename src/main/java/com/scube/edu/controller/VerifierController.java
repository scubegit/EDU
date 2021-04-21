package com.scube.edu.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scube.edu.request.StatusChangeRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.StudentVerificationDocsResponse;
import com.scube.edu.response.VerificationResponse;
import com.scube.edu.service.VerifierService;
import com.scube.edu.util.FileStorageService;
import com.scube.edu.util.StringsUtils;

import lombok.var;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/verifier")
public class VerifierController {
	
	private static final Logger logger = LoggerFactory.getLogger(VerifierController.class);

	BaseResponse response = null;
	
	@Autowired
	VerifierService	verifierService;
	
	
	@Autowired
	 private FileStorageService fileStorageService;
	
	@GetMapping("/getVerifierRequestList")
	public  ResponseEntity<Object> getVerifierRequestList() {
		
		response = new BaseResponse();
		
		    try {
		    	List<VerificationResponse> list = verifierService.getVerifierRequestList();
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
	
	
	@GetMapping("/getverifydocument/{id}")
	public  ResponseEntity<Object> verifydocument(@PathVariable Long id) {
		
		response = new BaseResponse();
		
		    try {
		    	
		    	List<StudentVerificationDocsResponse> list = verifierService.verifyDocument(id);

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

	
	//This is to get dynamic(Actual) file from the drive location
	 @GetMapping("/getimage/{UserFor}/{id}")
	 public ResponseEntity<byte[]> getFileFromStorageSelection(@PathVariable String UserFor, @PathVariable Long id ) throws Exception {

	  //      var imgFile = new ClassPathResource("image/ha-img.jpg");
	        
	       // Resource res =  fileStorageService.loadFileAsResource("file/ha-img.jpg");
		 
		 Resource res =  fileStorageService.loadFileAsResource(UserFor,id);
	        
	        byte[] bytes = StreamUtils.copyToByteArray(res.getInputStream());

	        return ResponseEntity.ok()
	                             .contentType(MediaType.IMAGE_JPEG)
	                             .body(bytes);
	    }
	 
		@PostMapping("/setStatusForVerifierDocument")
		public  ResponseEntity<Object> setStatusForVerifierDocument(@RequestBody StatusChangeRequest statusChangeRequest) {
			
			response = new BaseResponse();
			System.out.println("*****VerifierController setStatusForVerifierDocument*****"+statusChangeRequest.getRemark());
			    try {
			    	
			    	List<StudentVerificationDocsResponse> list = verifierService.setStatusForVerifierDocument(statusChangeRequest);

			    	    response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
						response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
						response.setRespData(null);
						
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
