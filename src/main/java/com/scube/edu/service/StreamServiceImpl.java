package com.scube.edu.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scube.edu.model.StreamMaster;
import com.scube.edu.repository.DocumentRepository;
import com.scube.edu.repository.StreamRepository;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.StreamResponse;

@Service
public class StreamServiceImpl  implements StreamService{
	
	private static final Logger logger = LoggerFactory.getLogger(StreamServiceImpl.class);
		
		BaseResponse	baseResponse	= null;
		
		@Autowired
		StreamRepository  streamRespository;

		@Override
		public List<StreamResponse> getStreamList(HttpServletRequest request) {

			List<StreamResponse> List = new ArrayList<>();
			
			List<StreamMaster> streamEntities    = streamRespository.findAll();
			
			for(StreamMaster entity : streamEntities) {
				
				StreamResponse streamResponse = new StreamResponse();

				streamResponse.setId(entity.getId());
				streamResponse.setUniversityId(entity.getUniversityId());
				streamResponse.setStreamName(entity.getStreamName());
				
				List.add(streamResponse);
			}
			return List;
		}
}
