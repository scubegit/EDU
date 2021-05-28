package com.scube.edu.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.scube.edu.model.PassingYearMaster;
import com.scube.edu.request.YearOfPassingRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.YearOfPassingResponse;

public interface YearOfPassingService {
	
	List<YearOfPassingResponse> getYearOfPassingList(HttpServletRequest request);

	PassingYearMaster getYearById(String id);

	boolean addYear(YearOfPassingRequest yearOfPass) throws Exception;

	boolean deleteYear(long id) throws Exception;

	String updateYearById(YearOfPassingRequest yearOfPassReq, HttpServletRequest request) throws Exception;


}
