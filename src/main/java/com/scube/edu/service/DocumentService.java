package com.scube.edu.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.scube.edu.model.DocumentMaster;
import com.scube.edu.model.UserMasterEntity;
import com.scube.edu.request.DocumentAddRequest;
import com.scube.edu.request.UserAddRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.DocumentResponse;

public interface DocumentService {
	
	List<DocumentResponse> getDocumentList(HttpServletRequest request);

	DocumentMaster getNameById(String documentName);
	
	//Abhishek Added
	public String addDocument(DocumentAddRequest documentRequest) throws Exception;
	
	
	public String UpdateDocument(DocumentMaster documentMaster) throws Exception;
	
	public BaseResponse deleteDocument(long id, HttpServletRequest request) throws Exception;
	//Abhishek Added

	DocumentResponse getDocumentEntityByName(String name) throws Exception;

}
