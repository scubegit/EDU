package com.scube.edu.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.scube.edu.model.DocumentMaster;
import com.scube.edu.model.CountryMaster;
import com.scube.edu.request.DocumentAddRequest;
import com.scube.edu.request.CountryRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.CountryResponse;

public interface CountryService {
	
	List<CountryResponse> getCountryList(HttpServletRequest request);
	
		CountryMaster getNameById(Long streamId);

}
