package com.scube.edu.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.scube.edu.model.StreamMaster;
import com.scube.edu.response.StreamResponse;

public interface StreamService {
	
	List<StreamResponse> getStreamList(HttpServletRequest request);

	StreamMaster getNameById(Long streamId);

}
