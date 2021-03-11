package com.scube.edu.service;

import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.StudentDocsResponse;
public interface VerifierService {

	List<StudentDocsResponse> getVerifierRequestList() throws Exception;

}
