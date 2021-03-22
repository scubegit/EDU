package com.scube.edu.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scube.edu.model.DocumentMaster;
import com.scube.edu.repository.CollegeRepository;
import com.scube.edu.repository.DocumentRepository;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.DocumentResponse;


@Service
public class DocumentServiceImpl implements DocumentService{
	
    private static final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);
	
	@Autowired
	DocumentRepository documentRespository;

	@Override
	public List<DocumentResponse> getDocumentList(HttpServletRequest request) {
		
		logger.info("-----------getDocumentList---------------------");
		
		List<DocumentResponse> docList = new ArrayList<>();
		
		List<DocumentMaster> docEntities    = documentRespository.findAll();
		
		for(DocumentMaster entity : docEntities) {
			
			DocumentResponse documentResponse = new DocumentResponse();

			documentResponse.setId(entity.getId());
			documentResponse.setDocumentName(entity.getDocumentName());
			documentResponse.setUniversityId(entity.getUniversityId());
			
			docList.add(documentResponse);
		}
		return docList;
	}

	@Override
	public DocumentMaster getNameById(String documentName) {
		
		Long ID = Long.parseLong(documentName);
		System.out.println(ID);
		
		Optional<DocumentMaster> docMas = documentRespository.findById(ID);
		DocumentMaster resp = docMas.get();
		
		return resp;
	}
}
