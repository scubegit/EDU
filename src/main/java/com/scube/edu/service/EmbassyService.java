package com.scube.edu.service;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.scube.edu.response.EmbassyResponse;


public interface EmbassyService {

	public List<EmbassyResponse> getembassy();
	public List<EmbassyResponse> getembassyList(Long id,HttpServletRequest request);
}
