package com.scube.edu.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.scube.edu.request.UniversityStudentRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.UniversityStudentDocumentResponse;

public interface AssociateSupervisorService {

	boolean deleteRecordById(long id, HttpServletRequest request) throws Exception;

	boolean updateRecordById(UniversityStudentRequest universityStudentRequest, HttpServletRequest request) throws Exception;

	UniversityStudentDocumentResponse getRecordById(long id, HttpServletRequest request);

	String saveDocument(MultipartFile file);

}
