package com.scube.edu.service;

import javax.servlet.http.HttpServletRequest;

import com.scube.edu.request.UniversityStudentRequest;
import com.scube.edu.response.BaseResponse;

public interface AssociateSupervisorService {

	boolean deleteRecordById(long id, HttpServletRequest request) throws Exception;

	boolean updateRecordById(UniversityStudentRequest universityStudentRequest, HttpServletRequest request) throws Exception;

}
