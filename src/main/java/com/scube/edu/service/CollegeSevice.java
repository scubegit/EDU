package com.scube.edu.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.scube.edu.response.CollegeResponse;

public interface CollegeSevice {

	
	List<CollegeResponse> getCollegeList(HttpServletRequest request);
}
