package com.scube.edu.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.scube.edu.model.DocumentMaster;
import com.scube.edu.model.StreamMaster;
import com.scube.edu.request.DocumentAddRequest;
import com.scube.edu.request.StreamRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.StreamResponse;

public interface StreamService {
	
	List<StreamResponse> getStreamList(HttpServletRequest request);
	
	//Abhishek Added
		public Boolean addStream(StreamRequest streamRequest) throws Exception;

		public BaseResponse updateStream(StreamMaster streamMaster) throws Exception;
		//Abhishek Added
}
