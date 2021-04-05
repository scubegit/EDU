package com.scube.edu.service;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scube.edu.model.DocumentMaster;
import com.scube.edu.model.PassingYearMaster;
import com.scube.edu.model.PriceMaster;
import com.scube.edu.model.StreamMaster;
import com.scube.edu.model.VerificationDocView;
import com.scube.edu.model.VerificationRequest;
import com.scube.edu.repository.PriceMasterRepository;
import com.scube.edu.repository.VerificationDocViewRepository;
import com.scube.edu.repository.VerificationRequestRepository;
import com.scube.edu.repository.YearOfPassingRepository;
import com.scube.edu.request.StudentDocVerificationRequest;
import com.scube.edu.response.BaseResponse;

import com.scube.edu.response.PriceMasterResponse;
import com.scube.edu.response.StudentVerificationDocsResponse;
import com.scube.edu.response.VerificationListPojoResponse;
import com.scube.edu.response.JwtResponse;
import com.scube.edu.response.StreamResponse;

import com.scube.edu.security.JwtUtils;
import com.scube.edu.util.FileStorageService;
import com.scube.edu.util.StringsUtils;


@Service
public class StudentServiceImpl implements StudentService{

private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
	
	Base64.Encoder baseEncoder = Base64.getEncoder();
	
	BaseResponse	baseResponse	= null;  
      
	 BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	 
	 @Autowired
	 VerificationRequestRepository verificationReqRepository;
	 
	 @Autowired
	 PriceMasterRepository priceMasterRepo;
	 
	 @Autowired
	 YearOfPassingRepository yearOfPassRepo;
	 
	@Autowired
	DocumentService	documentService;
	 
	 @Autowired
	VerificationRequestRepository verificationReqRepo;
	 
	 @Autowired
	 VerificationDocViewRepository veriDocViewRepo;
	 
	 @Autowired
	 YearOfPassingService yearOfPassService;
	 
	 @Autowired
	 private FileStorageService fileStorageService;
	 
	 @Autowired 
	 StreamService streamService;
	 
	 @Override

		public List<StudentVerificationDocsResponse> getVerificationDocsDataByUserid(long userId) throws Exception {

		 
		 logger.info("********StudentServiceImpl getVerificationDataByUserid********");
		 
		 	List<StudentVerificationDocsResponse> List = new ArrayList<>();
		 	
		 	List<VerificationRequest> verReq = verificationReqRepository.findByUserId(userId);
			
			System.out.println("---------"+ verReq);
			
			for(VerificationRequest req: verReq) {
				
				System.out.println("verReq---"+ req.getDocumentId());
				
				StudentVerificationDocsResponse studentVerificationList = new StudentVerificationDocsResponse();
				
				PassingYearMaster year = yearOfPassService.getYearById(req.getYearOfPassingId());
				
				DocumentMaster doc = documentService.getNameById(req.getDocumentId());
				
				StreamMaster stream = streamService.getNameById(req.getStreamId());
				
				studentVerificationList.setDoc_status(req.getDocStatus());
				studentVerificationList.setId(req.getId());
				studentVerificationList.setApplication_id(req.getApplicationId());
//				closedDocResp.setCollege_name_id(req.getCollegeId());
				studentVerificationList.setDoc_name(doc.getDocumentName()); //
				studentVerificationList.setEnroll_no(req.getEnrollmentNumber());
				studentVerificationList.setFirst_name(req.getFirstName());
				studentVerificationList.setLast_name(req.getLastName());
////				studentVerificationList.setRequest_type_id(req.get);
//				studentVerificationList.setStream_id(req.getStreamId());
//				studentVerificationList.setUni_id(req.getUniversityId());
				studentVerificationList.setUser_id(req.getUserId());
				studentVerificationList.setVer_req_id(req.getVerRequestId());
				studentVerificationList.setYear(year.getYearOfPassing());	
				studentVerificationList.setUpload_doc_path(req.getUploadDocumentPath());
				studentVerificationList.setStream_name(stream.getStreamName());
				
				List.add(studentVerificationList);
			}
			
			return List;
			
//			List<VerificationListPojoResponse> verDocRespPojo = new ArrayList<VerificationListPojoResponse>();
//			final ObjectMapper mapper = new ObjectMapper();
//			for(int i=0; i<verReq.size();i++) {
//				final VerificationListPojoResponse pojo = mapper.convertValue(verReq.get(i), VerificationListPojoResponse.class);
//				System.out.println(pojo);
//				verDocRespPojo.add(pojo);
//			}

				

		
		 
	 }

	@Override
	public List<StudentVerificationDocsResponse> getClosedRequests(long userId) {
		
		logger.info("********StudentServiceImpl getClosedRequests********"+ userId);
		
		List<StudentVerificationDocsResponse> List = new ArrayList<>();
		
		List<VerificationRequest> closedReqs = verificationReqRepository.findByStatusAndUserId(userId);
		System.out.println("---------------------"+ closedReqs);
		
		for(VerificationRequest req: closedReqs) {
			
			StudentVerificationDocsResponse closedDocResp = new StudentVerificationDocsResponse();
			
			PassingYearMaster year = yearOfPassService.getYearById(req.getYearOfPassingId());
			
			DocumentMaster doc = documentService.getNameById(req.getDocumentId());
			
			StreamMaster stream = streamService.getNameById(req.getStreamId());
			
			closedDocResp.setDoc_status(req.getDocStatus());
			closedDocResp.setId(req.getId());
			closedDocResp.setApplication_id(req.getApplicationId());
//			closedDocResp.setCollege_name_id(req.getCollegeId());
			closedDocResp.setDoc_name(doc.getDocumentName());
			closedDocResp.setEnroll_no(req.getEnrollmentNumber());
			closedDocResp.setFirst_name(req.getFirstName());
			closedDocResp.setLast_name(req.getLastName());
////			closedDocResp.setRequest_type_id(req.get);
//			closedDocResp.setStream_id(req.getStreamId());
//			closedDocResp.setUni_id(req.getUniversityId());
			closedDocResp.setUser_id(req.getUserId());
			closedDocResp.setVer_req_id(req.getVerRequestId());
			closedDocResp.setYear(year.getYearOfPassing());	
			closedDocResp.setStream_name(stream.getStreamName());
			
			List.add(closedDocResp);
			
		}
		
		return List;
	}

	@Override
	public HashMap<String, Long> saveVerificationDocAndCalculateAmount(List<StudentDocVerificationRequest> studentDocReq, HttpServletRequest request) {
		
		logger.info("********StudentServiceImpl calculateTotalAmount********");
		
		int year = Calendar.getInstance().get(Calendar.YEAR);
		long total;
		long totalWithGST;
		
		long amtWithoutGST = 0;
		long amtWithGST = 0;
		
		// assign application_id here
		Long appId = (long) 0;
		Long app_id;
		app_id = verificationReqRepo.getMaxApplicationId();
		logger.info("---------"+ app_id);
		
		if(app_id == null) {
			appId = (long) 1;
			System.out.println("here");
		}else {
			appId = app_id + 1;
		}
		
		logger.info("---------"+ appId);
		
		
		
		List<VerificationRequest> list = new ArrayList<>();
		Long ver_req = (long) 1;
		for(StudentDocVerificationRequest req : studentDocReq) {
			System.out.println(req);
			VerificationRequest resp = new VerificationRequest();
			
			Long assign_to = (long) 0;
			System.out.println("------In Save and calculate Req FOR LOOP----");
			
			PriceMaster diff =  priceMasterRepo.getPriceByYearDiff(year , req.getYearofpassid());
			
			// diff fetches * from pricemaster where year diff is between year_range_start and year_range_end
			
			total = diff.getTotalAmt();
			totalWithGST =    ((diff.getTotalAmt() * diff.getGst()) / 100) + diff.getTotalAmt();
			
			amtWithoutGST += diff.getTotalAmt();
			amtWithGST += ((diff.getTotalAmt() * diff.getGst()) / 100) + diff.getTotalAmt();
			
			logger.info(total+ "   "+ totalWithGST+ "   "+ amtWithoutGST + "   "+ amtWithGST);
			
			// Make a verificationRequestRepo function which will save total and totalWithoutGST against each application id
			
			resp.setApplicationId(appId);
			resp.setAssignedTo(assign_to);
			resp.setCollegeId(req.getCollegenameid());
			resp.setDocStatus("Requested");
			resp.setDocumentId(req.getDocname());
			resp.setEnrollmentNumber(req.getEnrollno());
			resp.setFirstName(req.getFirstname());
			resp.setLastName(req.getLastname());
			resp.setIsdeleted("N");
			resp.setStreamId(req.getStreamid());
			resp.setUniversityId((long) 1);
			resp.setUploadDocumentPath(req.getFilepath());
			resp.setUserId(req.getUserid());
			resp.setVerRequestId(ver_req);
			resp.setYearOfPassingId(String.valueOf(req.getYearofpassid()));
			
			resp.setDocAmt(total);
			resp.setDosAmtWithGst(totalWithGST);
			
			ver_req += 1;
			
			list.add(resp);
			
		}
		HashMap<String, Long> map = new HashMap<String, Long>();
		
		map.put("total_without_gst", amtWithoutGST);
		map.put("total_with_gst", amtWithGST);
		verificationReqRepo.saveAll(list);
		logger.info("list------------"+ list);
		
		return map;
	}

	@Override
	public HashMap<String, Long> saveStudentSingleVerificationDoc(StudentDocVerificationRequest studentDocReq,
			HttpServletRequest request) {
		
		System.out.println("********StudentServiceImpl saveStudentSingleVerificationDoc********"+ studentDocReq.getDocname());
		
		VerificationRequest verDoc = new VerificationRequest();
		
		int year = Calendar.getInstance().get(Calendar.YEAR);
		long total;
		long totalWithGST;
		
		long amtWithoutGST = 0;
		long amtWithGST = 0;
		
		// assign application_id here
		Long appId = (long) 0;
		Long app_id;
		app_id = verificationReqRepo.getMaxApplicationId();
		logger.info("---------"+ app_id);
		
		if(app_id == null) {
			appId = (long) 1;
			System.out.println("here");
		}else {
			appId = app_id + 1;
		}
		
		logger.info("---------"+ appId);
		
		PriceMaster diff =  priceMasterRepo.getPriceByYearDiff(year , studentDocReq.getYearofpassid());
		
		total = diff.getTotalAmt();
		totalWithGST =    ((diff.getTotalAmt() * diff.getGst()) / 100) + diff.getTotalAmt();
		
		verDoc.setApplicationId(appId);
		verDoc.setAssignedTo((long)0);
		verDoc.setCollegeId(studentDocReq.getCollegenameid());
		verDoc.setCreateby(String.valueOf(studentDocReq.getUserid()));
		verDoc.setDocAmt(total);
		verDoc.setDocStatus("Requested");
		verDoc.setDocumentId(studentDocReq.getDocname());
		verDoc.setDosAmtWithGst(totalWithGST);
		verDoc.setEnrollmentNumber(studentDocReq.getEnrollno());
		verDoc.setFirstName(studentDocReq.getFirstname());
		verDoc.setLastName(studentDocReq.getLastname());
		verDoc.setIsdeleted("N");
		verDoc.setStreamId(studentDocReq.getStreamid());
		verDoc.setUniversityId((long) 1);
		verDoc.setUploadDocumentPath(studentDocReq.getFilepath());
		verDoc.setVerRequestId((long) 1);
		verDoc.setYearOfPassingId(String.valueOf(studentDocReq.getYearofpassid()));
		verDoc.setUserId(studentDocReq.getUserid());
		verificationReqRepo.save(verDoc);
		
		HashMap<String, Long> map = new HashMap<String, Long>();
		
		map.put("total_without_gst", total);
		map.put("total_with_gst", totalWithGST);
		
		return map;
//		return null;
	}
	
	
	public String saveDocument (MultipartFile file) {
		String fileSubPath = "file/";
		String filePath = fileStorageService.storeFile(file , fileSubPath);
		
		
		
		return filePath;
		
	}

	
}
