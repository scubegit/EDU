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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scube.edu.model.PassingYearMaster;
import com.scube.edu.model.PriceMaster;
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
	VerificationRequestRepository verificationReqRepo;
	 
	 @Autowired
	 VerificationDocViewRepository veriDocViewRepo;
	 
	 @Autowired
	 YearOfPassingService yearOfPassService;
	 
	 @Override

		public List<StudentVerificationDocsResponse> getVerificationDocsDataByUserid(long userId) throws Exception {

		 
		 logger.info("********StudentServiceImpl getVerificationDataByUserid********");
		 
		 	List<StudentVerificationDocsResponse> List = new ArrayList<>();
		 	
		 	List<VerificationRequest> verReq = verificationReqRepository.findByUserId(userId);
			
			System.out.println("---------"+ verReq);
			
			for(VerificationRequest req: verReq) {
				
				StudentVerificationDocsResponse studentVerificationList = new StudentVerificationDocsResponse();
				
				PassingYearMaster year = yearOfPassService.getYearById(req.getYearOfPassingId());
				
				studentVerificationList.setDoc_status(req.getDocStatus());
				studentVerificationList.setId(req.getId());
				studentVerificationList.setApplication_id(req.getApplicationId());
//				closedDocResp.setCollege_name_id(req.getCollegeId());
				studentVerificationList.setDoc_name(req.getDocumentName());
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
			
			
			closedDocResp.setDoc_status(req.getDocStatus());
			closedDocResp.setId(req.getId());
			closedDocResp.setApplication_id(req.getApplicationId());
//			closedDocResp.setCollege_name_id(req.getCollegeId());
			closedDocResp.setDoc_name(req.getDocumentName());
			closedDocResp.setEnroll_no(req.getEnrollmentNumber());
			closedDocResp.setFirst_name(req.getFirstName());
			closedDocResp.setLast_name(req.getLastName());
////			closedDocResp.setRequest_type_id(req.get);
//			closedDocResp.setStream_id(req.getStreamId());
//			closedDocResp.setUni_id(req.getUniversityId());
			closedDocResp.setUser_id(req.getUserId());
			closedDocResp.setVer_req_id(req.getVerRequestId());
			closedDocResp.setYear(year.getYearOfPassing());	
			
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
		
		for(StudentDocVerificationRequest req : studentDocReq) {
			
			VerificationRequest resp = new VerificationRequest();
			
			Long assign_to = (long) 0;
			System.out.println("------In Save and calculate Req FOR LOOP----");
			
			PriceMaster diff =  priceMasterRepo.getPriceByYearDiff(year , req.getYearOfPassId());
			
			// diff fetches * from pricemaster where year diff is between year_range_start and year_range_end
			
			total = diff.getTotalAmt();
			totalWithGST =    ((diff.getTotalAmt() * diff.getGst()) / 100) + diff.getTotalAmt();
			
			amtWithoutGST += diff.getTotalAmt();
			amtWithGST += ((diff.getTotalAmt() * diff.getGst()) / 100) + diff.getTotalAmt();
			
			logger.info(total+ "   "+ totalWithGST+ "   "+ amtWithoutGST + "   "+ amtWithGST);
			
			// Make a verificationRequestRepo function which will save total and totalWithoutGST against each application id
			
//			resp.setApplicationId(req.getApplicationId());
			resp.setApplicationId(appId);
			resp.setAssignedTo(assign_to);
			resp.setCollegeId(req.getCollegeNameId());
			resp.setDocStatus("Requested");
			resp.setDocumentName(req.getDocName());
			resp.setEnrollmentNumber(req.getEnrollNo());
			resp.setFirstName(req.getFirstName());
			resp.setLastName(req.getLastName());
			resp.setIsdeleted("N");
			resp.setStreamId(req.getStreamId());
			resp.setUniversityId(req.getUniId());
			resp.setUploadDocumentPath(req.getUploadDocPath());
			resp.setUserId(req.getUserId());
			resp.setVerRequestId(req.getVerReqId());
			resp.setYearOfPassingId(String.valueOf(req.getYearOfPassId()));
			
			resp.setDocAmt(total);
			resp.setDosAmtWithGst(totalWithGST);
			
			list.add(resp);
			
		}
		HashMap<String, Long> map = new HashMap<String, Long>();
		
		map.put("total_without_gst", amtWithoutGST);
		map.put("total_with_gst", amtWithGST);
		verificationReqRepo.saveAll(list);
		logger.info("list------------"+ list);
		
		return map;
	}

	
}
