package com.scube.edu.service;

import javax.servlet.http.HttpServletRequest;

import com.scube.edu.response.BaseResponse;

public interface MasterService {

	BaseResponse getStreamList(HttpServletRequest request);

	BaseResponse getDocumentList(HttpServletRequest request);

	BaseResponse getCollegeList(HttpServletRequest request);

	BaseResponse getYearOfPassingMasterList(HttpServletRequest request);

	
}
