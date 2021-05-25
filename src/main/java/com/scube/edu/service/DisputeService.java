package com.scube.edu.service;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.lowagie.text.BadElementException;
import com.scube.edu.request.DisputeRequest;
import com.scube.edu.request.LoginRequest;
import com.scube.edu.request.UpdateDisputeRequest;
import com.scube.edu.request.UserAddRequest;
import com.scube.edu.response.BaseResponse;

public interface DisputeService {

	public boolean saveDispute(DisputeRequest disputeReq, HttpServletRequest request) throws Exception;

	public boolean updateDispute(UpdateDisputeRequest updateDisputeReq, HttpServletRequest request) throws Exception, BadElementException, IOException;

}
