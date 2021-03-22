package com.scube.edu.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.scube.edu.model.DocumentMaster;
import com.scube.edu.response.DocumentResponse;

public interface DocumentService {
	
	List<DocumentResponse> getDocumentList(HttpServletRequest request);

	DocumentMaster getNameById(String documentName);
	
	
	

}
