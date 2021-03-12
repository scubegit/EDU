package com.scube.edu.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.CollegeResponse;
import com.scube.edu.response.DocumentResponse;
import com.scube.edu.response.StreamResponse;
import com.scube.edu.response.YearOfPassingResponse;

public interface MasterService {

	List<StreamResponse> getStreamList(HttpServletRequest request);

	List<DocumentResponse> getDocumentList(HttpServletRequest request);

	List<CollegeResponse> getCollegeList(HttpServletRequest request);

	List<YearOfPassingResponse> getYearOfPassingMasterList(HttpServletRequest request);

	
}
