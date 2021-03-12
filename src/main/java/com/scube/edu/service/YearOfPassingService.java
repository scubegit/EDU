package com.scube.edu.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.scube.edu.response.YearOfPassingResponse;

public interface YearOfPassingService {
	
	List<YearOfPassingResponse> getYearOfPassingList(HttpServletRequest request);


}
