package com.scube.edu.service;

import java.util.ArrayList;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scube.edu.model.CollegeMaster;
import com.scube.edu.repository.CollegeRepository;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.CollegeResponse;

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

}
