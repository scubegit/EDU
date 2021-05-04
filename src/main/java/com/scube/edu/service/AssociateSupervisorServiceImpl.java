package com.scube.edu.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scube.edu.controller.MasterController;
import com.scube.edu.model.UniversityStudentDocument;
import com.scube.edu.repository.UniversityStudentDocRepository;
import com.scube.edu.request.UniversityStudentRequest;
import com.scube.edu.response.BaseResponse;

@Service
public class AssociateSupervisorServiceImpl implements AssociateSupervisorService{
	
	private static final Logger logger = LoggerFactory.getLogger(AssociateSupervisorServiceImpl.class);
	
	@Autowired
	UniversityStudentDocRepository 	universityStudentDocRepository ;

	@Override
	public boolean deleteRecordById(long id, HttpServletRequest request) throws Exception {
		
		logger.info("*******AssociateSupervisorServiceImpl deleteRecordById*******");
		
		Optional<UniversityStudentDocument> usd = universityStudentDocRepository.findById(id);
		UniversityStudentDocument uniRecord = usd.get();
		
		if(uniRecord == null) {
			   
			throw new Exception(" Invalid ID");
			
		}else {
			
			universityStudentDocRepository.deleteById(id);
			return true;
			
		}
		
		
	}

	@Override
	public boolean updateRecordById(UniversityStudentRequest universityStudentRequest, HttpServletRequest request) throws Exception {
		
		logger.info("*******AssociateSupervisorServiceImpl updateRecordById*******");
		
		Optional<UniversityStudentDocument> usd = universityStudentDocRepository.findById(universityStudentRequest.getId());
		UniversityStudentDocument ogRecord = usd.get();
		
		if(ogRecord != null) {
			logger.info("NOT NULL---");
			
			UniversityStudentDocument editRecord = new UniversityStudentDocument();
			
			editRecord.setId(universityStudentRequest.getId());
			editRecord.setFirstName(universityStudentRequest.getFirstName());
			editRecord.setLastName(universityStudentRequest.getLastName());
			editRecord.setEnrollmentNo(universityStudentRequest.getEnrollmentNo());
			editRecord.setOriginalDOCuploadfilePath(universityStudentRequest.getOriginalDOCuploadfilePath());
			editRecord.setCollegeId( Long.parseLong(universityStudentRequest.getCollegeId()));
			editRecord.setPassingYearId( Long.parseLong(universityStudentRequest.getPassingYearId()));
			editRecord.setStreamId( Long.parseLong(universityStudentRequest.getStreamId()));
			
			universityStudentDocRepository.save(editRecord);
			
			return true;
			
		}else {
			throw new Exception("Request Body cannot be empty.");
		}
		
		
	}

}
