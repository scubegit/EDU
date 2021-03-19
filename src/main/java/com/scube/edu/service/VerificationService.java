package com.scube.edu.service;

import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.scube.edu.request.LoginRequest;
import com.scube.edu.request.StudentDocVerificationRequest;
import com.scube.edu.request.UserAddRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.StudentVerificationDocsResponse;

public interface VerificationService {


	public boolean saveStudentVerificationDoc(List<StudentDocVerificationRequest> studentDocReq, HttpServletRequest request);

	public HashMap getdatabyapplicationId(String applicationId);


}
