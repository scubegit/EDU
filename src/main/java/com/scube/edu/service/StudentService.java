package com.scube.edu.service;

import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.scube.edu.request.StudentDocVerificationRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.PriceMasterResponse;
import com.scube.edu.response.StudentVerificationDocsResponse;
import com.scube.edu.response.VerificationListPojoResponse;

public interface StudentService {

	public List<StudentVerificationDocsResponse> getVerificationDocsDataByUserid(long userId) throws Exception;

	public List<StudentVerificationDocsResponse> getClosedRequests(long userId);

	public HashMap<String, Long> saveVerificationDocAndCalculateAmount(List<StudentDocVerificationRequest> studentDocReq, HttpServletRequest request);

	public HashMap<String, Long> saveStudentSingleVerificationDoc(StudentDocVerificationRequest studentDocReq,
			HttpServletRequest request);

	public String saveDocument(MultipartFile file);




}
