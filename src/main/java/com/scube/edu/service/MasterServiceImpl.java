package com.scube.edu.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scube.edu.model.StreamMaster;
import com.scube.edu.model.UserMasterEntity;
import com.scube.edu.repository.StreamRepository;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.StreamResponse;
import com.scube.edu.response.UserResponse;
import com.scube.edu.util.StringsUtils;

@Service
public class MasterServiceImpl implements MasterService {
	
	private static final Logger logger = LoggerFactory.getLogger(MasterServiceImpl.class);
	
	BaseResponse	baseResponse	= null;
	
	@Autowired
	StreamRepository streamRespository;

	@Override
	public BaseResponse getStreamList(HttpServletRequest request) {
		
		
			
			List<StreamResponse> List = new ArrayList<>();
			
			List<StreamMaster> Entities    = streamRespository.findAll();
			
			for(StreamMaster entity : Entities) {
				
				StreamResponse streamResponse = new StreamResponse();

				streamResponse.setId(entity.getId());
				streamResponse.setUniversityId(entity.getUniversityId());
				streamResponse.setStreamName(entity.getStreamName());
				
				List.add(streamResponse);
			}
			
			
			baseResponse.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
			baseResponse.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
			baseResponse.setRespData(List);
			 
			return baseResponse;
		
		
		}
	

	@Override
	public BaseResponse getDocumentList(HttpServletRequest request) {
		
		return null;
	}

	@Override
	public BaseResponse getCollegeList(HttpServletRequest request) {
		
		return null;
	}


	@Override
	public BaseResponse getYearOfPassingMasterList(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	

	
	
}
