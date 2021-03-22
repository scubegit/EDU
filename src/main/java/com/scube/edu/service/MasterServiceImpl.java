package com.scube.edu.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scube.edu.model.CollegeMaster;
import com.scube.edu.model.DocumentMaster;
import com.scube.edu.model.PassingYearMaster;
import com.scube.edu.model.StreamMaster;
import com.scube.edu.model.UserMasterEntity;
import com.scube.edu.repository.CollegeRepository;
import com.scube.edu.repository.DocumentRepository;
import com.scube.edu.repository.StreamRepository;
import com.scube.edu.repository.YearOfPassingRepository;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.CollegeResponse;
import com.scube.edu.response.DocumentResponse;
import com.scube.edu.response.StreamResponse;
import com.scube.edu.response.UserResponse;
import com.scube.edu.response.YearOfPassingResponse;
import com.scube.edu.util.StringsUtils;

@Service
public class MasterServiceImpl implements MasterService {
	
	private static final Logger logger = LoggerFactory.getLogger(MasterServiceImpl.class);
	
	BaseResponse	baseResponse	= null;
	
	@Autowired
	StreamRepository streamRespository;
	
	@Autowired
	DocumentRepository documentRespository;
	
	@Autowired
	CollegeRepository collegeRespository;
	
	@Autowired
	YearOfPassingRepository yearOfPassingRepository;

	@Override
	public List<StreamResponse> getStreamList(HttpServletRequest request) {
		
			List<StreamResponse> List = new ArrayList<>();
			
			List<StreamMaster> Entities    = streamRespository.findAll();
			
			for(StreamMaster entity : Entities) {
				
				StreamResponse streamResponse = new StreamResponse();

				streamResponse.setId(entity.getId());
				streamResponse.setUniversityId(entity.getUniversityId());
				streamResponse.setStreamName(entity.getStreamName());
				
				List.add(streamResponse);
			}
			return List;
		}
	

	@Override
	public List<DocumentResponse> getDocumentList(HttpServletRequest request) {
		
		List<DocumentResponse> List = new ArrayList<>();
		
		List<DocumentMaster> Entities    = documentRespository.findAll();
		
		for(DocumentMaster entity : Entities) {
			
			DocumentResponse documentResponse = new DocumentResponse();

			documentResponse.setId(entity.getId());
			documentResponse.setDocumentName(entity.getDocumentName());
			documentResponse.setUniversityId(entity.getUniversityId());
			
			List.add(documentResponse);
		}
		return List;
	}


	@Override
	public List<CollegeResponse> getCollegeList(HttpServletRequest request) {
		
		List<CollegeResponse> List = new ArrayList<>();
		List<CollegeMaster> Entities    = collegeRespository.findAll();
		
		for(CollegeMaster entity : Entities) {
			
			CollegeResponse collegeResponse = new CollegeResponse();

			collegeResponse.setId(entity.getId());
			collegeResponse.setCollegeName(entity.getCollegeName());
			collegeResponse.setUniversityId(entity.getUniversityId());
			
			List.add(collegeResponse);
		}
		
		return List;
	}


	@Override
	public List<YearOfPassingResponse> getYearOfPassingMasterList(HttpServletRequest request) {
		
		List<YearOfPassingResponse> List = new ArrayList<>();
		List<PassingYearMaster> Entities    = yearOfPassingRepository.findAll();
		
		for(PassingYearMaster entity : Entities) {
			
			YearOfPassingResponse yearOfPassing = new YearOfPassingResponse();

			yearOfPassing.setId(entity.getId());
			yearOfPassing.setYearOfPassing(entity.getYearOfPassing());
//			yearOfPassing.setUniversityId(entity.get);
			
			List.add(yearOfPassing);
		}
		
		return List;
	}


	

	
	
}
