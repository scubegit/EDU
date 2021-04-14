package com.scube.edu.service;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scube.edu.model.CollegeMaster;
import com.scube.edu.model.DocumentMaster;
import com.scube.edu.repository.CollegeRepository;
import com.scube.edu.request.CollegeAddRequest;
import com.scube.edu.request.DocumentAddRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.CollegeResponse;
import com.scube.edu.util.StringsUtils;

@Service
public class CollegeSeviceImpl implements CollegeSevice {

private static final Logger logger = LoggerFactory.getLogger(CollegeSeviceImpl.class);
	
	BaseResponse	baseResponse	= null;
	
	@Autowired
	CollegeRepository collegeRespository;
	
	@Override
	public List<CollegeResponse> getCollegeList(HttpServletRequest request) {
		
		 List<CollegeResponse> collegeList = new ArrayList<>();
			
			List<CollegeMaster> collegeEntities    = collegeRespository.findAll();
			
			for(CollegeMaster entity : collegeEntities) {
				
				CollegeResponse response = new CollegeResponse();

				response.setId(entity.getId());
				response.setCollegeName(entity.getCollegeName());
				response.setUniversityId(entity.getUniversityId());
				
				collegeList.add(response);
			}
			return collegeList;
	}

	
	
	
	//Abhishek Added
	
	@Override
	public Boolean addCollege(CollegeAddRequest collegeRequest) throws Exception {
		
		
		
		CollegeMaster collegeMasterEntity  = new  CollegeMaster();
		
		collegeMasterEntity.setUniversityId(collegeRequest.getUniversityId());//1
		collegeMasterEntity.setCollegeName(collegeRequest.getCollegeName());//Name Document
		collegeMasterEntity.setCreateby(collegeRequest.getCreated_by()); // Logged User Id 
		collegeMasterEntity.setIsdeleted(collegeRequest.getIs_deleted()); // By Default N	
	
	     collegeRespository.save(collegeMasterEntity);
	
		
			
		return true;
		
	}
	
	
	
	
	@Override
	public BaseResponse UpdateCollege(CollegeMaster collegeMaster) throws Exception {
		
		baseResponse	= new BaseResponse();	

		Optional<CollegeMaster> collegeEntities  = collegeRespository.findById(collegeMaster.getId());
		
		   if(collegeEntities == null) {
			   
				throw new Exception(" Invalid ID");
			}
		
		
		CollegeMaster collegeEntit = collegeEntities.get();
		
		
		collegeEntit.setCollegeName(collegeMaster.getCollegeName());
		
		
		collegeRespository.save(collegeEntit);
		
		   
	
		baseResponse.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
		baseResponse.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
		baseResponse.setRespData("success");
		 
		return baseResponse;
	}
	
	
	//Abhishek Added
}
