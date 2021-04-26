package com.scube.edu.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.scube.edu.model.CollegeMaster;
import com.scube.edu.model.DocumentMaster;
import com.scube.edu.request.CollegeAddRequest;
import com.scube.edu.request.DocumentAddRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.CollegeResponse;

public interface CollegeSevice {

	
	List<CollegeResponse> getCollegeList(HttpServletRequest request);
	
	//Abhishek Added
		public String addCollege(CollegeAddRequest collegeRequest) throws Exception;
		
		public BaseResponse UpdateCollege(CollegeMaster collegeMaster) throws Exception;
		
		public BaseResponse deleteClgRequest(long id, HttpServletRequest request) throws Exception;
		
		//Abhishek Added
}
