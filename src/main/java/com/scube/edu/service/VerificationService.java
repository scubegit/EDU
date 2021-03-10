package com.scube.edu.service;

import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.scube.edu.request.LoginRequest;
import com.scube.edu.request.StudentDocRequest;
import com.scube.edu.request.UserAddRequest;
import com.scube.edu.response.BaseResponse;

public interface VerificationService {

	String saveStudentDocs(List<StudentDocRequest> studentDocReq, HttpServletRequest request);

}
