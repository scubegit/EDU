package com.scube.edu.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

import com.scube.edu.model.PassingYearMaster;
import com.scube.edu.model.PriceMaster;
import com.scube.edu.model.VerificationRequest;
import com.scube.edu.repository.PriceMasterRepository;
import com.scube.edu.repository.VerificationRequestRepository;
import com.scube.edu.repository.YearOfPassingRepository;
import com.scube.edu.request.StudentDocVerificationRequest;
import com.scube.edu.response.BaseResponse;

import com.scube.edu.response.PriceMasterResponse;
import com.scube.edu.response.StudentVerificationDocsResponse;

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
	 
	 @Override

		public List<StudentVerificationDocsResponse> getVerificationDocsDataByUserid(long userId) throws Exception {

		 
		 logger.info("********StudentServiceImpl getVerificationDataByUserid********");
		 
		 	List<StudentVerificationDocsResponse> List = new ArrayList<>();
		 	
			List<VerificationRequest> verReq = verificationReqRepository.findByUserId(userId);
			
			for(VerificationRequest veriRequest : verReq) {
				
				StudentVerificationDocsResponse stuDocResp = new StudentVerificationDocsResponse();
				
				stuDocResp.setApplication_id(veriRequest.getApplicationId());
				stuDocResp.setCollege_name_id(veriRequest.getCollegeId());
				stuDocResp.setDoc_name(veriRequest.getDocumentName());
				stuDocResp.setEnroll_no(veriRequest.getEnrollmentNumber());
				stuDocResp.setFirst_name(veriRequest.getFirstName());
				stuDocResp.setLast_name(veriRequest.getLastName());
//				stuDocResp.setRequest_type_id(veriRequest.get);
				stuDocResp.setStream_id(veriRequest.getStreamId());
				stuDocResp.setUni_id(veriRequest.getUniversityId());
				stuDocResp.setUser_id(veriRequest.getUserId());
				stuDocResp.setVer_req_id(veriRequest.getVerRequestId());
				stuDocResp.setYear_of_pass_id(veriRequest.getYearOfPassingId());		
				
				List.add(stuDocResp);
				
			}
		return List;
		 
	 }

	@Override
	public List<StudentVerificationDocsResponse> getClosedRequests(long userId) {
		
		logger.info("********StudentServiceImpl getClosedRequests********"+ userId);
		
		List<StudentVerificationDocsResponse> List = new ArrayList<>();
		
		List<VerificationRequest> closedReqs = verificationReqRepository.findByStatusAndUserId(userId);
		System.out.println("---------------------"+ closedReqs);
		
		for(VerificationRequest req: closedReqs) {
			
			StudentVerificationDocsResponse closedDocResp = new StudentVerificationDocsResponse();
			
			closedDocResp.setId(req.getId());
			closedDocResp.setApplication_id(req.getApplicationId());
			closedDocResp.setCollege_name_id(req.getCollegeId());
			closedDocResp.setDoc_name(req.getDocumentName());
			closedDocResp.setEnroll_no(req.getEnrollmentNumber());
			closedDocResp.setFirst_name(req.getFirstName());
			closedDocResp.setLast_name(req.getLastName());
//			closedDocResp.setRequest_type_id(req.get);
			closedDocResp.setStream_id(req.getStreamId());
			closedDocResp.setUni_id(req.getUniversityId());
			closedDocResp.setUser_id(req.getUserId());
			closedDocResp.setVer_req_id(req.getVerRequestId());
			closedDocResp.setYear_of_pass_id(req.getYearOfPassingId());	
			
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
			
			resp.setApplicationId(req.getApplicationId());
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
//		verificationReqRepo.saveAll(list);
		logger.info("list------------"+ list);
		
		return map;
	}

	
}
