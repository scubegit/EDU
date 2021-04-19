package com.scube.edu.service;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scube.edu.model.VerificationRequest;
import com.scube.edu.repository.UniversityVerifierRepository;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.VerificationResponse;

@Service
public class UniversityVerifierServiceImpl implements UniversityVerifierService{
private static final Logger logger = LoggerFactory.getLogger(UniversityStudentDocServiceImpl.class);
	
	BaseResponse	baseResponse	= null;
	
	@Autowired
	UniversityVerifierRepository universityVerifierRepository;
	
	
	
	@Override
	public List<VerificationResponse> getUniversityVerifierRequestList() throws Exception {
	logger.info("*******SuperVerifierServiceImpl getUniversityVerifierRequestList*******");
		
		List<VerificationResponse> responseList = new ArrayList<>();
		
		List<VerificationRequest> list = universityVerifierRepository.findByStatus();	
		
		for(VerificationRequest req: list) {
			
			VerificationResponse resp = new VerificationResponse();
						
			resp.setDoc_status(req.getDocStatus());
			resp.setId(req.getId());
			resp.setApplication_id(req.getApplicationId());
			resp.setEnroll_no(req.getEnrollmentNumber());
			resp.setFirst_name(req.getFirstName());
			resp.setLast_name(req.getLastName());			
			resp.setUser_id(req.getUserId());
			resp.setVer_req_id(req.getVerRequestId());
			resp.setUpload_doc_path(req.getUploadDocumentPath());
			
			
			responseList.add(resp);
			
		}
		
		return responseList;
	}
	

}
