package com.scube.edu.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.scube.edu.model.FileStorageProperties;
import com.scube.edu.model.VerificationRequest;
import com.scube.edu.repository.VerificationRequestRepository;
import com.scube.edu.request.StatusChangeRequest;
import com.scube.edu.request.StudentDocEditVerificationRequest;
import com.scube.edu.request.StudentDocVerificationRequest;
import com.scube.edu.request.paymensReqFlagRequest;
import com.scube.edu.response.BaseResponse;

import com.scube.edu.response.PriceMasterResponse;


import com.scube.edu.response.StudentVerificationDocsResponse;
import com.scube.edu.response.VerificationListPojoResponse;
import com.scube.edu.response.VerificationResponse;
import com.scube.edu.service.StudentService;
import com.scube.edu.service.VerificationService;
import com.scube.edu.util.StringsUtils;
import com.scube.edu.util.VerificationPdf;
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;
import com.scube.edu.util.InvoicePDFExporter;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/student")
public class StudentController {
	
	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

	BaseResponse response = null;
	
	@Autowired
	StudentService	studentService;
	
	@Autowired
	VerificationService	verificationService;
	
	@Autowired
	VerificationRequestRepository verificationReqRepository;
	
	@Autowired
	VerificationPdf	verificationPdf;
	
	@PutMapping("/editVerificationRequest")
	public  ResponseEntity<Object> editVerificationRequest(@RequestBody StudentDocEditVerificationRequest stuVerReq) {
		response = new BaseResponse();
		
		    try {

		    	boolean list = studentService.editVerificationRequest(stuVerReq);
					
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
	
	@GetMapping("/getVerificationRequestById/{id}")
	public  ResponseEntity<Object> getVerificationRequestById(@PathVariable long id) {
		response = new BaseResponse();
		
		    try {

		    	VerificationResponse list = studentService.getVerificationRequestById(id);
					
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
	
	@GetMapping("/getVerificationDataByUserid/{userId}")
	public  ResponseEntity<Object> getVerificationDataByUserid(@PathVariable long userId) {
		response = new BaseResponse();
		
		    try {

		    	List<VerificationResponse> list = studentService.getVerificationDocsDataByUserid(userId);
					
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
	
	@GetMapping("/getClosedRequests/{userId}")
	public  ResponseEntity<Object> getClosedRequests(@PathVariable long userId) {
		
		response = new BaseResponse();
		
		    try {
		    	List<VerificationResponse> list = studentService.getClosedRequests(userId);
					
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
	
	@PostMapping("/saveVerificationDocAndCalculateAmount")
	public  ResponseEntity<Object> saveVerificationDocAndCalculateAmount(@RequestBody List<StudentDocVerificationRequest> studentDocReq, HttpServletRequest request) {
		response = new BaseResponse();
		    try {
		    	
		    	HashMap<String, List<Long>> list = studentService.saveVerificationDocAndCalculateAmount(studentDocReq, request);
					
					response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
					response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
					response.setRespData(list);
					
					return ResponseEntity.ok(response);
						
				}catch (Exception e) {
					
					
					logger.error(e.getMessage()); //BAD creds message comes from here
					
					response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
					response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
					response.setRespData(e.getMessage());
					
					logger.error("Check response 44"+response); 
					
					return ResponseEntity.badRequest().body(response);
					
				}
			
   }
	
	
	@PostMapping(value = "/uploadVerificationDocumentFile" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<BaseResponse> upload (@RequestParam MultipartFile file) {
		
		System.out.println("*******StudentController uploadVerificationDocumentFile********"+ file);
		
		response = new BaseResponse();
		
	    try {
	    	String path = studentService.saveDocument(file);
				
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
	
	
	@Autowired
	InvoicePDFExporter	invoicePDFExporter; 
	
	 @Value("${file.imagepath-dir}")
     private String imageLocation;
     
    @GetMapping("/exportinvoicepdf/{applicationId}")
    public void exportToPDF(@PathVariable String applicationId, HttpServletResponse response ,HttpServletRequest request) throws Exception {

		logger.info("-----imageLocation---------------"+imageLocation);
    	
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        
        String currentDate = dateFormatter.format(new Date());
         
        String headerKey =  "Content-Disposition";
        String headerValue = "attachment; filename= "+applicationId+"_" + currentDate + ".pdf";
        response.setHeader(headerKey, headerValue);
        try {
        HashMap<Object, Object>   pdfData  = verificationService.getdatabyapplicationId( applicationId) ;
        
        logger.info("before export");
     //   InvoicePDFExporter exporter = new InvoicePDFExporter(pdfData,applicationId, imageLocation);
        
        invoicePDFExporter.export(response,pdfData,applicationId, imageLocation);
        logger.info("after export"+ response.toString());
        }catch(Exception e) {
        	
        	logger.info("-----imageLocation---------------"+e.getMessage());
        }
         
    }
    
	@PostMapping("/saveStudentSingleVerificationDoc")
	public  ResponseEntity<Object> saveStudentSingleVerificationDoc(@RequestBody StudentDocVerificationRequest studentDocReq, HttpServletRequest request) {
		
		response = new BaseResponse();
		System.out.println("----"+studentDocReq.getFirstname());
		
		    try {
		    	
		    	HashMap<String, Long> list = studentService.saveStudentSingleVerificationDoc(studentDocReq, request);
					
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
	
	@PostMapping("/updatePaymentflg")
	public  ResponseEntity<Object> updatePaymentFlg(@RequestBody List<paymensReqFlagRequest>studentDocReq, HttpServletRequest request) {
		
		response = new BaseResponse();
		
		    try {
		    	
		    	String list = studentService.UpdatePaymentFlag(studentDocReq);
					
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
	
	@PostMapping("/calDocPaymentAmt")
	public  ResponseEntity<Object> calculateDocAmt(@RequestBody List<StudentDocVerificationRequest> studentDocReq, HttpServletRequest request) {
		response = new BaseResponse();
		    try {
		    	
		    	HashMap<String,List<Long>> list = studentService.CalculateDocAmount(studentDocReq);
					
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
	
	@PostMapping("/generateVerificationPdf")
    public void exportVerificationPDF(@RequestBody StatusChangeRequest statusChangeRequest, HttpServletResponse response ,HttpServletRequest request) throws Exception {

		logger.info("-----imageLocation---------------"+imageLocation);
    	
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        
        String currentDate = dateFormatter.format(new Date());
        
        VerificationRequest entt = verificationReqRepository.findById(statusChangeRequest.getId());
		
		Long id = entt.getId();
		
		Long applicationId = entt.getApplicationId();
		
		String status =statusChangeRequest.getStatus();
         
        String headerKey =  "Content-Disposition";
        String headerValue = "attachment; filename= "+applicationId+"_" + currentDate + ".pdf";
        response.setHeader(headerKey, headerValue);
        
		if (status.equalsIgnoreCase("Approved") || status.equalsIgnoreCase("SV_Approved")
				|| status.equalsIgnoreCase("Uni_Auto_Approved") || status.equalsIgnoreCase("UN_Approved_Pass")|| status.equalsIgnoreCase("UN_Approved_Fail")
				|| status.equalsIgnoreCase("SVD_Approved_Pass") || status.equalsIgnoreCase("SVD_Approved_Fail")) {
			
			try {
				verificationPdf.writeApprovalPdf(response, id, imageLocation);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			logger.info("writeApprovalPdf----->");
		}
		
		if (status.equalsIgnoreCase("Rejected") || status.equalsIgnoreCase("SV_Rejected")
				|| status.equalsIgnoreCase("Uni_Auto_Rejected") || status.equalsIgnoreCase("UN_Rejected") || status.equalsIgnoreCase("SVD_Rejected")) {
		
			try {
				verificationPdf.writeRejectionPdf(response, id, imageLocation);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			logger.info("writeRejectionPdf----->");

		}
		
		
        
         
    }
}
