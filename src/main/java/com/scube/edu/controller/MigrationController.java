package com.scube.edu.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.scube.edu.model.CollegeVerificationUrlEntity;
import com.scube.edu.request.LoadTcRequest;
import com.scube.edu.request.MigrationStatusChangeRequest;
import com.scube.edu.request.StatusChangeRequest;
import com.scube.edu.request.StudentDocVerificationRequest;
import com.scube.edu.request.StudentMigrationRequest;
import com.scube.edu.request.UniversityStudentRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.MigrationRequestResponse;
import com.scube.edu.response.StudentVerificationDocsResponse;
import com.scube.edu.response.MigrationVerificationResponse;
import com.scube.edu.response.UniversityStudDocResponse;
import com.scube.edu.response.VerificationResponse;
import com.scube.edu.service.CollegeVerificationUrlService;
import com.scube.edu.service.MigrationService;
import com.scube.edu.util.FileStorageService;
import com.scube.edu.util.StringsUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/migration")
public class MigrationController {
	
	private static final Logger logger = LoggerFactory.getLogger(MigrationController.class);

	BaseResponse response = null;
	
	@Autowired
	MigrationService migrationService;
	
	@Autowired
	private FileStorageService fileStorageService;
	
	@Autowired
	CollegeVerificationUrlService collegeVerificationUrlService;
	
	@GetMapping("/getMigrationRequestByPrimarykey/{id}")
	public  ResponseEntity<Object> getMigrationRequestByPrimarykey(@PathVariable String id) {
		
		response = new BaseResponse();
		
		    try {
		    		MigrationRequestResponse list = migrationService.getMigrationRequestByPrimarykey(id);
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
	
	@GetMapping("/getMigrationRequestByUserid/{userid}")
	public  ResponseEntity<Object> getMigrationRequestByUserid(@PathVariable String userid) {
		
		response = new BaseResponse();
		
		    try {
		    		MigrationRequestResponse list = migrationService.getMigrationRequestByUserid(userid);
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
	
	@PostMapping(value = "/uploadMigrationDocument" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<BaseResponse> upload (@RequestParam MultipartFile file) {
		
		System.out.println("*******MigrationController uploadMigrationDocument********"+ file);
		
		response = new BaseResponse();
		
	    try {
	    	String path = migrationService.saveMigrationDocument(file);
				
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
	
	@Value("${file.awsORtest}")
    private String awsORtest;
	
	 @GetMapping("/loadEncodedTC/{randomKey}/{flag}")
	 public ResponseEntity<byte[]> loadEncodedTCAsResource(@PathVariable String randomKey, @PathVariable String flag) throws Exception {
		 
		 String idd = "";
		 String random = "";
		 if(flag.equalsIgnoreCase("encoded")) {
			 Base64.Decoder decoder = Base64.getDecoder();  
			 random = new String(decoder.decode(randomKey)); 
			 
		 }
		
		 if(awsORtest.equalsIgnoreCase("TEST") || awsORtest.equalsIgnoreCase("LOCAL")) {
			 CollegeVerificationUrlEntity urlEntt = new CollegeVerificationUrlEntity();
			 if(flag.equalsIgnoreCase("encoded")) {
				 CollegeVerificationUrlEntity urlEnt = collegeVerificationUrlService.getEntityByRandomKey(random);
				 urlEntt = urlEnt;
				 if(urlEnt.getStatus().equalsIgnoreCase("Disabled")) {
					 throw new Exception("This link has expired since student was asked to edit his application and request again."
					 		+ "You will recieve another mail with the new verification link."
					 		+ "Thanks, Team University.");
				 }
			 }
			 
			 Resource res =  fileStorageService.loadFileAsResource("mig",Long.valueOf(urlEntt.getMigPriKey()));
			 
			 byte[] bytes = StreamUtils.copyToByteArray(res.getInputStream());
			 
			 MediaType mediaType;
			 String ext = FilenameUtils.getExtension(res.getFilename());
		        
		        if(ext.equalsIgnoreCase("pdf")|| ext == "PDF" ) {
		        
		        	mediaType = MediaType.APPLICATION_PDF ;
		        }
		        else {
		        	mediaType = MediaType.IMAGE_JPEG ;
		        }
		        	
		        
		        return ResponseEntity.ok()
		                             .contentType(mediaType)
		                             .body(bytes);
			 
		 }else {
			 
			 HashMap<String, Object> res =  fileStorageService.loadFileAsResourceFromAws("mig",Long.valueOf(idd));
			 
			 //File file = new File(res.getFile());
			 
			 
			 
			 
//		        byte[] bytes = StreamUtils.copyToByteArray(res.getInputStream());

		        byte[] ret = (byte[]) res.get("byteArray");
		        String ext = (String) res.get("extension");
		        MediaType mediaType;
			 
		        
		        if(ext.equalsIgnoreCase("pdf")|| ext == "PDF" ) {
		        
		        	mediaType = MediaType.APPLICATION_PDF ;
		        }
		        else {
		        	mediaType = MediaType.IMAGE_JPEG ;
		        }
		        	
		        
		        return ResponseEntity.ok()
		                             .contentType(mediaType)
		                             .body(ret);
			 
		 }
		 
	 }
	 
	 @GetMapping("/loadTC/{migId}")
	 public ResponseEntity<byte[]> loadTCAsResource(@PathVariable String migId) throws Exception {
		
		 if(awsORtest.equalsIgnoreCase("TEST") || awsORtest.equalsIgnoreCase("LOCAL")) {
			 
			 Resource res =  fileStorageService.loadFileAsResource("mig",Long.valueOf(migId));
			 
			 byte[] bytes = StreamUtils.copyToByteArray(res.getInputStream());
			 
			 MediaType mediaType;
			 String ext = FilenameUtils.getExtension(res.getFilename());
		        
		        if(ext.equalsIgnoreCase("pdf")|| ext == "PDF" ) {
		        
		        	mediaType = MediaType.APPLICATION_PDF ;
		        }
		        else {
		        	mediaType = MediaType.IMAGE_JPEG ;
		        }
		        	
		        
		        return ResponseEntity.ok()
		                             .contentType(mediaType)
		                             .body(bytes);
			 
		 }else {
			 
			 HashMap<String, Object> res =  fileStorageService.loadFileAsResourceFromAws("mig",Long.valueOf(migId));
			 
			 //File file = new File(res.getFile());
			 
			 
			 
			 
//		        byte[] bytes = StreamUtils.copyToByteArray(res.getInputStream());

		        byte[] ret = (byte[]) res.get("byteArray");
		        String ext = (String) res.get("extension");
		        MediaType mediaType;
			 
		        
		        if(ext.equalsIgnoreCase("pdf")|| ext == "PDF" ) {
		        
		        	mediaType = MediaType.APPLICATION_PDF ;
		        }
		        else {
		        	mediaType = MediaType.IMAGE_JPEG ;
		        }
		        	
		        
		        return ResponseEntity.ok()
		                             .contentType(mediaType)
		                             .body(ret);
			 
		 }
		 
	 }
	 
	 @PostMapping("/getMigrationRequestAmount")
		public  ResponseEntity<Object> calculateDocAmt(@RequestBody StudentMigrationRequest stuMigReq, HttpServletRequest request) {
			response = new BaseResponse();
			    try {
			    	
			    	HashMap<String,Long> list = migrationService.calculateMigrationAmount(stuMigReq);
						
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
	 

	 @PutMapping("/setStatusForMigrationRequest")
		public  ResponseEntity<Object> setStatusForMigrationRequest(@RequestBody MigrationStatusChangeRequest migStatusChangeRequest) {
			
			response = new BaseResponse();
			System.out.println("*****MigrationController setStatusForMigrationRequest*****"+migStatusChangeRequest.getId());
			    try {
			    	
			    	boolean updated = migrationService.setStatusForMigrationRequest(migStatusChangeRequest);

			    	    response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
						response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
						response.setRespData(updated);
						
						return ResponseEntity.ok(response);
						
				}catch (Exception e) {

					logger.error(e.getMessage()); //BAD creds message comes from here
					
					response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
					response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
					response.setRespData(e.getMessage());
					
					return ResponseEntity.badRequest().body(response);
					
				}
			
   }

	 @GetMapping("/getAllMigrationRequests")
		public  ResponseEntity<Object> getAllMigrationRequests(HttpServletRequest request) {
			response = new BaseResponse();
			    try {
			    	
			    	List<MigrationVerificationResponse> list = migrationService.getAllMigrationRequests();
						
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
	 
	 @GetMapping("/resendCollegeMail/{migId}/{collegeId}")
		public  ResponseEntity<Object> resendCollegeMail(@PathVariable String migId, @PathVariable String collegeId, HttpServletRequest request) {
			response = new BaseResponse();
			    try {
			    	
			    	boolean list = migrationService.resendCollegeMail(migId,collegeId);
						
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
	
	

}
