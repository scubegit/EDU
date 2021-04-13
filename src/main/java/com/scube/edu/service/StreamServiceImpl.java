package com.scube.edu.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scube.edu.model.DocumentMaster;
import com.scube.edu.model.StreamMaster;
import com.scube.edu.repository.DocumentRepository;
import com.scube.edu.repository.StreamRepository;
import com.scube.edu.request.DocumentAddRequest;
import com.scube.edu.request.StreamRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.StreamResponse;
import com.scube.edu.util.StringsUtils;

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
		
		
		//Abhishek Added
		
		@Override
		public Boolean addStream(StreamRequest streamRequest) throws Exception {
			
			
			
			StreamMaster streamMasterEntity  = new  StreamMaster();
			
			streamMasterEntity.setUniversityId(streamRequest.getUniversityId());//1
			streamMasterEntity.setStreamName(streamRequest.getStreamName());//Name Document
			streamMasterEntity.setCreateby(streamRequest.getCreated_by()); // Logged User Id 
			streamMasterEntity.setIsdeleted(streamRequest.getIs_deleted()); // By Default N	
		
			streamRespository.save(streamMasterEntity);
		
			
				
			return true;
			
		}
		
		

		@Override
		public BaseResponse updateStream(StreamMaster streamMaster) throws Exception {
			
			baseResponse	= new BaseResponse();	

			Optional<StreamMaster> streamEntities  = streamRespository.findById(streamMaster.getId());
			
			   if(streamEntities == null) {
				   
					throw new Exception(" Invalid ID");
				}
			
			
			StreamMaster streamEntit = streamEntities.get();
			
			streamEntit.setStreamName(streamMaster.getStreamName());
			//streamEntit.setUpdateby(streamMaster.getStreamName());
			
			
			streamRespository.save(streamEntit);
			
			   
		
			baseResponse.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
			baseResponse.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
			baseResponse.setRespData("success");
			 
			return baseResponse;
		}
		
		
		//Abhishek Added
		
		
}
